package kiun.com.bvroutine.interfaces.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RUNTIME)
public @interface Verifys {
    /**
     * 验证注解.
     * @return
     */
    Verify[] value();
}
