package kiun.com.bvroutine.cacheline.data.beans;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.cacheline.CacheDatabase;
import kiun.com.bvroutine.cacheline.CacheSet;
import kiun.com.bvroutine.cacheline.CacheType;
import kiun.com.bvroutine.cacheline.data.SqliteDBHelper;
import kiun.com.bvroutine.cacheline.utils.JexlUtil;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.ByteUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.MD5Util;
import kiun.com.bvroutine.utils.type.ClassUtil;
import retrofit2.Invocation;

/**
 * 设置存储单元.
 */
public class SettingUnit implements CacheSet {

    private static Map<String, SqliteDBHelper> sqliteDBHelperCache = new HashMap<>();

    private CacheSet cacheSet;
    private String dbName;
    private String id;
    private TableCluster cluster;
    private SqliteDBHelper dbHelper;
    private SqliteDBHelper uploadHelper;
    private Invocation invocation;

    private SettingUnit(CacheSet cacheSet, String id, String dbName, Invocation invocation) throws Exception {

        this.cacheSet = cacheSet;
        this.dbName = dbName;
        this.invocation = invocation;
        this.id = id;
        if(cacheSet.Name().contains("=") && cacheSet.Key().contains("=")) {
            cluster = new TableCluster(cacheSet.Name(), cacheSet.Key(), Extra());
        }

        String allName = String.format("normal_%s", dbName);
        String uploadName = "normal_upload_cache";
        if (ServiceGenerator.getLogin().getAuthorizeToken() != null){
            allName = String.format("%s_%s", ByteUtil.bytesToHexString(MD5Util.MD5(ServiceGenerator.getLogin().getAuthorizeToken())), dbName);
            uploadName = String.format("%s_upload_cache", ByteUtil.bytesToHexString(MD5Util.MD5(ServiceGenerator.getLogin().getAuthorizeToken())));
        }

        uploadHelper = getDbByName(uploadName);
        dbHelper = getDbByName(allName);

        if (!Filter().isEmpty()) {
            Map<String, Object> filterJSON = (Map<String, Object>) JSON.parse(Filter());
        }
    }

    public static SqliteDBHelper getDbByName(String name){
        if(sqliteDBHelperCache.containsKey(name)){
            return sqliteDBHelperCache.get(name);
        }else{
            File dbRoot = new File(ActivityApplication.getApplication().getFilesDir(), "cache_db");
            if(!dbRoot.exists()) dbRoot.mkdir();

            File dbFile = new File(dbRoot, name + ".db");
            SqliteDBHelper sqliteDBHelper = new SqliteDBHelper(dbFile.getPath());
            sqliteDBHelperCache.put(name, sqliteDBHelper);
            return sqliteDBHelper;
        }
    }

    public static SqliteDBHelper getUploadDb(){

        String uploadName = "normal_upload_cache";
        if (ServiceGenerator.getLogin().getAuthorizeToken() != null){
            uploadName = String.format("%s_upload_cache", ByteUtil.bytesToHexString(MD5Util.MD5(ServiceGenerator.getLogin().getAuthorizeToken())));
        }
        return getDbByName(uploadName);
    }

    /**
     * 根据 方法描述 生成设置
     * @param invocation 方法描述
     * @return
     */
    public static SettingUnit createByInvocation(Invocation invocation) {

        if (invocation == null){
            return null;
        }

        CacheSet cacheSet = invocation.method().getAnnotation(CacheSet.class);
        if (cacheSet == null){
            return null;
        }

        String dbName = "cache_line_main";
        CacheDatabase cacheDatabase = ClassUtil.getAnnotation(CacheDatabase.class, invocation.method());
        if (cacheDatabase != null){
            dbName = cacheDatabase.value();
        }
        String id = invocation.method().getDeclaringClass().getName() + "/" + invocation.method().getName();
        try {
            SettingUnit settingUnit = new SettingUnit(cacheSet, id, dbName, invocation);
            return settingUnit;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String Name() {
        if (cluster != null){
            return cluster.getTableName();
        }
        return cacheSet.Name();
    }

    @Override
    public String Key() {
        if (cluster != null){
            return cluster.getKeyName();
        }
        return cacheSet.Key();
    }

    @Override
    public String Filter() {
        return cacheSet.Filter();
    }

    @Override
    public String Params() {
        return cacheSet.Params();
    }

    @Override
    public boolean IsList() {
        return cacheSet.IsList();
    }

    @Override
    public String Description() {
        return cacheSet.Description();
    }

    @Override
    public String[] About() {
        return cacheSet.About();
    }

    @Override
    public String Null() {
        return cacheSet.Null();
    }

    @Override
    public String ReplaceKey() {
        return cacheSet.ReplaceKey();
    }

    @Override
    public String Update() {
        return cacheSet.Update();
    }

    @Override
    public String Return() {
        return cacheSet.Return();
    }

    @Override
    public boolean PutFile() {
        return cacheSet.PutFile();
    }

    @Override
    public String Extra() {
        return cacheSet.Extra();
    }

    @Override
    public String Igs() {
        return cacheSet.Igs();
    }

    @Override
    public String SQL() {
        return cacheSet.SQL();
    }

    @Override
    public boolean IsDelete() {
        return cacheSet.IsDelete();
    }

    @Override
    public boolean NoSave() {
        return cacheSet.NoSave();
    }

    @Override
    public String Intercept() {
        return cacheSet.Intercept();
    }

    @Override
    public String Data() {
        return cacheSet.Data();
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    public CacheType CacheType() {
        return cacheSet.CacheType();
    }

    @Override
    public boolean OnlyCache() {
        return cacheSet.OnlyCache();
    }

    public SqliteDBHelper getDbHelper() {
        return dbHelper;
    }

    public SqliteDBHelper getUploadHelper() {
        return uploadHelper;
    }

    public String Id() {
        return id;
    }

    public TableCluster getCluster(){
        return cluster;
    }

    public Map<String, Object> getParamObject(Map<String, Object> paramObj){
        if (Params().length() == 0 || paramObj == null){
            return null;
        }
        return JexlUtil.exeCodeByParams(Params(), paramObj);
    }

    public Map<String, Object> getParam(Map<String, Object> paramObj){
        if (Params().length() == 0 || paramObj == null){
            return null;
        }
        return JexlUtil.exeCodeByParams(Params(), paramObj, dbHelper);
    }

    public Map<String, Object> getFilterObject(Map<String, Object> paramObj) {
        if (Filter().length() == 0 || paramObj == null){
            return null;
        }
        return JexlUtil.exeCodeByParams(Filter(), paramObj);
    }

    public Invocation getInvocation() {
        return invocation;
    }
}