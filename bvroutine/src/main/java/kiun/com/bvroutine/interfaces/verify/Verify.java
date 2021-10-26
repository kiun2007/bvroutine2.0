package kiun.com.bvroutine.interfaces.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import kiun.com.bvroutine.data.VerifyBase;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证标注.
 * fieldName 字段名称.
 * that 表示被验证的数据.
 */
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RUNTIME)
@Inherited
public @interface Verify {

    /**
     * 验证类.
     * @return
     */
    Class<? extends VerifyBase> value();

    /**
     * 验证附加数据.
     * @return
     */
    String extra() default "";

    /**
     * 错误时的描述.
     * @return
     */
    String desc() default "";

    /**
     * 必须验证通过才能提交.
     * @return
     */
    boolean force() default true;

    /**
     * 允许通过的条件,默认不允许, that表示被验证的数据, fieldName表示字段名称.
     * @return
     */
    String pass() default "";

    /**
     * 验证显示等级, 等级越高越优先显示.
     * @return
     */
    int level() default -1;
}
