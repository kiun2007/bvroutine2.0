package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface SetCaller<T> {
    void call(T v);
}