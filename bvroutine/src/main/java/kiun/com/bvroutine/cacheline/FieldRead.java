package kiun.com.bvroutine.cacheline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import kiun.com.bvroutine.data.FieldGetter;
import kiun.com.bvroutine.data.getter.ObjectGetter;

/**
 * 字段获取映射注解.
 */
@Target(ElementType.ANNOTATION_TYPE)
public @interface FieldRead {

    /**
     * 输入的类型,当类型相同时启用此映射方案.
     * @return 输入值的类型.
     */
    Class type();

    /**
     * 获取输入值的某个字段.
     * @return 字段名称.
     */
    String field();

    /**
     * 如果同一类型有多个映射方案根据此字段区分.
     * @return 区分标志.
     */
    String flag() default "";

    /**
     * 提供的输入类型字段提取器.
     * @return 一个字段提取器.
     */
    Class<? extends FieldGetter> getter() default ObjectGetter.class;
}