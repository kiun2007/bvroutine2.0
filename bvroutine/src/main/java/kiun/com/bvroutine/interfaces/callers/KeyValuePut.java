package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface KeyValuePut<T>{
    void put(String key, T value);
}
