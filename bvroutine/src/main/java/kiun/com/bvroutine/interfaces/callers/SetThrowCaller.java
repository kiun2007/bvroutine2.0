package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface SetThrowCaller<T> {
    void call(T v) throws Exception;
}