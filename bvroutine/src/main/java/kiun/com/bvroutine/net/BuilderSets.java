package kiun.com.bvroutine.net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 服务构建选择多个.
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface BuilderSets {
    BuilderSet[] value();
}
