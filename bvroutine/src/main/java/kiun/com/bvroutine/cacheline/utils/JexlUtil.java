package kiun.com.bvroutine.cacheline.utils;

import com.alibaba.fastjson.JSON;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.JSONExtractor;
import kiun.com.bvroutine.cacheline.data.SqliteDBHelper;
import kiun.com.bvroutine.utils.MCString;

/**
 * Created by kiun_2007 on 2018/8/12.
 */

public class JexlUtil {

    private static Map<String, Object> utils = new HashMap<>();

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    static{
        utils.put("util", new UtilFunctions());
    }

    public static class UtilFunctions {

        static Object helpDb = null;

        public UtilFunctions() {
        }

        public static Map<String, Object> newParams(Map<String, Object> params, HashMap<String, Object> adds, String[] igs) {
            params.putAll(adds);
            for (String remove : igs){
                params.remove(remove);
            }
            return params;
        }

        public static Map<String, Object> newParams(Map<String, Object> params, HashMap<String, Object> adds) {
            return newParams(params, adds, null);
        }

        public static Map<String, Object> newParams(Map<String, Object> params, String[] igs) {
            return newParams(params, null, igs);
        }

        public static Map<String, Object> newParams(Map<String, Object> params) {
            return newParams(params, null, null);
        }

        public Double parseDouble(String value){
            return value == null ? 0 : Double.parseDouble(value);
        }

        public static List toObjects(List array){
            List newArray = new LinkedList();
            for (int i = 0; i < array.size() ; i++) {
                Object object = array.get(i);
                if(!(object instanceof Map)){
                    Map<String, Object> maps = new HashMap<>();
                    maps.put("value", object);
                    maps.put("id", randUUID());
                }
            }
            return newArray;
        }

        /**
         * 获取一个JSON对象的路径的整合数据集合.
         *
         * @param params JSON对象.
         * @param path   路径.
         * @return
         */
        public static List getValues(Map<String, Object> params, String path) {
            JSONExtractor extractor = new JSONExtractor(params, true);
            Object object = extractor.extract(path);
            return null;
        }

        public static HashMap<String, Object> getValues(String table, HashMap<String, Object> param, String[] keys){

            if (helpDb instanceof SqliteDBHelper) {
                SqliteDBHelper helper = (SqliteDBHelper) helpDb;
                List<Map<String, Object>> array = helper.readDataByParams(table, param);
                Map<String, Object> rowJSON = array.get(0);
                if(rowJSON == null){
                    return null;
                }

                HashMap<String, Object> data = new HashMap<>();

                for (int i = 0; i < keys.length; i++) {
                    data.put(keys[i], rowJSON.get(keys[i]));
                }
                return data;
            }
            return null;
        }

        public static HashMap<String, Object> merge(HashMap<String, Object> map1, HashMap<String, Object> map2){
            if (map1 == null && map2 == null){
                return null;
            }

            if(map1 == null || map2 == null){
                return map1 == null ? map2 : map1;
            }

            for (String key : map2.keySet()) {
                map1.put(key, map2.get(key));
            }
            return map1;
        }

        public static List cloneJSONs(Map<String, Object> params, HashMap<String, Object> value, String path, String key) {

            List newArray = new LinkedList();
            List array = getValues(params, path);

            for (int i = 0; i < array.size(); i++) {
                Map<String, Object> mapJSON = (Map<String, Object>) value.clone();
                mapJSON.put(key, array.get(i));
                newArray.add(mapJSON);
            }
            return newArray;
        }

        public static Object queryValue(String table, HashMap<String, Object> values, String key) {

            if (helpDb instanceof SqliteDBHelper) {
                SqliteDBHelper helper = (SqliteDBHelper) helpDb;
                List<Map<String, Object>> array = helper.readDataByParams(table, values);

                if (array != null && array.size() > 0) {
                    if(key == null){
                        return array.get(0);
                    }
                    return array.get(0).get(key);
                }
            }
            return null;
        }

        public static Object query(String table, HashMap<String, Object> values) {
            if (helpDb instanceof SqliteDBHelper) {
                SqliteDBHelper helper = (SqliteDBHelper) helpDb;
                List<Map<String, Object>> array = helper.readDataByParams(table, values);
                if (array != null && array.size() > 0) {
                    return array.get(0);
                }
            }
            return null;
        }

        public static List<Map<String, Object>> queryValues(String table, HashMap<String, Object> values, String distinctKey){

            if (helpDb instanceof SqliteDBHelper) {
                SqliteDBHelper helper = (SqliteDBHelper) helpDb;
                List<Map<String, Object>> array = helper.readDataByParams(table, values, new String[]{String.format("DISTINCT %s",distinctKey)});
                List<Map<String, Object>> newArray = new LinkedList<>();

                for (int i = 0; array != null && i < array.size(); i++) {
                    newArray.add(array.get(i));
                }
                return newArray;
            }
            return null;
        }

        public static String add(String str1, String str2){
            return str1 + str2;
        }

        public static String remove(String src, String rmChar, int max) {

            char delChar = rmChar.charAt(0);
            int i = 0;
            for (i = src.length() - 1; i >= 0 && max > 0; i--, max--) {
                if (src.charAt(i) != delChar) {
                    break;
                }
            }
            return src.substring(0, i + 1);
        }

        public static String removes(String[] srcs, String rmChar, int max) {
            if (srcs.length == 0) {
                return null;
            }

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < srcs.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(remove(srcs[i], rmChar, max));
            }
            return sb.toString();
        }

        public static Map<String, Object> toCamelCase(Map<String, Object> params) {
            return convertName(params, false);
        }

        public static Map<String, Object> toUnderline(Map<String, Object> params) {
            return convertName(params, true);
        }

        public static Map<String, Object> convertName(Map<String, Object> params, boolean isCamelCase) {
            Map<String, Object> newParams = new HashMap<>();
            for (String key : params.keySet()) {
                newParams.put(isCamelCase ? MCString.toUnderlineName(key, true) : MCString.toCamelCase(key), params.get(key));
            }
            return newParams;
        }

        public static boolean exist(String table, HashMap<String, Object> values){
            if (helpDb instanceof SqliteDBHelper) {
                SqliteDBHelper helper = (SqliteDBHelper) helpDb;
                List array = helper.readDataByParams(table, values);

                if (array != null && array.size() > 0) {
                    return true;
                }
            }
            return false;
        }

        public static String randUUID() {
            return MCString.randUUID();
        }

        public static String randCode() {
            return randNum(12);
        }

        public static String randNum(int count) {
            return MCString.randNum(count);
        }
    }

    /**
     * @author: Longjun
     * @Description: 使用commons的jexl可实现将字符串变成可执行代码的功能
     * @date:2016年3月21日 下午1:45:13
     */
    public static Object convertToCode(String jexlExp, Map<String, Object> map) {
        JexlEngine jexlEngine = new JexlEngine();
        Expression e = jexlEngine.createExpression(jexlExp);
        JexlContext jc = new MapContext();

        Map<String, Object> threadMap = threadLocal.get();
        if (threadMap != null){
            map.putAll(threadMap);
        }
        for (String key : map.keySet()) {
            jc.set(key, map.get(key));
        }
        Object retVal = e.evaluate(jc);
        if (null == retVal) {
            return "";
        }
        return retVal;
    }

    public static Map<String, Object> exeCodeByParams(String jexlExp, Map<String, Object> params, Object db)
    {
        return exeCodeByParams(jexlExp, params, null, 0, db);
    }

    public static Map<String, Object> exeCodeByParams(String jexlExp, Map<String, Object> params, Map<String, Object> extra, Object db)
    {
        return exeCodeByParams(jexlExp, params, extra, 0, db);
    }

    public static Map<String, Object> exeCodeByParams(String jexlExp, Map<String, Object> params, int isDown)
    {
        return exeCodeByParams(jexlExp, params, null, isDown, null);
    }

    public static Map<String, Object> exeCodeByParams(String jexlExp, Map<String, Object> params, Map<String, Object> extra, int isDownload, Object db){
        Object object = exeCode(jexlExp, params, extra, isDownload, db);

        if (object instanceof Map){
            return (Map<String, Object>) object;
        }

        if (object instanceof String){
            return (Map<String, Object>) JSON.parse((byte[]) object);
        }
        return null;
    }

    public static Map<String, Object> exeCodeByParams(String jexlExp, Map<String, Object> params){
        return exeCodeByParams(jexlExp, params, null);
    }

    public static Object exeCode(String jexlExp, Map<String, Object> params){
        return exeCode(jexlExp, params, null, 0, null);
    }

    public static Object exeCode(String jexlExp, Map<String, Object> params, Object db){
        return exeCode(jexlExp, params, null,0, db);
    }

    public static Object exeCode(String jexlExp, Object params, Map<String, Object> extra, int isDownload, Object db){

        UtilFunctions.helpDb = db;
        JexlEngine jexlEngine = new JexlEngine();
        Expression e = jexlEngine.createExpression(jexlExp);
        JexlContext jc = new MapContext();
        jc.set("PARAMS", params);
        if (extra != null){
            jc.set("EXTRA", extra);
        }
        jc.set("DOWN", isDownload);

        Map<String, Object> threadMap = threadLocal.get();
        if (threadMap != null){
            for (String key : threadMap.keySet()){
                jc.set(key, threadMap.get(key));
            }
        }

        for (String key : utils.keySet()){
            jc.set(key, utils.get(key));
        }

        Object object = e.evaluate(jc);
        return object;
    }

    public static boolean addUtilObject(String utilName, Object object){
        utils.put(utilName, object);
        return true;
    }

    public static boolean putThreadValue(Map<String, Object> value){
        threadLocal.set(value);
        return true;
    }

    public static ThreadValueBuild create(){
        return new ThreadValueBuild();
    }

    public static class ThreadValueBuild {
        private Map<String, Object> cacheMap = new HashMap<>();

        public ThreadValueBuild put(String key, Object value){
            cacheMap.put(key, value);
            return this;
        }

        public void build(){
            putThreadValue(cacheMap);
        }
    }
}
