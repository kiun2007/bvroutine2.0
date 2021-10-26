package kiun.com.bvroutine.base.binding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindConvertImport {
    /**
     * 绑定搜索的包名.
     * @return
     */
    String[] value();
}
