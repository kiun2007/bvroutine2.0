package kiun.com.bvroutine.interfaces.binding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Import {

    /**
     * 导入类.
     * @return
     */
    Class value();

    /**
     * 导入方式.
     * @return
     */
    ImportType type() default ImportType.Service;

    /**
     * 扩展信息.
     * @return
     */
    String extra() default "";
}
