package kiun.com.bvroutine.data;

public class ExtraValue<K,V,E> extends KeyValue<K,V> {

    E extra = null;

    public ExtraValue(K key, V value, E extra) {
        super(key, value);
        this.extra = extra;
    }

    public E extra() {
        return extra;
    }
}
