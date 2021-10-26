package kiun.com.bvroutine.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface BeginLoading {

    /**
     * 加载时页面主体显示的布局ID.
     * @return
     */
    int value() default -1;

    /**
     * 加载显示的标题.
     * @return
     */
    String title() default "网络请求";
}
