package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class StringArrayAttributeReader extends AttributeReaderBase{

    public StringArrayAttributeReader() {
        super(String[].class);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {

        int resourceId = array.getResourceId(attrId, -1);
        if (resourceId != -1){
            return context.getResources().getStringArray(resourceId);
        }
        return null;
    }
}
