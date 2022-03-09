package kiun.com.bvroutine.cacheline;

@FunctionalInterface
public interface JSONForeach<E> {
    boolean itemCall(String key, E value);
}
