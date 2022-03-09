package kiun.com.bvroutine.cacheline;

import android.content.Context;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.body.BaseBodyBuilder;
import kiun.com.bvroutine.cacheline.data.CacheSettingManager;
import kiun.com.bvroutine.cacheline.data.MapExtractor;
import kiun.com.bvroutine.cacheline.data.SqliteDBHelper;
import kiun.com.bvroutine.cacheline.data.UploadManager;
import kiun.com.bvroutine.cacheline.data.UploadRelationManager;
import kiun.com.bvroutine.cacheline.data.beans.Pager;
import kiun.com.bvroutine.cacheline.data.beans.RelationObject;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.cacheline.utils.JSONUtil;
import kiun.com.bvroutine.cacheline.utils.JexlUtil;
import kiun.com.bvroutine.data.ExtractorBase;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.ObjectUtil;

/**
 * 缓存核心业务类.
 */
class CacheKernel {

    public final String TAG = this.getClass().getSimpleName();

    /**
     * 缓存配置管理.
     */
    private CacheSettingManager manager;
    private UploadManager uploadManager;
    private Map<String, SqliteDBHelper> dbMap;
    private UploadRelationManager relationManager;
    private Context context;

    /**
     * 根据配置接口类型创建缓存核心.
     * @param context APP上下文.
     * @param settings 接口类型集合.
     */
    CacheKernel(Context context, String userField, CacheSettingInterface[] settings) throws Exception {

        BodyConvertBridge.loadPackage(BaseBodyBuilder.class.getPackage().getName());

        manager = new CacheSettingManager(settings);
        dbMap = new HashMap<>();
        this.context = context;

        File dbRoot = new File(context.getFilesDir(), "cache_db");
        if(!dbRoot.exists()) dbRoot.mkdir();

        for (CacheSettingInterface cacheInterface : settings){
            String dbPath = cacheInterface.theDbPath();
            File dbFile = new File(dbRoot, dbPath);

            if(!dbMap.containsKey(dbPath)) dbMap.put(dbPath, new SqliteDBHelper(dbFile.getAbsolutePath()));
        }

        SqliteDBHelper dbHelper = new SqliteDBHelper(new File(dbRoot, userField + UploadObject.UP_LOAD_DB).getAbsolutePath());
        dbMap.put(UploadObject.UP_LOAD_DB, dbHelper);
        relationManager = new UploadRelationManager(dbHelper);
        uploadManager = new UploadManager(manager, dbMap);
    }

    public void release(){
        for (SqliteDBHelper dbHelper : dbMap.values()) dbHelper.release();
    }

    /**
     * 匹配缓存数据.
     * @param url 请求原始URL.
     * @param requestBody 请求参数.
     * @param header 请求头部信息.
     * @param inBytes 请求Body内容.
     * @return 返回匹配后的数据.
     * @throws IOException 可能存在字符串转化错误.
     */
    public byte[] cacheIn(String url, Object requestBody, Map<String, String> header, byte[] inBytes) throws IOException, InterruptedException {

        String inDateString = null;
        Map<String, Object> jsonParams = requestBody instanceof Map ? (Map<String, Object>) requestBody : null; //入参内容.

        SettingUnit settingUnit = manager.matchUnitByURL(url, jsonParams);

        if(settingUnit != null){
            if(inBytes != null){
                if (settingUnit.CacheType() == CacheType.CacheDownLoad){
                    inDateString = new String(inBytes, "utf-8");

                    //服务器返回实际数据.
                    Map<String, Object> jsonObject = (Map<String, Object>) JSONUtil.getJSONData(inDateString);
                    Map<String, String> urlParam = settingUnit.getURLParams(url);
                    if(requestBody == null && urlParam != null && !urlParam.isEmpty()) requestBody = new HashMap<>();
                    JSONUtil.addMap((Map<String, Object>) requestBody, urlParam);

                    if (jsonObject != null)
                        saveCache(settingUnit, url, jsonObject, (Map<String, Object>) requestBody, null);
                }
            }else{
                //上行离线.
                if(settingUnit.CacheType() == CacheType.CacheUpLoad){
                    Map<String, String> urlParam = settingUnit.getURLParams(url);

                    //jsonParams body参数, urlParam URL 参数.
                    boolean isSueccess = saveCache(settingUnit, url, jsonParams == null ? requestBody : jsonParams, urlParam, header);
                    if(isSueccess){
                        List array = new LinkedList();
                        if(settingUnit.Return().isEmpty()){
                            if(jsonParams != null) array.add(jsonParams);
                        }else{
                            array.add(JexlUtil.exeCodeByParams(settingUnit.Return(), jsonParams,dbMap.get(settingUnit.SaveDB())));
                        }
                        String jsonString = JSON.toJSONString(JSONUtil.createStandardSuccess(array.isEmpty() ? null : array.get(0), null, false, settingUnit));
                        inBytes = jsonString.getBytes("utf-8");
                    }
                }else if (settingUnit.CacheType() == CacheType.CacheDownLoad){ //下行离线.
                    Thread.sleep(500);
                    inBytes = loadCache(settingUnit, url, jsonParams);
                }
            }
        }
        if (Debug.isDebuggerConnected() && !url.equals("http://sv.goldenwater.com.cn/pers/position/insertList")){
            if(inDateString == null && inBytes != null){
                inDateString = new String(inBytes, "utf-8");
            }
            Log.d(TAG, String.format("URL:%s, Param:%s, Data:(%s)", url, requestBody == null ? "null" : requestBody, inDateString));
        }
        return inBytes;
    }

    /**
     * 填充数据到本地.
     * @param url 请求地址.
     * @param data 存储的数据.
     */
    private boolean fillData(SettingUnit settingUnit, String url, Object data, Map<String, Object> urlParams, Map<String, String> header){

        String tableName = settingUnit.Name(), keyName = settingUnit.Key();

        if(tableName == null || (header == null && settingUnit.NoSave())) return settingUnit.NoSave();

        SqliteDBHelper sqliteDBHelper = dbMap.get(settingUnit.SaveDB()), uploadDb = dbMap.get(UploadObject.UP_LOAD_DB);

        //数据库存在问题 返回存储失败.
        if(sqliteDBHelper == null || uploadDb == null) return false;

        //插入数据到上传表格.
        if (settingUnit.CacheType() == CacheType.CacheUpLoad){

            Object uploadData = data;
            String dummyKey = null;
            Map<String, Object> newParams = null;
            Map<String, Object> whereJSON = null;

            if (data instanceof Map){
                whereJSON = settingUnit.getFilterObject((Map<String, Object>) data);
                Map<String, Object> upJSON = JSONUtil.clone(new HashMap<>(), (Map<String, Object>) data);

                Map<String, Object> newParam = settingUnit.getParamDB((Map<String, Object>) data, sqliteDBHelper);
                if (newParam != null) JSONUtil.fillJSON((Map<String, Object>) data, newParam);

                if (data != null && TextUtils.isEmpty((dummyKey = (String) ((Map<String, Object>) data).get(settingUnit.Key())))
                        && whereJSON == null){ //如果主键不存在先创建一个虚拟的主键,虚拟的主键不能用于上传参数.
                    ((Map<String, Object>) data).put(settingUnit.Key(), dummyKey = MCString.randUUID());
                }

                newParams = JSONUtil.addJSON(urlParams, (Map<String, Object>) data);
                if(!settingUnit.Intercept().isEmpty()){
                    //拦截操作,可能存在重复.
                    Object isIntercept = JexlUtil.exeCode(settingUnit.Intercept(), newParams, sqliteDBHelper);
                    if ((isIntercept instanceof Boolean) && ((Boolean) isIntercept).booleanValue() == true){
                        return false;
                    }
                }
                uploadData = settingUnit.IsDelete() ? newParams : upJSON;
            }

            UploadObject uploadObject = new UploadObject(url, settingUnit, uploadData , header, dummyKey);

            if (newParams != null){
                RelationObject relationObject = relationManager.requestRelation(settingUnit.Update(), settingUnit.ReplaceKey(), JSONUtil.clone(new HashMap<>(), newParams));
                uploadObject.setRelation(relationObject);
            }

            if (!UploadObject.putUpload(uploadObject, uploadDb)){
                return false;
            }

            //插入关联表逻辑, 关联操作不做特别事务, 主要数据成功就成功, 主要数据失败也失败.
            for (String aboutExpress : settingUnit.About()){
                String[] express = aboutExpress.split("=");
                if(express.length == 2){
                    //关联表格.
                    String aboutTable = express[0];
                    boolean isDel = aboutTable.indexOf("<DEL>") == 0;
                    boolean isIns = aboutTable.indexOf("<INS>") == 0;
                    if(isDel || isIns) aboutTable = aboutTable.substring(5);

                    //关联参数表达式.
                    String exp = express[1];
                    //生成参数.
                    Object retValue = JexlUtil.exeCode(exp, newParams, sqliteDBHelper);

                    if(retValue != null){

                        if (retValue instanceof Map){
                            Map<String, Object> jsonValue = (Map<String, Object>)retValue;
                            if (isDel){
                                //对表格进行删除操作.
                                sqliteDBHelper.delByValue(aboutTable, jsonValue);
                            }else{
                                sqliteDBHelper.updateValue(aboutTable, jsonValue, isIns);
                            }
                        }else if (retValue instanceof List){
                            for (int i = 0; i < ((List) retValue).size(); i++) {
                                sqliteDBHelper.updateValue(aboutTable, (Map<String, Object>) ((List) retValue).get(i));
                            }
                        }
                    }
                }
            }

            if (settingUnit.IsDelete()){

                if(newParams != null){
                    if (!settingUnit.Params().isEmpty()){
                        newParams = JexlUtil.exeCodeByParams(settingUnit.Params(), newParams);
                    }
                    Map<String, Object> keyValues = new HashMap<>();
                    keyValues.put("key_value", (String) newParams.get(settingUnit.Key()));

                    //删除本地上传表数据.
                    int uploadDelSize = uploadDb.delByValue(UploadObject.UP_LOAD_TABLE, keyValues);
                    int dataDelSize = sqliteDBHelper.delByValue(settingUnit.Name(), newParams);
                    return saveTransaction(uploadDelSize > 0 && dataDelSize > 0, sqliteDBHelper, uploadDb);
                }
                return true;
            }

            if (whereJSON != null){
                return saveTransaction(sqliteDBHelper.updateValueWhere(tableName, (Map<String, Object>) data, whereJSON), sqliteDBHelper, uploadDb);
            }
        }else{
            //下行插入,将服务数据经过一定运算修改,暂时还不能应对多层嵌套的子母表替换逻辑.
            if (!settingUnit.Return().isEmpty()){
                if (data instanceof Map){
                    data = JexlUtil.exeCodeByParams(settingUnit.Return(), (Map<String, Object>) data, urlParams, sqliteDBHelper);
                }else if (data instanceof List){
                    List<Map<String, Object>> arrayNew = new LinkedList<>();
                    for (int i = 0; i < ((List) data).size(); i++) {
                        arrayNew.add(JexlUtil.exeCodeByParams(settingUnit.Return(), (Map<String, Object>) ((List) data).get(i), urlParams, sqliteDBHelper));
                    }
                    data = arrayNew;
                }
            }
        }

        if(settingUnit.getCluster() != null && data instanceof Map){
            settingUnit.getCluster().setParams((Map<String, Object>) data);
        }

        String[] igs = null;
        if (!settingUnit.Igs().isEmpty()){
            igs = settingUnit.Igs().split(",");
        }
        return saveTransaction(settingUnit.NoSave() || sqliteDBHelper.createTableAndFill(tableName, keyName, data, settingUnit.getCluster(), igs), sqliteDBHelper, uploadDb);
    }

    private boolean saveTransaction(boolean isSuccess, SqliteDBHelper dataDb, SqliteDBHelper uploadDb){
        //保存成功确认事务.
        if (isSuccess) ObjectUtil.batchCall(item -> item.setTransactionSuccessful(), dataDb, uploadDb);
        return isSuccess;
    }

    /**
     * 存储缓存,下行时将服务器的数据保存到本地,上行时将请求的参数存起来,并且将数据分散到各个需要的表格.
     * @param settingUnit 配置单元对象.
     * @param url 服务器请求地址.
     * @param value 存储的数据.
     * @param params 下行为body请求参数, 上行为URL Query 参数.
     * @param header 上传时用的header.
     * @return 存储是否成功.
     */
    private boolean saveCache(SettingUnit settingUnit, String url, Object value, Map<String, ? extends Object> params, Map<String, String> header){

        boolean isSuc = false;
        synchronized (CacheKernel.class) {
            try{
                //开启事务.
                ObjectUtil.batchCall(item -> item.beginTransaction(), dbMap.get(settingUnit.SaveDB()), dbMap.get(UploadObject.UP_LOAD_DB));

                if(settingUnit.CacheType() == CacheType.CacheDownLoad){ //下行数据.

                    MapExtractor dataExtractor = new MapExtractor(value);
                    Object data = dataExtractor.extract(settingUnit.Data());
                    if(data != null){
                        isSuc = fillData(settingUnit, url, data, (Map<String, Object>) params, null);
                    }else{
                        return false;
                    }
                }else{ //上行数据.
                    isSuc = fillData(settingUnit, url, value, (Map<String, Object>) params, header);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                //结束事务.
                ObjectUtil.batchCall(item -> item.endTransaction(), dbMap.get(settingUnit.SaveDB()), dbMap.get(UploadObject.UP_LOAD_DB));
            }
        }
        return isSuc;
    }

    /**
     * 加载离线数据, 根据配置单元的规则逻辑加载指定的数据
     * @param settingUnit 配置单元对象.
     * @param url 服务器请求地址.
     * @param param 请求参数.
     * @return 返回缓存的数据.
     */
    private byte[] loadCache(SettingUnit settingUnit, String url, Map<String, Object> param){

        if(settingUnit == null){
            return null;
        }

        if(param == null){
            param = new HashMap<>();
        }

        Map<String, String> urlParams = settingUnit.getURLParams(url);

        //加入URL 参数.
        JSONUtil.addMap(param, urlParams);

        //从参数中提取分页信息, 如果没有分页大小则为0.
        Integer pageSize = (Integer) param.get("pageSize"), pageNum = (Integer) param.get("pageNum");
        Pager pager = (settingUnit.IsList() || pageSize == null) ? null : new Pager(pageSize, pageNum);

        SqliteDBHelper sqliteDBHelper = dbMap.get(settingUnit.SaveDB());

        Map<String, Object> newParam = settingUnit.getParamObject(param);
        if (newParam != null){
            param = newParam;
        }

        if(sqliteDBHelper == null) return null;

        if(settingUnit.getCluster() != null) settingUnit.getCluster().setParams(param);

        Object object;
        if (settingUnit.NoSave()){
            //只取不存的接口.
            List retArray = new LinkedList();
            Object retValue = JexlUtil.exeCode(settingUnit.Return(), param, sqliteDBHelper);
            if (retValue instanceof List){
                object = retValue;
            }else{
                retArray.add(retValue);
                object = retArray;
            }
        }else{

            JexlUtil.create().put("Setting", settingUnit).build();

            List<Map<String, Object>> returnList;
            if (!settingUnit.SQL().isEmpty()){
                String sql = (String) JexlUtil.exeCode(settingUnit.SQL(), param);
                returnList = sqliteDBHelper.readDataBySql(sql);
            }else{
                returnList = sqliteDBHelper.readDataByCluster(settingUnit.Name(), param, pager, settingUnit.getCluster());
            }

            if (returnList != null && !settingUnit.Return().isEmpty()){

                List<Map<String, Object>> newList = new LinkedList<>();
                for (Map<String, Object> item : returnList){
                    newList.add(JexlUtil.exeCodeByParams(settingUnit.Return(), item, param, sqliteDBHelper));
                }
                returnList = newList;
            }
            object = returnList;
        }

        Map<String, Object> successData;
        if(object instanceof List && !((List) object).isEmpty()){
            successData = JSONUtil.createStandardSuccess(object, pager, settingUnit.IsList(), settingUnit);
        }else {
            if (settingUnit.Null().isEmpty()){
                successData = JSONUtil.createStandardError(200, "无缓存数据");
                successData.put(settingUnit.Data(), new LinkedList<>());
            }else{
                successData = JSONUtil.createStandardNull(settingUnit.Null());
            }
        }

        try {
            if(successData != null){
                String json = JSON.toJSONString(successData);
                return json.getBytes("utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UploadObject> readAllUpload(){
        return uploadManager != null ? uploadManager.readAllUpload() : null;
    }
    public void startUpload(CacheUploadEventer eventer, List<UploadObject> uploadList){
        if (uploadManager != null) uploadManager.startUpload(eventer, uploadList);
    }

    public void stopUpload(){
        if (uploadManager != null) uploadManager.stopUpload();
    }
}
