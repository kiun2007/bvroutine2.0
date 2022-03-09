package kiun.com.bvroutine.cacheline;


import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.body.BaseBodyBuilder;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.type.ClassUtil;

public class BodyConvertBridge {

    private static Map<Class, Class<? extends BaseBodyBuilder>> classBindConvertMap = new HashMap<>();

    public static void loadPackage(String... packageName){
        List<String> clsName = ClassUtil.getClassName(packageName);
        for (String name : clsName){
            try {
                Class clz = Class.forName(name);
                if (clz.getGenericSuperclass() instanceof ParameterizedType){
                    Class viewClz = ObjectUtil.getGenericClass((ParameterizedType)clz.getGenericSuperclass(), 0);
                    if (BaseBodyBuilder.class.isAssignableFrom(clz)){
                        classBindConvertMap.put(viewClz, clz);
                    }
                }
            } catch (ClassNotFoundException e) {
            }
        }
    }

    public static BaseBodyBuilder getBodyBuilder(Class clz){

        Class<? extends BaseBodyBuilder> builderClz = null;
        for (Class allClz = clz; !Object.class.equals(allClz); allClz = allClz.getSuperclass()){

            if (classBindConvertMap.containsKey(allClz)){
                builderClz = classBindConvertMap.get(allClz);
                break;
            }

            for (Class iClz : allClz.getInterfaces()){
                if (classBindConvertMap.containsKey(iClz)){
                    builderClz = classBindConvertMap.get(iClz);
                    break;
                }
            }
        }

        if (builderClz == null){
            return null;
        }

        return ObjectUtil.newObject(builderClz);
    }
}
