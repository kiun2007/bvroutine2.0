package kiun.com.bvroutine.base.jexl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用代理注解.
 * 运行时基本变量有如下.
 * @see that 表示对象本身, 类似this.
 * @see args 表示代理前方法的参数集合, 类型Object[].
 * @see methodName 方法名.
 * @see oldValue 如果是后执行代理模式,此值未原函数
 * 若需使用其他或工具变量请用
 * {@link RuntimeContext#runTime()}
 * {@link RuntimeContext#set(String, Object)} 导入, 例子如下.
 * RuntimeContext.runTime().set("MCString", new MCString())
 * 常用工具类 {@link kiun.com.bvroutine.utils.MCString}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxyEnable {

    /**
     * 代理过程 使用表达式, that表示被代理对象本身.
     * @return 选定方法执行时使用过程.
     */
    String use();

    /**
     * 过滤过程 表达式, 表达式返回boolean类型值，为true表示此函数使用代理过程, false 不使用.
     * @return 表达式字符, 表达式为空则全部适用, 这是个危险行为所有方法被调用都将触发 代理过程，非常可怕.
     */
    String filter() default "";

    /**
     * 是否先调用本身方法,后调用代理过程.
     * @return 默认为true.
     */
    boolean isAfter() default true;
}
