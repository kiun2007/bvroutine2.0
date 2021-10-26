package kiun.com.bvroutine.data.getter;

import android.os.Bundle;
import android.os.Parcelable;
import java.lang.reflect.Type;

import kiun.com.bvroutine.data.FieldGetter;

public class BundleGetter extends FieldGetter<Bundle> {

    public BundleGetter(Bundle value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {
        if (type.equals(String.class)){
            return value.getString(fieldName);
        }else if (type.equals(Integer.class)){
            return value.getInt(fieldName, 0);
        }else if (type.equals(Double.class)){
            return value.getDouble(fieldName, 0);
        }else if (type.equals(Float.class)){
            return value.getFloat(fieldName, 0);
        }else if (type.equals(Long.class)){
            return value.getLong(fieldName, 0);
        }else if (type.equals(Short.class)){
            return value.getShort(fieldName, (short) 0);
        }else if (type.equals(Boolean.class)){
            return value.getBoolean(fieldName, false);
        }else if (type.equals(Byte.class)){
            return value.getByte(fieldName, (byte) 0);
        }else if ((type instanceof Class) && ((Class) type).isAssignableFrom(Parcelable.class)){
            return value.getParcelable(fieldName);
        }
        return null;
    }
}
