package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface GetObjectCaller<T>{
    Object call(T obj);
}