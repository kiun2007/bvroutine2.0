package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class DrawableAttributeReader extends AttributeReaderBase{

    public DrawableAttributeReader() {
        super(Drawable.class);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {
        return array.getDrawable(attrId);
    }
}
