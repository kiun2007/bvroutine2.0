package kiun.com.bvroutine.cacheline.data;

import java.lang.reflect.Type;

public abstract class FieldGetter<T>{

    protected Class<T> tClass;
    protected T value;
    public FieldGetter(T value){
        this.value = value;
        tClass = (Class<T>) value.getClass();
    }

    public abstract Object getValue(String fieldName, Type type);
}