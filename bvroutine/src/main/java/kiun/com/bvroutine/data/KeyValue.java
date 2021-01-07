package kiun.com.bvroutine.data;

public class KeyValue<K,V>{
    K mKey;
    V mValue;

    public KeyValue(K key, V value){
        mKey = key;
        mValue = value;
    }

    public K key() {
        return mKey;
    }

    public V value(){
        return mValue;
    }
}