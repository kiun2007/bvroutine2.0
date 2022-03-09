package kiun.com.bvroutine.cacheline.data.beans;

import android.content.ContentValues;


import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.SqliteDBHelper;

public class ContrastObject {

    private String id;
    private String keyName;
    private String local;
    private String host;
    private int useTime = 0;

    public ContrastObject(String local, String host){
        this.host = host;
        this.local = local;
    }

    public JSONObject toJSON(){
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", id);
        jsonMap.put("name", keyName);
        jsonMap.put("local", local);
        jsonMap.put("host", host);
        jsonMap.put("useTime", useTime);
        return new JSONObject(jsonMap);
    }

    public static void putContrast(SqliteDBHelper dbHelper, ContrastObject contrast){
        dbHelper.createTableAndFill("contrast_table","id", contrast.toJSON(), null);
    }

    public static Map<String, String> allContrastMap(SqliteDBHelper dbHelper){

        List<Map<String, Object>> array = dbHelper.readDataByWhere("contrast_table", "useTime=0", null);
        Map<String, String> map = new HashMap<>();

        for (int i = 0; array != null && i < array.size(); i++) {
            Map<String, Object> item = array.get(i);
            map.put((String) item.get("local"), (String) item.get("host"));
        }
        return map;
    }

    public static void useAll(SqliteDBHelper dbHelper){

        if (!dbHelper.isWithTable("contrast_table")){
            return;
        }

        ContentValues values = new ContentValues();
        values.put("useTime", new Date().getTime());
        dbHelper.updateValue("contrast_table", "useTime=0", values);
    }
}
