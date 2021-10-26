package kiun.com.bvroutine.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

public class VerifyUtil {

    /**
     * 开始验证某个对象存在的验证问题.
     * @param that
     * @return
     */
    public static List<Problem> verify(Object that){

        Class thatClz = that.getClass();

        //用来继承的验证类.
        Map<Class, List<Verify>> parentVerifyMap = new LinkedHashMap<>();

        List<Field> allFieldList = new ArrayList<>();

        //先搜索类的定义
        for (;thatClz != null && !thatClz.equals(EventBean.class); thatClz = thatClz.getSuperclass()){

            Verifys parentVerifyList = (Verifys) thatClz.getAnnotation(Verifys.class);
            if (parentVerifyList != null){
                parentVerifyMap.put(thatClz, Arrays.asList(parentVerifyList.value()));
            }else{
                Verify parentVerify = (Verify) thatClz.getAnnotation(Verify.class);
                if (parentVerify != null){
                    parentVerifyMap.put(thatClz, Arrays.asList(parentVerify));
                }
            }

            allFieldList.addAll(Arrays.asList(thatClz.getDeclaredFields()));
        }

        //问题集合.
        List<Problem> problemList = new LinkedList<>();
        for (Field field : allFieldList){

            List<Verify> verifyList = new LinkedList<>();

            //先抓去字段存在的验证.
            Verifys fieldVerify = field.getAnnotation(Verifys.class);
            if (fieldVerify != null){
                verifyList.addAll(Arrays.asList(fieldVerify.value()));
            }else{
                Verify verify = field.getAnnotation(Verify.class);
                if (verify != null){
                    verifyList.add(verify);
                }
            }

            List<Verify> parentVerify = parentVerifyMap.get(field.getDeclaringClass());

            //替换类中定义
            if (parentVerify != null){
                for (Verify itemVerify : parentVerify){
                    boolean isEmpty = ListUtil.filter(verifyList, item -> item.value().equals(itemVerify.value())).isEmpty();
                    if (isEmpty){
                        verifyList.add(itemVerify);
                    }
                }
            }

            /**
             * 开始根据注解验证.
             */
            if (!verifyList.isEmpty()){

                for (Verify verify : verifyList){

                    try {
                        VerifyBase verifyBase = verify.value().getConstructor(Object.class, Verify.class).newInstance(that, verify);
                        field.setAccessible(true);
                        Object fieldValue = field.get(that);
                        Problem problem = verifyBase.verify(fieldValue, field.getName());

                        if (problem != null){
                            problemList.add(problem);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 根据优先级高低每个字段的问题只先选优先级最高的问题展示.
         */
        if (!problemList.isEmpty()){

            Collections.sort(problemList, (o1, o2) -> o1.getLevel() - o2.getLevel());

            Map<String,Problem> problemMap = new HashMap<>();
            ListUtil.map(problemList, item->problemMap.put(item.getField(), item));
            problemList = MapUtil.toList(problemMap, (key, value)->value);
        }

        return problemList;
    }
}
