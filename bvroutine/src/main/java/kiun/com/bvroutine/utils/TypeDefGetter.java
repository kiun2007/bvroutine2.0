package kiun.com.bvroutine.utils;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.KeyValueGetDef;

public class TypeDefGetter {

    private Map<Class, KeyValueGetDef> allPuts = new HashMap<>();

    public <T>TypeDefGetter addGetter(Class<T> clz, KeyValueGetDef<T> getter){
        allPuts.put(clz, getter);
        return this;
    }

    public <T>T execute(String key, T defValue){
        KeyValueGetDef<T> valuePut = allPuts.get(defValue.getClass());
        if (valuePut != null){
            return valuePut.get(key, defValue);
        }
        return null;
    }
}