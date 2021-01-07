package kiun.com.bvroutine.utils;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.attri.AttributeReaderBase;

/**
 * 控件属性转化工具类.
 */
public class AttriUtil {

    private static int getStyleableIndex(Class clz, String name, Context context) {
        try {
            if (context == null)
                return 0;

            String packageName = context.getPackageName();
            if (ActivityApplication.getAppPackageName() != null){
                packageName = ActivityApplication.getAppPackageName();
            }

            String className = packageName + ".R$styleable";
            if (clz.getPackage().getName().contains(R.class.getPackage().getName())){
                className = R.class.getPackage().getName() + ".R$styleable";
            }
            Field field = Class.forName(className).getDeclaredField(name);
            int ret = (Integer) field.get(null);
            return ret;
        } catch (Throwable t) {
        }
        return 0;
    }

    public static void readAllAttri(TypedView typedView, StringBuilder logError, boolean isEditMode, Context context, TypedArray array, Class typeClz) {
        for (Field field : typeClz.getDeclaredFields()){

            AttrBind attrBind = field.getAnnotation(AttrBind.class);

            if(attrBind != null){
                Object value = null;

                if (BVBaseActivity.class.isAssignableFrom(field.getType())){
                    value = ViewUtil.getActivity(context);
                }

                if (value == null){
                    int attrId = attrBind.value();

                    String fieldName = field.getName();
                    if (attrId == -1){
                        String name = String.format("%s_%s", typeClz.getSimpleName(), fieldName);
                        attrId = getStyleableIndex(typeClz, name, context);;

                        if (attrId < 0){
                            continue;
                        }
                    }

                    AttributeReaderBase attributeReader = AttributeReaderBase.find(field.getType());

                    if (attributeReader != null){
                        value = attributeReader.read(context, typedView, array, attrId, attrBind);
                    }
                }

                if (value != null){
                    try {
                        field.setAccessible(true);
                        field.set(typedView, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        array.recycle();
    }
}
