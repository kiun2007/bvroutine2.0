package kiun.com.bvroutine.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RUNTIME)
public @interface QueryParam {
    String value();
}