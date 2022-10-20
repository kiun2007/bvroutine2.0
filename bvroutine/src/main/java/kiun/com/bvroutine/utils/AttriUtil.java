package kiun.com.bvroutine.utils;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, Class> packageAndRClz = new HashMap<>();

    private static Class getRClass(Class viewClz){

        for (String key : packageAndRClz.keySet()) {
            if (viewClz.getPackage().getName().startsWith(key)){
                return packageAndRClz.get(key);
            }
        }

        String packageName = viewClz.getPackage().getName();
        do {
            String className = packageName + ".R$styleable";
            try {
                Class rClass = Class.forName(className);
                packageAndRClz.put(packageName, rClass);
                return rClass;
            } catch (ClassNotFoundException e) {
            }
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        } while (packageName.contains("."));
        return null;
    }

    private static int getStyleableIndex(Class clz, StringBuilder logError, String name, Context context) {
        try {
            if (context == null)
                return 0;

            Class rclz = getRClass(clz);
            if (rclz == null){
                throw new RuntimeException("not found R class");
            }

            Field field = rclz.getDeclaredField(name);
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
                        attrId = getStyleableIndex(typeClz, logError, name, context);;

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
