package kiun.com.bvroutine.cacheline.utils;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.data.FieldGetter;
import kiun.com.bvroutine.data.FieldMapping;
import kiun.com.bvroutine.data.FieldRead;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.data.getter.ObjectGetter;

public class ObjectUtil {

    @FunctionalInterface
    public interface BatchVoidCall<T>{
        void call(T item);
    }

    @FunctionalInterface
    public interface PutVoidCall<T,E>{
        void call(T input, E output) throws Exception;
    }

    public interface GetObjectCall<T>{
        Object call(T obj);
    }

    public static<T> void batchCall(BatchVoidCall<T> callback, T ... items){
        for (int i = 0; i < items.length; i++) {
            callback.call(items[i]);
        }
    }

    public static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) {
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) {
            return getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }

    public static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) {
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return getClass(((TypeVariable) type).getBounds()[0], 0);
        } else {
            return (Class) type;
        }
    }

    public static FieldRead getMappingName(AccessibleObject access, Class src, String flag){
        FieldMapping mapping = access.getAnnotation(FieldMapping.class);
        if (mapping != null){
            for (FieldRead fieldRead : mapping.value()){
                if (fieldRead.type().equals(src) && fieldRead.flag().equals(flag)){
                    return fieldRead;
                }
            }
        }else{
            FieldRead fieldRead = access.getAnnotation(FieldRead.class);
            if (fieldRead != null){
                return fieldRead;
            }
        }
        return null;
    }

    private static void addMappingByAccess(Map<AccessibleObject, KeyValue<String,Class<? extends FieldGetter>>> accessMap,
                                           AccessibleObject[] accessibles, Class srcClz, String flag, boolean isMethod){

        for (AccessibleObject access : accessibles){
            Member member = (Member) access;
            if (Modifier.isStatic(member.getModifiers())){
                continue;
            }

            FieldRead mappingReader = getMappingName(access, srcClz, flag);
            //跳过不是set方法和特别标注的方法,没有标注的私有字段..
            if (isMethod){
                Method method = (Method) access;
                if ((member.getName().indexOf("set") < 0 && mappingReader == null) || method.getGenericParameterTypes().length != 1){
                    continue;
                }
            }else{
                if (!Modifier.isPublic(member.getModifiers()) && mappingReader == null){
                    continue;
                }
            }
            accessMap.put(access, new KeyValue<>(mappingReader != null ? mappingReader.field() : member.getName(),mappingReader != null? mappingReader.getter():null));
        }
    }

    private static Object getValue(KeyValue<String,Class<? extends FieldGetter>> fieldGetter, Object src, Type type) throws Exception {

        Class srcClz = src.getClass();

        FieldGetter fieldGet;
        if (fieldGetter.value() == null){
            fieldGet = new ObjectGetter(src);
        }else{
            Type actualTypes[] = ((ParameterizedType)fieldGetter.value().getGenericSuperclass()).getActualTypeArguments();
            Constructor<FieldGetter> constructor = (Constructor<FieldGetter>) fieldGetter.value().getConstructor((Class<?>) actualTypes[0]);
            fieldGet = constructor.newInstance(src);
        }

        return fieldGet.getValue(fieldGetter.key(), type);
    }

    private static Map<String,Map<AccessibleObject, KeyValue<String,Class<? extends FieldGetter>>>> allAccessMap;

    static {
        allAccessMap = new HashMap<>();
    }

    private static Map<AccessibleObject, KeyValue<String,Class<? extends FieldGetter>>> getAccessMap(Class destClz, Class srcClz, String flag){

        Map<AccessibleObject, KeyValue<String,Class<? extends FieldGetter>>> accessMap;
        String destClzName = destClz.getName();
//        if ((accessMap = allAccessMap.get(destClzName)) != null) {
//            return accessMap;
//        }

        accessMap = new HashMap<>();
        //提取公共的方法和字段.
        while(!destClz.getSimpleName().equals(Object.class.getSimpleName())){
            addMappingByAccess(accessMap, destClz.getMethods(), srcClz, flag, true);
            addMappingByAccess(accessMap, destClz.getDeclaredFields(), srcClz, flag, false);
            destClz = destClz.getSuperclass();
        }

//        allAccessMap.put(destClzName, accessMap);
        return accessMap;
    }

    /**
     * 将一个对象按照映射规则复制到另一个对象.
     * @param dest 数据目标值,这个对象应该包含映射规则的注解.
     * @param src 数据源,为目标提供数据的复制.
     * @param flag 如果有多个映射规则,提供该规则的标志,可以直接使用此规则.
     */
    public static void copyByMapping(Object dest, Object src, String flag){

        Class destClz = dest.getClass();
        Class srcClz = src.getClass();

        Map<AccessibleObject, KeyValue<String,Class<? extends FieldGetter>>> accessMap = getAccessMap(destClz, srcClz, flag);
        for (AccessibleObject access : accessMap.keySet()) {
            try {
                if (access instanceof Method) {
                    Method method = (Method) access;
                    Object value = getValue(accessMap.get(access), src, method.getGenericParameterTypes()[0]);
                    if (value != null) method.invoke(dest, value);
                } else if (access instanceof Field) {
                    Field field = (Field) access;
                    Object value = getValue(accessMap.get(access), src, field.getType());
                    if (value != null) field.set(dest, value);
                }
            }catch (Exception ex){
            }
        }
    }

    /**
     * 将一个对象按照映射规则复制到另一个对象.
     * @param dest 数据目标值,这个对象应该包含映射规则的注解.
     * @param src 数据源,为目标提供数据的复制.
     */
    public static void copyByMapping(Object dest, Object src){
        copyByMapping(dest, src, "");
    }


    public static<T,L extends List> L copyList(List src, Class<T> clz){
        return copyList(src, clz, null);
    }

    /**
     * 将一个列表内容复制另外一个不同类型的列表.
     * @param src 列表数据.
     * @param clz 新列表每一项的类型.
     * @param lsClz 装载数据的列表类型.
     * @return 全新的列表.
     */
    public static<T,L extends List> L copyList(List src, Class<T> clz, Class<L> lsClz){

        L newList = null;
        try {
            if (lsClz == null){
                lsClz = (Class<L>) LinkedList.class;
            }
            newList = lsClz.newInstance();
            for (Object item : src){
                T newItem = clz.newInstance();
                copyByMapping(newItem, item);
                newList.add(newItem);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return newList;
    }
}
