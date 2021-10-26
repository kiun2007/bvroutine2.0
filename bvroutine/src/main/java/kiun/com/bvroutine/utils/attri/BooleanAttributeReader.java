package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class BooleanAttributeReader extends AttributeReaderBase{

    public BooleanAttributeReader() {
        super(Boolean.class, boolean.class);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {
        return array.getBoolean(attrId, attrBind.boolDef());
    }
}
