package kiun.com.bvroutine.cacheline;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 字段映射数组.
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RUNTIME)
public @interface FieldMapping {
    FieldRead[] value();
}