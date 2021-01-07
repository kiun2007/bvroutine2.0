package kiun.com.bvroutine.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {

    long value() default -1;

    /**
     * 自启动.
     * @return
     */
    boolean auto() default false;

    /**
     * 键值.
     * @return
     */
    String key() default "";

    /**
     * 是否异步执行(默认在主线程执行任务方法).
     * @return
     */
    boolean async() default false;
}
