package kiun.com.bvroutine.interfaces;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 状态透传注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StateTransfer {

    /**
     * 需要透传递的参数名称.
     */
    String[] value();

    /**
     * 需要跳过的Intent Action参数.
     * @return
     */
    String[] breakAction() default {};

}
