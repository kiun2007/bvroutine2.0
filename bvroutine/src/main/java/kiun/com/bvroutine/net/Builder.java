package kiun.com.bvroutine.net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 服务构建选择.
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface Builder {

    /**
     * 服务构建选择名称.
     * @return 名称.
     */
    String value() default ServiceGenerator.MAIN;
}
