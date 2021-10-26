package kiun.com.bvroutine.base.binding.variable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自动注入标志, 表示改类型如果出现在ViewDataBinding 中，将由框架自动注入生成实例.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface AutoImport {

    /**
     * 自动导入对象实现类.
     * @return
     */
    Class<? extends VariableBinding> value();
}
