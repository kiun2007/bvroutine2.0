package kiun.com.bvroutine.data.getter;

import java.lang.reflect.Type;
import java.util.Map;

import kiun.com.bvroutine.data.FieldGetter;

public class MapGetter extends FieldGetter<Map> {

    public MapGetter(Map value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {
        return value.get(fieldName);
    }
}
