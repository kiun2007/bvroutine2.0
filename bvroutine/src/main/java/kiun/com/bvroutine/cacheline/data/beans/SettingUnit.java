package kiun.com.bvroutine.cacheline.data.beans;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.CacheSet;
import kiun.com.bvroutine.cacheline.CacheType;
import kiun.com.bvroutine.cacheline.utils.JexlUtil;
import kiun.com.bvroutine.utils.MCString;

/**
 * 设置存储单元.
 */
public class SettingUnit implements CacheSet {

    private List<String> restfulValues;
    private CacheSet cacheSet = null;
    private CacheType cacheType;
    private String saveDB;
    private String id;
    private String urlFeature;
    private TableCluster cluster;
    private Map<String, Object> filterJSON = null;
    private static final String[] PATTERNS = new String[]{"[0-9,a-z,A-Z]","[0-9,a-z,A-Z]","\\d"};

    public SettingUnit(Field field, String savedb, CacheType cacheType) throws Exception {

        if (field == null){
            throw new Exception("Field is Null");
        }
        cacheSet = field.getAnnotation(CacheSet.class);
        id = field.getDeclaringClass().getName()+"/"+field.getName();
        this.cacheType = cacheType;
        saveDB = savedb;

        if(cacheSet == null){
            throw new Exception(field.getName() + " is Not CacheSet Annotation Field");
        }

        String splits[] = Url().indexOf("?") < 0 ? Url().split("/\\{") : Url().split("\\?");
        if (splits.length > 0){
            if(Url().indexOf("/{") > 0){
                String maths[] = MCString.patternValues("\\{(.+?):(.+?)\\}", Url());
                if(maths.length > 0){

                    restfulValues = new LinkedList<>();
                    for (int i = 0; i < maths.length; i += 2){
                        String value = maths[i + 1];
                        String type = "s", patt = null;
                        int min = 1, max = -1;

                        String[] pattParams = MCString.patternValues("(.+?)\\[(.+?),(.+?)\\]", value); //最全参数.
                        if (pattParams.length == 0){
                            pattParams = MCString.patternValues("(.+?)\\[(.+?)\\]", value); //两个参数.
                            if (pattParams.length == 0){
                                pattParams = MCString.patternValues("([0-9]{2,6})", value); //一个参数.
                            }
                        }

                        if (pattParams.length > 0){
                            type = pattParams[0];
                            boolean isNumber;
                            if(isNumber = ("sd".indexOf(type) < 0)){
                                max = min = Integer.parseInt(type);
                                type = "s";
                            }
                            boolean typeNumber = "d".equals(type);

                            if (!isNumber){
                                max = pattParams.length == 3 ? MCString.toNumber(pattParams[2]).intValue() :
                                        (pattParams.length == 2 ? MCString.toNumber(pattParams[1]).intValue() : (typeNumber ? 20 : 1024));
                                min = pattParams.length == 3 ? MCString.toNumber(pattParams[1]).intValue() : max;
                            }
                            patt = String.format("%s{%d,%d}", PATTERNS["sd".indexOf(type)], min, max);
                        }

                        if (patt != null){
                            restfulValues.add(patt);
                        }
                    }
                }
            }
            urlFeature = splits[0];
        }

        if(cacheSet.Name().indexOf("=") > -1 && cacheSet.Key().indexOf("=") > -1) {
            cluster = new TableCluster(cacheSet.Name(), cacheSet.Key(), Extra());
        }

        if (!Filter().isEmpty()){
            filterJSON = (Map<String, Object>) JSON.parse(Filter());
        }
    }

    @Override
    public String Url() {
        return cacheSet.Url();
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
        return cacheType;
    }

    public String SaveDB() {
        return saveDB;
    }

    public String Id() {
        return id;
    }

    public String getUrlFeature(){
        return urlFeature;
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

    public Map<String, Object> getParamDB(Map<String, Object> paramObj, Object db){
        if (Params().length() == 0 || paramObj == null){
            return null;
        }
        return JexlUtil.exeCodeByParams(Params(), paramObj, db);
    }

    public Map<String, Object> getFilterObject(Map<String, Object> paramObj) {
        if (Filter().length() == 0 || paramObj == null){
            return null;
        }
        return JexlUtil.exeCodeByParams(Filter(), paramObj);
    }

    /**
     * 提取URL内的参数.
     * @param url 访问服务地址.
     * @return 提取到参数的Map
     */
    public Map<String, String> getURLParams(String url)
    {
        Map<String, String> paramMap = new HashMap<>();
        boolean isQuery = Url().indexOf("?") > -1;
        String[] keys = MCString.patternValues(isQuery ? "(?:\\?|&)(\\w+)={0,1}" : "\\{(.+?):.+?\\}", Url());

        if(isQuery){
            for (int i = 0; i < keys.length; i++) {
                String values[] = MCString.patternValues("[^\\?&]?"+keys[i]+"=([^&]+)", url);
                if(values != null && values.length > 0){
                    String value = values[0];
                    paramMap.put(keys[i], value);
                }
            }
        }else {
            String[] paths = url.split("/");
            if(paths.length > keys.length){
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    String value = paths[paths.length - (keys.length - i)];
                    paramMap.put(key, value);
                }
            }
        }
        return paramMap;
    }

    public boolean matchRestful(String url){

        if(restfulValues == null || restfulValues.size() == 0){
            return false;
        }

        String[] paths = url.split("/");
        if(paths.length > restfulValues.size()){
            int start = paths.length - restfulValues.size();

            for (int i = start, item = 0; i < paths.length; i++,item++) {
                if(!paths[i].matches(restfulValues.get(item))) {
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }
}