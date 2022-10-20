package kiun.com.bvroutine.cacheline.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.beans.Pager;
import kiun.com.bvroutine.cacheline.data.beans.TableCluster;
import kiun.com.bvroutine.cacheline.utils.JSONUtil;
import kiun.com.bvroutine.cacheline.utils.ObjectUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.LogUtil;
import kiun.com.bvroutine.utils.MCString;

public class SqliteDBHelper {

    private static final String TAG = "SqliteDBHelper";
    private SQLiteDatabase db;
    public SqliteDBHelper(String filePath){
        db = SQLiteDatabase.openOrCreateDatabase(filePath, null);
    }

    public List<Map<String, Object>> readDataBySql(String sql){
        Cursor cursor = db.rawQuery(sql, null);
        return readJSONByCursor(cursor);
    }

    public List<Map<String, Object>> readDataByCluster(String tableName, Map<String, Object> params, Pager pager, TableCluster cluster){

        if (cluster != null && cluster.pathKeys().length > 0){
            List<Map<String, Object>> array = readDataByParams(tableName, params, pager, null);
            for (int i = 0; array != null && i < array.size(); i++) {

                Map<String, Object> object = array.get(i);
                for (String key : cluster.pathKeys()){
                    String clustrTable = cluster.getTableName(key);

                    Map<String, Object> clustrParams = new HashMap<>();
                    //参数填入子表主键.
                    clustrParams.put(cluster.getNickOrName(), object.get(cluster.getKeyName()));
                    Map<String, Object> extraJSON = cluster.getExtraJSON();
                    if (extraJSON != null){
                        JSONUtil.addJSON(clustrParams, extraJSON);
                    }

                    List<Map<String, Object>> clustrArray = readDataByParams(clustrTable, JSONUtil.copyJSON(params, clustrParams, null), pager, null);
                    object.put(key, clustrArray);
                }
            }
            return array;
        }

        return readDataByParams(tableName, params, pager, null);
    }

    public List<Map<String, Object>> readDataByParams(String tableName, Map<String, Object> params, Pager pager, String[] columns){

        Map<String, Map<String, Object>> tableFields = getTableFields(tableName);

        Cursor cursor;
        //无数据.
        if(tableFields == null){
            return null;
        }

        StringBuffer querySQL = new StringBuffer(1024);

        for (String columnName : params.keySet()) {

            String jsonKey = columnName;
            boolean isNot = columnName.indexOf("<NOT>") == 0;
            if (isNot) columnName = columnName.substring(5);

            if (tableFields.containsKey(columnName)){
                Object object = params.get(jsonKey);
                if(object != null && object != null){
                    if(!isNot && (object instanceof String)){
                        if(!"".equals(object)) {
                            if(object.toString().indexOf(",") > 0){
                                String[] chars = object.toString().split(",");
                                StringBuffer strBuffer = new StringBuffer(200);
                                strBuffer.append("(" + columnName + " like ");
                                for (int i = 0; i < chars.length; i++) {
                                    strBuffer.append(String.format("'%s%%'", chars[i]));
                                    if(i < chars.length - 1){
                                        strBuffer.append(" OR ");
                                    }
                                }
                                strBuffer.append(")");
                                querySQL.append(strBuffer);
                                querySQL.append(" AND ");
                            }else if (object.toString().indexOf("%") > -1 || object.toString().indexOf("_") > -1){
                                querySQL.append(String.format("%s like '%s' AND ", columnName, object.toString()));
                            }else{
                                querySQL.append(String.format("%s like '%%%s%%' AND ", columnName, object.toString()));
                            }
                        }
                    }else{
                        querySQL.append(String.format("%s %s '%s' AND ", columnName, isNot ? "!=" : "=", object.toString()));
                    }
                }
            }
        }

        String sqlWhere = querySQL.length() == 0 ? null : querySQL.substring(0, querySQL.length() - 4);

        if(pager != null){
            //获取分页信息.
            String sqlCount = "SELECT COUNT(*) FROM " + tableName + (sqlWhere == null ? ";" : (" WHERE " + sqlWhere + ";"));
            cursor = db.rawQuery(sqlCount, null);
            List<Map<String, Object>> countValues = readJSONByCursor(cursor);
            pager.setRowSize((Integer) countValues.get(0).get("COUNT(*)"));
        }
        LogUtil.Log(sqlWhere);

        cursor = db.query(tableName, columns, sqlWhere, null,null,null, "row_input_time", pager == null ? null : pager.limit());
        List<Map<String, Object>> array = readJSONByCursor(cursor);
        return array;
    }

    public boolean isWithTable(String tableName){
        return getTableFields(tableName) != null;
    }

    public List<Map<String, Object>> readBySql(String sql){
        return readJSONByCursor(db.rawQuery(sql, null));
    }

    public List<Map<String, Object>> readDataByWhere(String tableName, String where, String orderBy){
        if (!isWithTable(tableName)){
            return null;
        }
        Cursor cursor = db.query(tableName, null, where, null,null,null, orderBy, null);
        return readJSONByCursor(cursor);
    }

    public void fillValue(String tableName, String where, String key, String value){

        ContentValues values = new ContentValues();
        values.put(key, value);
        db.update(tableName, values, where, null);
    }

    public List<Map<String, Object>> readDataByParams(String tableName, Map<String, Object> params){
        return readDataByParams(tableName, params, null, null);
    }

    public List<Map<String, Object>> readDataByParams(String tableName, Map<String, Object> params, String[] columns){
        return readDataByParams(tableName, params, null, columns);
    }

    private Map<String, Map<String, Object>> getTableFields(String tableName){

        //获取表格结构.
        Cursor cursor = db.rawQuery(String.format("PRAGMA table_info(%s)", tableName), new String[]{});
        List<Map<String, Object>> array = readJSONByCursor(cursor);

        if(array.size() == 0){
            return null;
        }

        return JSONUtil.assembleJSONMap(array, "name");
    }

    public Map<String, Map<String, Object>> createTableBySample(String tableName, String keyName, Object object, String[] ignoreKey){
        //数据样品.
        Map<String, Object> sample = null;

        //提取样品数据.
        if (object instanceof List){
            if(((List) object).size() > 0){
                sample = (Map<String, Object>) ((List) object).get(0);
            }
        }else if (object instanceof Map){
            sample = (Map<String, Object>) object;
        }

        if(sample == null){
            Log.e(TAG, "Object Type is error!");
            return null;
        }

        Map<String, Map<String, Object>> tableFields = getTableFields(tableName);

        //表不存在.
        if(tableFields == null){
            createTable(sample, tableName, keyName, ignoreKey);
            tableFields = getTableFields(tableName);
        }else{
            //表格存在,.
            if(alterTable(sample, tableName, tableFields, ignoreKey)){
                tableFields = getTableFields(tableName);
            }
        }

        return tableFields;
    }

    /**
     * 创建或修改表并填充数据.
     * @param tableName 表格名称.
     * @param keyName 表格主键.
     * @param object 需要填充的数据.
     * @param cluster 表关联关系.
     */
    public boolean createTableAndFill(String tableName, String keyName, Object object, TableCluster cluster, String[] ignoreKey){

        //复杂关联表.
        if (cluster != null){
            //创建主表.
            String[] allKeys = cluster.pathKeys();
            if(!createTableAndFill(tableName, keyName, object, null, allKeys)){
                return false;
            }
            ExtractorBase extractorBase = new MapExtractor(object, true);

            for (String pathKey : allKeys){
                tableName = cluster.getTableName(pathKey);
                keyName = cluster.getKeyName(pathKey);
                String expressIn = String.format("%s.%s", pathKey, cluster.getKeyName());
                if(cluster.getRootKeyNick() != null){
                    expressIn = String.format("%s.%s(%s)", pathKey, cluster.getKeyName(), cluster.getRootKeyNick());
                }

                String path = String.format("_root.%s[i]", pathKey);

                object = extractorBase.extract(path, expressIn);
                Map<String, Object> extraJSON;

                if(object instanceof List){

                    for (int i = 0; i < ((List) object).size(); i++) {
                        if(((List) object).get(i) == null){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                ((List) object).remove(i--);
                            }
                        }
                    }

                    if (((List) object).size() == 0){
                        continue;
                    }
                    if ((extraJSON = cluster.getExtraJSON()) != null){
                        for (int i = 0; i < ((List) object).size(); i++) {
                            JSONUtil.addJSON((Map<String, Object>) ((List) object).get(i), extraJSON);
                        }
                    }
                }

                if(!createTableAndFill(tableName, keyName, object, null, ignoreKey)){
                    return false;
                }
            }
        }else{

            boolean isSuccess = true;
            String where = null;
            if (object instanceof Map){
                where = JSONUtil.getWhereJSON((Map<String, Object>) object);
            }

            Map<String, Map<String, Object>> tableFields = createTableBySample(tableName, keyName, object, ignoreKey);

            if(tableFields == null){
                return false;
            }

            if (object instanceof List){
                for (int i = 0; i < ((List) object).size() ; i++) {
                    if(!fillDataToTable(tableName, (Map<String, Object>) ((List) object).get(i), tableFields)){
                        isSuccess = false;
                    }
                }
            }else if (object instanceof Map){
                isSuccess = fillDataToTable(tableName, (Map<String, Object>) object, tableFields, where);
            }
            return isSuccess;
        }
        return true;
    }

    public void createTableAndFill(String tableName, String keyName, Object object, TableCluster cluster){
        createTableAndFill(tableName, keyName, object, cluster, null);
    }

    private boolean alterTable(Map<String, Object> object, String tableName, Map<String, Map<String, Object>> tableFields, String[] ignoreKeys){

        boolean isAlter = false;

        for (String columnName : tableFields.keySet()) {

            if(!tableFields.containsKey(columnName) && !MCString.isWith(columnName, ignoreKeys)){
                StringBuffer sb = new StringBuffer(1024);
                sb.append(String.format("ALTER TABLE %s ADD ", tableName));
                sb.append(String.format(getFieldType(object.get(columnName), false, ";"), columnName));
                db.execSQL(sb.toString());
                isAlter = true;
            }
        }

        return isAlter;
    }

    private String getFieldType(Object object){
        return getFieldType(object, true, ",");
    }

    private String getFieldType(Object object, boolean isPrefix){
        return getFieldType(object, isPrefix, ",");
    }

    private String getFieldType(Object object, boolean isPrefix, String prefixString) {
        if(object instanceof Integer || object instanceof Long || object instanceof BigInteger){
            return (isPrefix ? prefixString : "") + "'%s' INTEGER" + (isPrefix ? "" : prefixString);
        }else if (object instanceof Number){
            return (isPrefix ? prefixString : "") + "'%s' FLOAT" + (isPrefix ? "" : prefixString);
        }else if (object instanceof byte[]){
            return (isPrefix ? prefixString : "") + "'%s' BLOB" + (isPrefix ? "" : prefixString);
        } else {
            return (isPrefix ? prefixString : "") + "'%s' TEXT" + (isPrefix ? "" : prefixString);
        }
    }

    /**
     * 创建表格
     * @param object
     * @param tableName
     * @param keyName
     * @param ignoreKeys
     */
    private void createTable(Map<String, Object> object, String tableName, String keyName, String[] ignoreKeys){

        StringBuffer sb = new StringBuffer(2048);
        sb.append(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));
        sb.append(String.format("'%s' TEXT PRIMARY KEY,'row_input_time' INTEGER", keyName));

        List<String> columns = new LinkedList<>();

        for (String columnName : object.keySet()) {

            if(columnName.equals(keyName) || MCString.isWith(columnName, ignoreKeys)){
                continue;
            }

            if (columns.indexOf(columnName.toLowerCase()) < 0){
                sb.append(String.format(getFieldType(object.get(columnName)), columnName));
                columns.add(columnName.toLowerCase());
            }
        }
        sb.append(");");
        db.execSQL(sb.toString());
    }

    private String getPKByFields(Map<String,Map<String, Object>> tableColumns){
        for (String key : tableColumns.keySet()){
            if ((Integer) tableColumns.get(key).get("pk") == 1){
                return (String) tableColumns.get(key).get("name");
            }
        }
        return null;
    }

    /**
     * 直接存入数据.
     * @param tableName 表名.
     * @param data 插入的数据.
     */
    public boolean updateValue(String tableName, Map<String, Object> data, boolean isReplace){
        Map<String,Map<String, Object>> tableColumns = getTableFields(tableName);

        String dataKey = JSONUtil.getJSONKey(data);
        if (tableColumns == null) {
            createTable(data, tableName, dataKey, null);
            tableColumns = getTableFields(tableName);
        }
        String pkName = getPKByFields(tableColumns);
        String where = JSONUtil.getWhereJSON(data);
        String pkValue;

        alterTable(data, tableName, tableColumns, null);

        if(!isReplace){
            if (data.containsKey(pkName) && ((pkValue = (String) data.get(pkName)) != null) && !pkName.isEmpty()){
                where = String.format("%s = '%s'", pkName, pkValue);
            }
        }

        if(tableColumns != null){
            return fillDataToTable(tableName, data, tableColumns, where);
        }
        return false;
    }

    public boolean updateValue(String tableName, Map<String, Object> data){
        return updateValue(tableName, data, false);
    }

    public boolean updateValue(String tableName, String where, ContentValues values){
        int ret = db.update(tableName, values, where, null);
        return ret > 0;
    }

    private String getWhereByJSON(Map<String, Object> whereParams){

        if(whereParams.isEmpty()){
            return null;
        }

        WhereBuilder builder = WhereBuilder.create();
        for (String key : whereParams.keySet() ) {
            builder.addParam(key, whereParams.get(key));
        }
        return builder.build(" AND ");
    }

    public boolean updateValueWhere(String tableName, Map<String, Object> data, Map<String, Object> whereParams){

        Map<String,Map<String, Object>> tableColumns = getTableFields(tableName);

        if(tableColumns == null){
            return false;
        }

        return fillDataToTable(tableName, data, tableColumns, getWhereByJSON(whereParams));
    }

    private void copyValuesByJSON(Map<String, Object> object, ContentValues values, Map<String,Map<String, Object>> tableColumns){
        for (String columnName : object.keySet()) {

            //表格不存在字段不进行填充.
            if(!tableColumns.containsKey(columnName)) {
                continue;
            }

            String type = (String) tableColumns.get(columnName).get("type");
            if(object.get(columnName) != null){
                Object value = object.get(columnName);
                if("TEXT".equals(type) && value instanceof String){
                    values.put(columnName, (String) value);
                }else if ("FLOAT".equals(type) && value instanceof Number){
                    values.put(columnName, ((Number) value).doubleValue());
                }else if ("INTEGER".equals(type) && value instanceof Number){
                    values.put(columnName, ((Number) value).intValue());
                }else if ("BLOB".equals(type) && value instanceof byte[]){
                    values.put(columnName, (byte[]) value);
                }
            }
        }
    }

    private boolean fillDataToTable(String tableName, Map<String, Object> data, Map<String,Map<String, Object>> tableColumns, String where) {

        String pkName = getPKByFields(tableColumns);
        ContentValues values = new ContentValues();

        if(data.containsKey(pkName)){
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, pkName, data.get(pkName)), null);
            List<Map<String, Object>> withData = readJSONByCursor(cursor);
            if(withData != null && !withData.isEmpty()){
                Map<String, Object> object = withData.get(0);
                copyValuesByJSON(object, values, tableColumns);
            }
        }

        copyValuesByJSON(data, values, tableColumns);

        //非条件插入数据.
        if(where == null){
            values.put("row_input_time", System.currentTimeMillis());
            //不存在主键.
            if (pkName == null || !values.containsKey(pkName) || values.get(pkName) == null){
                Log.e(TAG, String.format("主键(%s)不存在,无法插入数据,需要配置 Param 进行参数转换.", pkName));
                return false;
            }
        }

        long ret = -1;
        if(!TextUtils.isEmpty(where)){
            ret = db.update(tableName, values, where, null);
        }else{
            ret = db.replace(tableName, null, values);
        }
        return ret > -1;
    }

    private boolean fillDataToTable(String tableName, Map<String, Object> data, Map<String,Map<String, Object>> tableColumns){
        return fillDataToTable(tableName, data, tableColumns, null);
    }

    private List<Map<String, Object>> readJSONByCursor(Cursor cursor){

        List<Map<String, Object>> array = new LinkedList();
        String[] columnNames = cursor.getColumnNames();
        while (cursor.moveToNext()) {

            Map<String, Object> object = new LinkedHashMap<>();
            for (int column = 0; column < columnNames.length; column++) {
                int i = column, type = cursor.getType(i);

                if(type >= 5) continue;

                ObjectUtil.GetObjectCall<Cursor> nullCall = obj -> null, intCall = obj -> obj.getInt(i),
                        floatCall = obj -> obj.getDouble(i),
                        stringCall = obj -> obj.getString(i),
                        blobCall = obj -> obj.getBlob(i);
                ObjectUtil.GetObjectCall<Cursor> calls[] = new ObjectUtil.GetObjectCall[]{nullCall, intCall, floatCall, stringCall, blobCall};

                object.put(columnNames[i], calls[type].call(cursor));
            }

            array.add(object);
        }
        cursor.close();
        return array;
    }

    public boolean updateKeyValues(String[] tableNames, String key, String newValue, String oldValue) {
        boolean allSuccess = true;

        for (String tableName : tableNames) {
            if (updateKeyValue(tableName, key, newValue, oldValue)) allSuccess = false;
        }
        return allSuccess;
    }

    public boolean updateKeyValue(String tableName, String key, String newValue, String oldValue){

        String sql = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s'", tableName, key, newValue, key, oldValue);
        try {
            db.execSQL(sql);
        }catch (SQLException ex){
            return false;
        }
        return true;
    }

    public int delByValue(String tableName, Map<String, Object> value){
        if (!isWithTable(tableName)){
            return 0;
        }

        Map<String, Map<String, Object>> fields = getTableFields(tableName);
        for (String key : value.keySet()){
            if (!fields.containsKey(key)){
                value.remove(key);
            }
        }

        int delRet = db.delete(tableName, getWhereByJSON(value), null);
        return delRet;
    }

    public Map<String, Object> readRowByPK(String table, String key, String value, String[] columns){
        Cursor cursor = db.query(table, columns, String.format("%s='%s'", key, value), null, null, null, null, null);
        List<Map<String, Object>> array = readJSONByCursor(cursor);

        if (array.size() == 1){
            return array.get(0);
        }
        return null;
    }

    public void beginTransaction(){
        db.beginTransaction();
        Log.i(TAG, "beginTransaction");
    }

    public void endTransaction(){
        db.endTransaction();
        Log.i(TAG, "endTransaction");
    }

    public void setTransactionSuccessful(){
        db.setTransactionSuccessful();
    }

    public void release(){
        db.close();
    }

    public void addCustomFunction(){
    }
}
