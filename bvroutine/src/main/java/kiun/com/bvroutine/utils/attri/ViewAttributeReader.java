package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import java.lang.reflect.Type;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class ViewAttributeReader extends AttributeReaderBase{

    public ViewAttributeReader() {
        super(View.class);
    }

    @Override
    public boolean isWithType(Type type) {
        return View.class.isAssignableFrom((Class<?>) type);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {
        int viewId = array.getResourceId(attrId, attrBind.def());
        if (viewId != Integer.MIN_VALUE){
            return typedView.findParentById(viewId);
        }
        return null;
    }
}
