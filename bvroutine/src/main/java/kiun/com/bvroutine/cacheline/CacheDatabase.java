package kiun.com.bvroutine.cacheline;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RUNTIME)
public @interface CacheDatabase {

    /**
     * 数据库名称
     * @return
     */
    String value();
}
