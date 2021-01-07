package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class FloatAttributeReader extends AttributeReaderBase{

    public FloatAttributeReader() {
        super(Float.class, float.class);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {
        return array.getFloat(attrId, attrBind.def());
    }
}
