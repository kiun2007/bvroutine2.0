package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface BatchVoidCaller<T>{
    void call(T item);
}