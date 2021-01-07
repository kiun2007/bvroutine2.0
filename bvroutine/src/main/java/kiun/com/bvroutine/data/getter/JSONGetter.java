package kiun.com.bvroutine.data.getter;


import org.json.JSONObject;

import java.lang.reflect.Type;

import kiun.com.bvroutine.data.FieldGetter;

public class JSONGetter extends FieldGetter<JSONObject> {

    public JSONGetter(JSONObject value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {
        return value.opt(fieldName);
    }
}
