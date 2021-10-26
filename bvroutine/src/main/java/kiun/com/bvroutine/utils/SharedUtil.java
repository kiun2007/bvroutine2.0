package kiun.com.bvroutine.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.data.TypeSetter;
import kiun.com.bvroutine.interfaces.JSON;

public class SharedUtil {

    private static Context context;
    private static SharedUtil defShared;
    public static void initContext(Context context){
        SharedUtil.context = context;
        defShared = new SharedUtil("SHARED");
    }

    private String sharedName;
    private Map<String, Object> cacheMap = new HashMap<>();

    private SharedUtil(String sharedName) {
        this.sharedName = sharedName;
    }

    public void save(String name, Object value){

        if (context == null) return;

        //存储新值后清除Cache.
        cacheMap.remove(name);

        if (value instanceof JSON){
            value = JSONObject.toJSONString(value);
        }

        SharedPreferences shared = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        if (value == null){
            editor.remove(name);
        }else{
            new TypeSetter().addPutter(String.class,editor::putString).addPutter(Integer.class,editor::putInt)
                    .addPutter(Long.class,editor::putLong).addPutter(Float.class,editor::putFloat)
                    .addPutter(Boolean.class,editor::putBoolean).execute(name, value);
        }
        editor.apply();
    }

    public <T>T get(String name, T defValue){
        return get(name, defValue, true);
    }

    public <T>T get(String name, T defValue, boolean cache){

        if (context == null || defValue == null) return null;

        boolean isObject = false;
        Object def = defValue;

        if (defValue instanceof JSON){
            isObject = true;
            def = "";
        }

        Object ret = null;

        if (cache){
            ret = cacheMap.get(name);
        }

        //获取不到值时读取 SharedPreferences.
        if (ret == null){

            SharedPreferences shared = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
            ret = new TypeDefGetter().addGetter(String.class,shared::getString).addGetter(Integer.class,shared::getInt)
                    .addGetter(Long.class,shared::getLong).addGetter(Float.class,shared::getFloat)
                    .addGetter(Boolean.class,shared::getBoolean).execute(name, def);
            cacheMap.put(name, ret);
        }

        if (ret == null){
            return defValue;
        }

        if (ret instanceof String && isObject){
            if (((String) ret).isEmpty()){
                return defValue;
            }
            Object object = JSONObject.parseObject((String) ret, defValue.getClass());
            if (object == null){
                object = defValue;
            }
            return (T) object;
        }
        return (T) ret;
    }

    public void del(String name){
        SharedPreferences shared = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        shared.edit().remove(name).apply();
        cacheMap.remove(name);
    }

    public void clearAll(){
        SharedPreferences shared = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        shared.edit().clear().apply();
        cacheMap.clear();
    }

    public static SharedUtil create(String sharedName){
        return new SharedUtil(sharedName);
    }

    public static void saveValue(String name, Object value){
        if (defShared != null){
            defShared.save(name, value);
        }
    }

    public static<T> T getValue(String name, T defValue){
        return getValue(name, defValue, true);
    }

    public static<T> T getValue(String name, T defValue, boolean cache){
        if (defShared != null){
            return defShared.get(name, defValue, cache);
        }
        return null;
    }

    public static void delete(String name){
        if (defShared != null){
            defShared.del(name);
        }
    }

    //清除
    public static void clear(){
        if (defShared != null){
            defShared.clearAll();
        }
    }
}
