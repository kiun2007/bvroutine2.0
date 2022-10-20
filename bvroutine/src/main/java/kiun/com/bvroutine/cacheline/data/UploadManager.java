package kiun.com.bvroutine.cacheline.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.CacheUploadEventer;
import kiun.com.bvroutine.cacheline.data.beans.ContrastObject;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.cacheline.utils.HTTPUtil;

public class UploadManager implements Runnable {

    private Map<String, SqliteDBHelper> dbMap;
    Thread mThread = null;
    CacheUploadEventer uploadEventer;
    boolean isStop = false;
    List<UploadObject> uploadObjects;
    final String allErrorSql = "SELECT * FROM cache_upload_table WHERE relation_id IN (SELECT relation_id FROM cache_upload_table WHERE out_time = 0 AND try_again > 0)";

    //建立新旧key替换缓存机制<替换字段标志,<旧值,新值>>.
    Map<String, String> keyRelapceCacheMap = null;

    //标志等级.
    Map<String, Integer> errRelationLevel = new HashMap<>();

    public UploadManager(Map<String,SqliteDBHelper> dbMap){
        this.dbMap = dbMap;
    }

    public List<UploadObject> readAllUpload(){
        return readAllUpload(false);
    }

    public List<UploadObject> readAllUpload(boolean isUpload){

        return null;
    }

    public boolean startUpload(CacheUploadEventer uploadEventer, List<UploadObject> uploadList){
        if(mThread != null || uploadList == null){
            return false;
        }

        uploadObjects = uploadList;
        mThread = new Thread(this);
        mThread.start();
        this.uploadEventer = uploadEventer;
        return true;
    }

    //清除对照表.
    public void clearKeyCache(){
        keyRelapceCacheMap.clear();
        ContrastObject.useAll(dbMap.get(UploadObject.UP_LOAD_DB));
    }

    public void stopUpload(){
    }

    public boolean putValue(String oldValue, String newValue){

        if(TextUtils.isEmpty(newValue) || TextUtils.isEmpty(oldValue)){
            return false;
        }

        //存入对照表.
        ContrastObject.putContrast(dbMap.get(UploadObject.UP_LOAD_DB), new ContrastObject(oldValue, newValue)); //数据库对照表.
        keyRelapceCacheMap.put(oldValue, newValue);
        return true;
    }

    private String replaceNewValue(String value){

        for (String key : keyRelapceCacheMap.keySet()){
            value = value.replace(key, keyRelapceCacheMap.get(key));
        }
        return value;
    }

    public String pullValue(String oldValue){

        return keyRelapceCacheMap.get(oldValue);
    }

    private Map<String, Object> getJSONByUpload(UploadObject uploadObject, boolean isUpdate){
        SettingUnit settingUnit = uploadObject.getSettingUnit();
        if (settingUnit != null && !"".equals(isUpdate ? settingUnit.Update() : settingUnit.ReplaceKey())) {
            return (Map<String, Object>) JSON.parse(isUpdate ? settingUnit.Update() : settingUnit.ReplaceKey());
        }
        return null;
    }

    public List<Map<String, Object>> getErrorUploads(){
        return dbMap.get(UploadObject.UP_LOAD_DB).readBySql(allErrorSql);
    }

    public SqliteDBHelper getDB(SettingUnit settingUnit){
        return settingUnit.getDbHelper();
    }

    @Override
    public void run() {

        List<UploadObject> uploadList = uploadObjects;

        //加载内存对照表.
        keyRelapceCacheMap = ContrastObject.allContrastMap(dbMap.get(UploadObject.UP_LOAD_DB));

        int progress = 0;
        UploadObject lastUpload = null;
        for (UploadObject uploadObject : uploadList){

            progress ++;
            lastUpload = uploadObject;
            Map<String, Object> replaceJSON = getJSONByUpload(uploadObject, false);
            SettingUnit settingUnit = uploadObject.getSettingUnit();
            if (settingUnit == null){
                break;
            }

            String requestBody = (String) uploadObject.getParam();
            requestBody = replaceNewValue(requestBody);

            if (uploadEventer != null){
                uploadEventer.beforeUpload(uploadObject, this);
            }

            Integer level = null;
            //设置跳过.
            if ((level = errRelationLevel.get(uploadObject.getRelation().getId())) != null){
                if (level.intValue() < uploadObject.getRelation().getLevel()){
                    continue;
                }
            }

            Map<String, Object> retValue = null;
            uploadObject.tryUpload();
            if (!settingUnit.PutFile()){
                //提交其他请求.
                HTTPUtil.Code code = new HTTPUtil.Code(1001);
                retValue = JSON.parseObject(HTTPUtil.postURL(uploadObject.getUrl(), requestBody, uploadObject.getHeader(), code));
            }else{
                //多表单提交
                SqliteDBHelper helper = settingUnit.getDbHelper();
                retValue = HTTPUtil.upload(uploadObject.getUrl(), uploadObject.getHeader(), helper.readRowByPK(settingUnit.Name(), settingUnit.Key(), requestBody, null));
            }

            //上传之后修改其他表格关联的数据.
            if ("200".equals(String.valueOf(retValue.get("code")))){

                Map<String, Object> retData = (Map<String, Object>) retValue.get("data");
                uploadObject.upload();

                if (retData != null) {
                    String setting = (String) retData.get(settingUnit.Key());
                    putValue(uploadObject.getKeyValue(), setting);
                }
            } else {
                //服务器返回失败.
                errRelationLevel.put(uploadObject.getRelation().getId(), uploadObject.getRelation().getLevel());
                uploadObject.setErrorMsg(retValue != null ? retValue.toString() : "unkown error!");
            }

            if(isStop) break;

            //修改入库.
            dbMap.get(UploadObject.UP_LOAD_DB).updateValue(UploadObject.UP_LOAD_TABLE, uploadObject.toJSON());

            if (uploadEventer != null){
                if (!uploadEventer.onUploadProgress(uploadObject, progress, uploadList.size(), new JSONExtractor(retValue), this))
                    break;
            }
        }

        boolean isComplete = progress == uploadList.size();
        if (uploadEventer != null){
            uploadEventer.onUploadStop(lastUpload, isComplete, this);
        }

        if(isComplete){
            clearKeyCache();
        }

        isStop = false;
        mThread = null;
    }
}
