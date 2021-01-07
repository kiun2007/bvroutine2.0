package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface CompareCaller<T> {
    boolean call(T item);
}