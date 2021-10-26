package kiun.com.bvroutine.utils.type;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import dalvik.system.DexFile;
import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.utils.MCString;

public class ClassUtil {

    private static List<String> notAndroidClassName = new LinkedList<>();

    private static boolean isWithPackage(String findName, String[] packageNames){
        for (String packageName : packageNames){
            if (findName.startsWith(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化指定包名.
     * @param packageNames
     */
    public static void initAllClass(String ...packageNames){

        try {
            DexFile df = new DexFile(ActivityApplication.getApplication().getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();
                if (isWithPackage(className, packageNames)){
                    notAndroidClassName.add(className);
                }
            }
            df.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> findAll(String ...packageNames){

        List<String> classNameList = new ArrayList<String>();
        try {
            DexFile df = new DexFile(ActivityApplication.getApplication().getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();
                if (isWithPackage(className, packageNames)){
                    classNameList.add(className);
                }
            }
            df.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classNameList;
    }

    public static List<String > getClassName(String... packageNames){

        List<String> classNameList = new ArrayList<String>();

        for (String className : notAndroidClassName){
            if (isWithPackage(className, packageNames)){
                classNameList.add(className);
            }
        }
        return  classNameList;
    }

    public static List<Class<?>> getClassByType(String tail, Class parentClz, Class<? extends Annotation> annotationClz){

        List<String> classNameList = new LinkedList<>();
        try {
            for (String className : notAndroidClassName){
                if (className.endsWith(tail)){
                    classNameList.add(className);
                }
            }

            List<Class<?>> classList = new LinkedList<>();

            for(String itemClzName : classNameList){
                Class<?> clz = Class.forName(itemClzName);
                if (clz.isAnnotationPresent(annotationClz) && parentClz.isAssignableFrom(clz)){
                    classList.add(clz);
                }
            }
            return classList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static<T> Class<? extends T> forName(String clzName, Class<T> tClass){
        try {
            Class clz = Class.forName(clzName);
            if (tClass != null){
                if (tClass.isAssignableFrom(clz)){
                    return clz;
                }
            }else{
                return clz;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isBaseType(Class clz){
        return MCString.isWith(clz, int.class, short.class, byte.class, float.class,
                long.class, double.class, boolean.class, char.class,
                Integer.class, Short.class, Byte.class, Float.class,
                Long.class, Double.class, Boolean.class, String.class);
    }

    public static boolean isOriginalType(Class clz){
        return MCString.isWith(clz, int.class, short.class, byte.class, float.class,
                long.class, double.class, boolean.class, char.class);
    }

    public static int getBaseTypeLevel(Class clz){

        Class[] classes = new Class[]{byte.class, Byte.class, short.class, Short.class, int.class, Integer.class,
                                      long.class, Long.class, float.class, Float.class, double.class, Double.class};
        List<Class> classList = Arrays.asList(classes);
        int levelSize = classList.indexOf(clz);
        return levelSize > -1 ? levelSize/2 : levelSize;
    }

    public static boolean isLogicType(Class clz){
        return MCString.isWith(clz, boolean.class, Boolean.class);
    }

    public static boolean isCharType(Class clz){
        return MCString.isWith(clz, char.class, Character.class);
    }
}
