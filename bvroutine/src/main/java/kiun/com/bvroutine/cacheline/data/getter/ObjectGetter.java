package kiun.com.bvroutine.cacheline.data.getter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.data.FieldGetter;

/**
 * 普通JavaBean对象提取器.
 */
public class ObjectGetter extends FieldGetter<Object> {

    private static Map<String, AccessibleObject> allGetAccess = new HashMap<>();

    public ObjectGetter(Object value) {
        super(value);
    }

    @Override
    public Object getValue(String fieldName, Type type) {

        String fillName = tClass.getName() + "_" + fieldName;

        AccessibleObject access;
        if((access = allGetAccess.get(fieldName)) != null){
            try {
                if (access instanceof Field) {
                    return ((Field) access).get(value);
                } else if (access instanceof Method) {
                    return ((Method) access).invoke(value);
                }
            }catch (Exception ex){
            }
            return null;
        }

        //读取字段.
        try {
            Field field = tClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            allGetAccess.put(fillName, field);
            return field.get(value);
        } catch (Exception ex) {
        }

        String methodName = getAccessName(fieldName, false);
        try {
            Method getMethod = tClass.getDeclaredMethod(methodName);
            allGetAccess.put(fillName, getMethod);
            return getMethod.invoke(value);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getAccessName(String methodName, boolean field){
        int setIndex = methodName.indexOf("set");
        int getIndex = methodName.indexOf("get");
        if (setIndex == 0){
            if (field){
                String newName = methodName.substring(3, methodName.length() - 3);
                return newName.substring(0,1).toLowerCase() + newName.substring(1);
            }
            return methodName.replace("set", "get");
        }else if (getIndex < 0 && !field){
            return "get" + methodName.substring(0,1).toUpperCase() + methodName.substring(1);
        }
        return methodName;
    }
}
