package kiun.com.bvroutine.base;

public class Access<T> {

    private T value;

    public T get() {
        return value;
    }

    public T set(T value) {
        return this.value = value;
    }
}
