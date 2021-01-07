package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface PutVoidCaller<T,E>{
    void call(T input, E output) throws Exception;
}