package kiun.com.bvroutine.cacheline.callers;

@FunctionalInterface
public interface KeyValuePut<T>{
    void put(String key,T value);
}
