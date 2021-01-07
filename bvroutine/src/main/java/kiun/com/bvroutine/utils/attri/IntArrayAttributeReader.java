package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.TypedArray;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;

public class IntArrayAttributeReader extends AttributeReaderBase{

    public IntArrayAttributeReader() {
        super(Integer[].class, int[].class);
    }

    @Override
    public Object read(Context context, TypedView typedView, TypedArray array, int attrId, AttrBind attrBind) {

        int resourceId = array.getResourceId(attrId, -1);
        if (resourceId != -1){
            int[] intArray = context.getResources().getIntArray(resourceId);

            if (int[].class.equals(type)){
                return intArray;
            }

            Integer[] integers = new Integer[intArray.length];
            for (int i = 0; i < integers.length; i++) {
                integers[i] = intArray[i];
            }
            return integers;
        }
        return null;
    }
}
