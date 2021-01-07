package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class IntAttributeReader extends AttributeReaderBase{

    public IntAttributeReader() {
        super(Integer.class, int.class);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {

        Object value = null;

        TypedValue typedValue = new TypedValue();
        if (!array.getValue(attrId, typedValue)) {
            value = attrBind.def();
        }else{
            if (typedValue.type == TypedValue.TYPE_FIRST_INT || typedValue.type == TypedValue.TYPE_INT_HEX){
                value = typedValue.data;
            }else if (typedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && typedValue.type <= TypedValue.TYPE_LAST_COLOR_INT){
                value = typedValue.data;
            }else if (typedValue.type == TypedValue.TYPE_DIMENSION){
                value = array.getDimensionPixelOffset(attrId, attrBind.def());
            }else if (typedValue.type == TypedValue.TYPE_FLOAT){
                value = (int)typedValue.getFloat();
            }else if (typedValue.resourceId != 0){
                value = typedValue.resourceId;
            }else if (typedValue.type == TypedValue.TYPE_STRING){
                value = array.getResourceId(attrId, attrBind.def());
            }
        }

        //如果为设置默认值, 不修改原值.
        if (value != null && value.equals(Integer.MIN_VALUE)){
            value = null;
        }
        return value;
    }
}
