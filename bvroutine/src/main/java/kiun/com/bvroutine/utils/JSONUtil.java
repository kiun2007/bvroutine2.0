package kiun.com.bvroutine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {

    public static JSONArray toJSONArray(String jsonArray){
        return JSON.parseArray(jsonArray);
    }

    public static List<String> toStringList(String json){

        JSONArray array = toJSONArray(json);

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            lists.add(array.getString(i));
        }
        return lists;
    }


    public static JSONObject createStandardError(int code, String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        jsonObject.put("throwable", null);
        return jsonObject;
    }

    public static String toJSON(Object javaObject){
        return JSON.toJSONString(javaObject);
    }
}
