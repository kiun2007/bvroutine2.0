package kiun.com.bvroutine.cacheline.data;

import android.text.TextUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.beans.RelationObject;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.cacheline.data.beans.XJSONObject;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.utils.ByteUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.MD5Util;
import kiun.com.bvroutine.utils.RetrofitUtil;
import retrofit2.Call;

/**
 * 上传关系管理.
 */
public class UploadRelationManager {

    SqliteDBHelper dbHelper;
    Map<String, Map<String, RelationObject>> relationMap = new HashMap<>();
    final String RELATION_TABLE = "relation_table";

    private void putValue(String key, String value, String relationId, int level){
        putValue(key, value, relationId, level,false);
    }

    private void putValue(String key, String value, String relationId, int level, boolean init){
        Map<String, RelationObject> keyIdMap = relationMap.get(key);
        if(keyIdMap == null){
            relationMap.put(key, keyIdMap = new HashMap<>());
        }
        keyIdMap.put(value, new RelationObject(relationId, level));

        if(!init){
            JSONObject mapJSON = new JSONObject();
            try {
                mapJSON.put("id", ByteUtil.bytesToHexString(MD5Util.MD5(key+value+relationId)));
                mapJSON.put("use", 0);
                mapJSON.put("key", key);
                mapJSON.put("value", value);
                mapJSON.put("level", level);
                mapJSON.put("relation_id", relationId);
                dbHelper.createTableAndFill(RELATION_TABLE, "id", mapJSON, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private RelationObject pullValue(String key, String value){
        Map<String, RelationObject> valueId = relationMap.get(key);
        if (valueId != null){
            if (valueId.containsKey(value)){
                return valueId.get(value);
            }
        }
        return null;
    }

    public UploadRelationManager(SqliteDBHelper dbHelper){
        this.dbHelper = dbHelper;
        if(dbHelper.isWithTable(RELATION_TABLE)){
            //初始化关系MAP.
            List<Map<String, Object>> array = dbHelper.readDataByWhere(RELATION_TABLE, "use='0'", null);
            for (int i = 0; i < array.size(); i++) {
                Map<String, Object> itemJSON = array.get(i);
                String key, value, relationId;
                if (itemJSON != null && (key = (String) itemJSON.get("key")) != null
                        && (value = (String) itemJSON.get("value")) != null
                        && (relationId = (String) itemJSON.get("relation_id")) != null){
                    putValue(key, value, relationId, (Integer) itemJSON.get("level"));
                }
            }
        }
    }

    //查找关系标志.
    private RelationObject findRelationId(Map<String, Object> params, String replaceJSONStr){
        final RelationObject[] relationId = new RelationObject[1];

        //主动请求关系查找.
        XJSONObject replaceJSON = XJSONObject.create(replaceJSONStr);
        if(replaceJSON != null){
            replaceJSON.foreach(((key, value) -> {
                MapExtractor extractor = new MapExtractor(params, true);
                Object object = extractor.extract(key);

                if (key.indexOf(".") > 0){
                    int lastIndex = key.lastIndexOf(".");
                    String pathKey = key.substring(lastIndex + 1, key.length());
                    if (object instanceof JSONArray){
                        for (int i = 0; i < ((JSONArray) object).length(); i++) {
                            String itemValue = ((JSONArray) object).optString(0);
                            if((relationId[0] = pullValue(pathKey, itemValue)) != null) break;
                        }
                    }else{
                        relationId[0] = pullValue(pathKey, (String) object);
                    }
                }else {
                    relationId[0] = pullValue(key, (String) object);
                }
                return relationId[0] == null;
            }));
        }

        if(relationId[0] == null){
            //被动查找.
            for (String key : params.keySet()){
                Object value = params.get(key);
                if (value != null){
                    relationId[0] = pullValue(key, value.toString());
                }
            }
        }

        if(relationId[0] == null){
            return new RelationObject(MCString.randUUID(), 0);
        }
        return relationId[0];
    }

    public RelationObject requestRelation(String uploadJSONStr, String replaceJSONStr, Map<String, Object> params){

        XJSONObject updateJSON = XJSONObject.create(uploadJSONStr);

        //找到关系标志.
        RelationObject relationObject = findRelationId(params, replaceJSONStr);

        //提供关系者,也可能存在使用其他关系.
        if (updateJSON != null){
            updateJSON.<String>foreach((key, value)->{
                if(!TextUtils.isEmpty((CharSequence) params.get(key))){
                    //存入关系标志,关系标志接力.
                    putValue(key, (String) params.get(key), relationObject.getId(), relationObject.getLevel() + 1);
                }
                return true;
            });
        }
        return relationObject;
    }

    public SqliteDBHelper getDbHelper() {
        return dbHelper;
    }

    private List<UploadObject> toUploadObjects(List<Map<String, Object>> array){

        List<UploadObject> list = new LinkedList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                Map<String, Object> item = array.get(i);
                if(item != null){
                    list.add(new UploadObject(item));
                }
            }
        }
        return list;
    }

    /**
     * 获取符合条件的上传数据
     * @param isUpload 是否已经上传成功
     * @return
     */
    public List<UploadObject> readUpload(boolean isUpload){
        List<Map<String, Object>> array = getDbHelper().readDataByWhere(UploadObject.UP_LOAD_TABLE, isUpload ? "out_time>0" : "out_time=0", "in_time");
        return toUploadObjects(array);
    }

    /**
     * 提交保存对象
     * @param uploadObject
     * @return
     * @throws Exception
     */
    public boolean upload(UploadObject uploadObject) throws Exception{
        Call netCall = uploadObject.getNetCall();
        if (netCall != null){
            Object body = netCall.execute().body();
            if (body instanceof DataWrap){
                DataWrap wrap = (DataWrap) body;
                if (wrap.isSuccess()){
                    return true;
                }
                throw new Exception(wrap.getMsg());
            }
        }
        return false;
    }
}