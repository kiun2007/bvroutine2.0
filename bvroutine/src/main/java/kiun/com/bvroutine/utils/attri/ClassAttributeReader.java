package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Type;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

/**
 * 读取类.
 */
public class ClassAttributeReader extends AttributeReaderBase{

    public ClassAttributeReader() {
        super(Class.class);
    }

    @Override
    public boolean isWithType(Type type) {
        return super.isWithType(type);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {
        String clzName = array.getString(attrId);

        try {
            return Class.forName(clzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
