package kiun.com.bvroutine.data;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.KeyValuePut;

/**
 * 类型选择器，根据类型自动选择对应的方法.
 */
public class TypeSetter {
    private Map<Class,KeyValuePut> allPuts = new HashMap<>();

    public <T>TypeSetter addPutter(Class<T> clz, KeyValuePut<T> putter){
        allPuts.put(clz, putter);
        return this;
    }

    public void execute(String key, Object value){
        KeyValuePut valuePut = allPuts.get(value.getClass());
        if (valuePut != null){
            valuePut.put(key, value);
        }
    }
}
