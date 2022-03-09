package kiun.com.bvroutine.cacheline.utils;

import android.content.Context;
import android.content.SharedPreferences;

import kiun.com.bvroutine.data.TypeSetter;
import kiun.com.bvroutine.utils.TypeDefGetter;


public class SharedUtil {
    private static Context context;
    public static void initContext(Context context){
        SharedUtil.context = context;
    }

    public static void saveValue(String name, Object value){
        if (context == null) return;
        SharedPreferences shared = context.getSharedPreferences("SHARED", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        new TypeSetter().addPutter(String.class,editor::putString).addPutter(Integer.class,editor::putInt)
                        .addPutter(Long.class,editor::putLong).addPutter(Float.class,editor::putFloat)
                        .addPutter(Boolean.class,editor::putBoolean).execute(name, value);
        editor.commit();
    }

    public static<T> T getValue(String name, T defValue){
        if (context == null) return null;
        SharedPreferences shared = context.getSharedPreferences("SHARED", Context.MODE_PRIVATE);

        return new TypeDefGetter().addGetter(String.class,shared::getString).addGetter(Integer.class,shared::getInt)
                           .addGetter(Long.class,shared::getLong).addGetter(Float.class,shared::getFloat)
                           .addGetter(Boolean.class,shared::getBoolean).execute(name, defValue);
    }
}
