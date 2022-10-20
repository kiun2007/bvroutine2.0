package kiun.com.bvroutine.cacheline.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.WhereBuilder;
import kiun.com.bvroutine.cacheline.data.beans.Pager;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;
import kiun.com.bvroutine.utils.MCString;

public class JSONUtil {

    private static class CaseHashMap extends HashMap{

        @Override
        public boolean containsKey(Object key) {
            if (key instanceof String){
                key = ((String) key).toLowerCase();
            }
            return super.containsKey(key);
        }

        @Override
        public Object get(Object key) {
            if (key instanceof String){
                key = ((String) key).toLowerCase();
            }
            return super.get(key);
        }

        @Override
        public Object put(Object key, Object value) {
            if (key instanceof String){
                key = ((String) key).toLowerCase();
            }
            return super.put(key, value);
        }
    }

    /**
     * 将JSON数组重新组装,方便通过主键查找想要的数据.
     * @param array JSON数组.
     * @param mapKey 检索主键.
     * @return 重新组装的JSON Map.
     */
    public static Map<String, Map<String, Object>> assembleJSONMap(List<Map<String, Object>> array, String mapKey){

        Map<String, Map<String, Object>> map = new CaseHashMap();

        for (Map<String, Object> jsonObject : array) {
            String keyValue;
            if(jsonObject != null && (keyValue = (String) jsonObject.get(mapKey)) != null){
                map.put(keyValue, jsonObject);
            }
        }
        return map;
    }

    public static<T extends Map<String, Object>>T clone(T des,  Map<String, Object> src){
        if (src == null){
            return null;
        }

        for (String key : src.keySet()){
            des.put(key, src.get(key));
        }
        return des;
    }

    public static void fillJSON(Map<String, Object> src, Map<String, Object> des){
        src.clear();
        addJSON(src, des);
    }

    public static Map<String, Object> addJSON(Map<String, Object> src, Map<String, Object> adds){

        if(adds == null){
            return src;
        }
        if (src == null){
            return adds;
        }

        src.putAll(adds);
        return src;
    }

    public static void addMap(Map<String, Object> src, Map addMap){
        if(src == null || addMap == null){
            return;
        }
        addJSON(src, addMap);
    }

    public static Map<String, Object> copyJSON(Map<String, Object> src, Map<String, Object> desc, String[] igs){

        Map<String, Object> values = new HashMap<>();
        for (String key : src.keySet()) {
            if(MCString.isWith(key, igs)){
                continue;
            }
            values.put(key, src.get(key));
        }

        values.putAll(desc);
        return values;
    }

    public static Map<String, Object> toJSONByBody(String body){
        if (body.indexOf("=") > 0){
            Map<String, Object> maps = new HashMap<>();
            String[] paramItems = body.split("&");
            for (String paramItem : paramItems) {
                String[] keyValue = paramItem.split("=");
                if (keyValue != null && keyValue.length == 2){
                    maps.put(keyValue[0], keyValue[1]);
                }
            }
            return maps;
        }
        return new HashMap<>();
    }

    private static void removeAndCall(Map<String, Object> data, String find, ObjectUtil.PutVoidCall<String, String> caller){

        Map<String, Object> addList = new HashMap<>();
        for (String key : data.keySet()) {
            String newKey = null;
            boolean isWhere = key.indexOf(find) == 0;
            if (isWhere) newKey = key.substring(5);

            if (isWhere){
                if(caller != null){
                    try {
                        caller.call(newKey, key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                addList.put(newKey, data.get(key));
            }
        }

        for (String key : addList.keySet()){
            data.remove(find + key);
            data.put(key, addList.get(key));
        }
    }

    public static String getWhereJSON(Map<String, Object> data){

        WhereBuilder builder = WhereBuilder.create();
        removeAndCall(data, "<WHE>", ((input, output) -> {
            builder.addParam(input, data.get(output));
        }));
        return builder.build(" AND ");
    }

    public static Map<String, Object> createStandardNull(String nullString){

        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("success", true);
        jsonObject.put("code", null);
        jsonObject.put("message", null);
        jsonObject.put("throwable", null);
        jsonObject.put("data", JSON.parse(nullString));
        return jsonObject;
    }

    /**
     * 创建标准的正确数据返回.
     * @param data 要被载入的数据.
     * @param pager 页面信息对象.
     * @param isList 强制标志为列表.
     * @return 可以直接使用的数据.
     */
    public static Map<String, Object> createStandardSuccess(Object data, Pager pager, boolean isList, SettingUnit settingUnit){

        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("code", 200);
        if(data != null && settingUnit != null && !settingUnit.Data().isEmpty()) {
            jsonObject.put(settingUnit.Data(), data);
        }
        return jsonObject;
    }

    public static Map<String, Object> createStandardError(int code, String message){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("success", false);
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        jsonObject.put("throwable", null);
        jsonObject.put("rows", new LinkedList<>());
        return jsonObject;
    }

    /**
     * 将字符串转化为JSON对象或JSON数组.
     * @param data JSON格式的字符串.
     * @return 返回的JSON对象.
     */
    public static Object getJSONData(String data) {
        return JSON.parse(data);
    }

    public static String getJSONKey(Map<String, Object> data){

        final String[] mKey = new String[1];
        removeAndCall(data, "<KEY>", ((newKey, key) -> {
            mKey[0] = newKey;
        }));
        return mKey[0];
    }
}
