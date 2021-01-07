package kiun.com.bvroutine.base;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttrBind {

    /**
     * 绑定控件的属性.
     * @return
     */
    int value() default -1;

    /**
     * 默认值.
     * @return
     */
    int def() default Integer.MIN_VALUE;

    /**
     * 布尔默认值.
     * @return
     */
    boolean boolDef() default false;

    /**
     * 是否为资源.
     * @return
     */
    boolean resource() default false;

    /**
     * 是否为颜色.
     * @return
     */
    boolean color() default false;

    /**
     * 是否为长度单位.
     * @return
     */
    boolean dimension() default false;
}