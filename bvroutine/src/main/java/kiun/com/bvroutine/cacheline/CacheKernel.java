package kiun.com.bvroutine.cacheline;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.body.BaseBodyBuilder;
import kiun.com.bvroutine.cacheline.data.MapExtractor;
import kiun.com.bvroutine.cacheline.data.SqliteDBHelper;
import kiun.com.bvroutine.cacheline.data.UploadRelationManager;
import kiun.com.bvroutine.cacheline.data.beans.Pager;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.cacheline.utils.JSONUtil;
import kiun.com.bvroutine.cacheline.utils.JexlUtil;
import kiun.com.bvroutine.cacheline.utils.ParamUtil;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.ByteUtil;
import kiun.com.bvroutine.utils.MD5Util;
import kiun.com.bvroutine.utils.ObjectUtil;
import retrofit2.Invocation;

/**
 * 缓存核心业务类.
 */
class CacheKernel {

    public final String TAG = this.getClass().getSimpleName();
    final UploadRelationManager relationManager;
    private Context context;

    /**
     * 根据配置接口类型创建缓存核心.
     * @param context APP上下文.
     */
    CacheKernel(Context context) throws Exception {

        BodyConvertBridge.loadPackage(BaseBodyBuilder.class.getPackage().getName());
        this.context = context;
        relationManager = new UploadRelationManager(SettingUnit.getUploadDb());
    }

    public void release(){
    }

    /**
     * 匹配缓存数据.
     * @param invocation 请求时方法描述
     * @param header 请求头部信息.
     * @param inBytes 请求Body内容.
     * @return 返回匹配后的数据.
     * @throws IOException 可能存在字符串转化错误.
     */
    public byte[] cacheIn(Invocation invocation,Map<String, String> header, byte[] inBytes) throws IOException, InterruptedException {

        String inDateString = null;
        SettingUnit settingUnit = SettingUnit.createByInvocation(invocation);

        if(settingUnit != null){
            if(inBytes != null){
                if (settingUnit.CacheType() == CacheType.CacheDownLoad){
                    inDateString = new String(inBytes, "utf-8");

                    //服务器返回实际数据.
                    Map<String, Object> jsonObject = (Map<String, Object>) JSONUtil.getJSONData(inDateString);

                    if (jsonObject != null)
                        saveCache(settingUnit, invocation, jsonObject);
                }
            }else{
                //上行离线.
                if(settingUnit.CacheType() == CacheType.CacheUpLoad){

                    //jsonParams body参数, urlParam URL 参数.
                    boolean isSuccess = saveCache(settingUnit, invocation, null);
                    if(isSuccess){
                        List<Map<String, Object>> array = new LinkedList<>();
                        if(settingUnit.Return().isEmpty()){
//                            if(jsonParams != null) array.add(jsonParams);
                        }else{
                            array.add(JexlUtil.exeCodeByParams(settingUnit.Return(), null, settingUnit.getDbHelper()));
                        }
                        String jsonString = JSON.toJSONString(JSONUtil.createStandardSuccess(array.isEmpty() ? null : array.get(0), null, false, settingUnit));
                        inBytes = jsonString.getBytes(StandardCharsets.UTF_8);
                    }
                }else if (settingUnit.CacheType() == CacheType.CacheDownLoad){ //下行离线.
                    inBytes = loadCache(settingUnit, invocation);
                }
            }
        }
        return inBytes;
    }

    /**
     * 填充数据到本地.
     * @param settingUnit 请求地址.
     * @param data 存储的数据.
     */
    private boolean fillData(SettingUnit settingUnit, Object data, Map<String, Object> param){

        String tableName = settingUnit.Name(), keyName = settingUnit.Key();
        if(tableName == null || settingUnit.NoSave()) return settingUnit.NoSave();

        SqliteDBHelper sqliteDBHelper = settingUnit.getDbHelper(), uploadDb = settingUnit.getUploadHelper();
        //数据库存在问题 返回存储失败.
        if(sqliteDBHelper == null || uploadDb == null) return false;

        //插入数据到上传表格.
        if (settingUnit.CacheType() == CacheType.CacheUpLoad){
            Boolean result = CacheUtil.fillUpload(data, settingUnit, this.relationManager);
            if (result != null) return result;
        }else{
            //下行插入,将服务数据经过一定运算修改,暂时还不能应对多层嵌套的子母表替换逻辑.
            if (!settingUnit.Return().isEmpty()){
                if (data instanceof Map){
                    data = JexlUtil.exeCodeByParams(settingUnit.Return(), (Map<String, Object>) data, param, sqliteDBHelper);
                }else if (data instanceof List){
                    List<Map<String, Object>> arrayNew = new LinkedList<>();
                    for (int i = 0; i < ((List) data).size(); i++) {
                        arrayNew.add(JexlUtil.exeCodeByParams(settingUnit.Return(), (Map<String, Object>) ((List) data).get(i), param, sqliteDBHelper));
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
        return CacheUtil.saveTransaction(settingUnit.NoSave() || sqliteDBHelper.createTableAndFill(tableName, keyName, data, settingUnit.getCluster(), igs), sqliteDBHelper, uploadDb);
    }

    /**
     * 存储缓存,下行时将服务器的数据保存到本地,上行时将请求的参数存起来,并且将数据分散到各个需要的表格.
     * @param settingUnit 配置单元对象.
     * @param invocation 服务器请求地址.
     * @return 存储是否成功.
     */
    private boolean saveCache(SettingUnit settingUnit, Invocation invocation, Object value){

        Object params = ParamUtil.paramFormInvocation(invocation);
        boolean isSuc = false;
        synchronized (CacheKernel.class) {
            try{
                //开启事务.
                ObjectUtil.batchCall(item -> item.beginTransaction(), settingUnit.getDbHelper(), settingUnit.getUploadHelper());

                if(settingUnit.CacheType() == CacheType.CacheDownLoad){ //下行数据.

                    MapExtractor dataExtractor = new MapExtractor(value);
                    Object data = dataExtractor.extract(settingUnit.Data());
                    if(data != null){
                        isSuc = fillData(settingUnit, data, (Map<String, Object>) params);
                    }else{
                        return false;
                    }
                }else{
                    if (!settingUnit.Data().isEmpty()){
                        MapExtractor dataExtractor = new MapExtractor(params);
                        params = dataExtractor.extract(settingUnit.Data());
                    }
                    //上行数据保存.
                    isSuc = fillData(settingUnit, params, null);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                //结束事务.
                ObjectUtil.batchCall(item -> item.endTransaction(), settingUnit.getDbHelper(), settingUnit.getUploadHelper());
            }
        }
        return isSuc;
    }



    /**
     * 加载离线数据, 根据配置单元的规则逻辑加载指定的数据
     * @param settingUnit 配置单元对象.
     * @param invocation 服务器请求地址.
     * @return 返回缓存的数据.
     */
    private byte[] loadCache(SettingUnit settingUnit, Invocation invocation){

        if(settingUnit == null){
            return null;
        }

        Map<String, Object> param = ParamUtil.paramFormInvocation(invocation);

        //从参数中提取分页信息, 如果没有分页大小则为0.
        Integer pageSize = (Integer) param.get("pageSize"), pageNum = (Integer) param.get("pageNum");
        Pager pager = (settingUnit.IsList() || pageSize == null) ? null : new Pager(pageSize, pageNum);

        SqliteDBHelper sqliteDBHelper = settingUnit.getDbHelper();

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
}
