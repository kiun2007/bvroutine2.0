package kiun.com.bvroutine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface SearchPackage {

    /**
     * 需要参与搜索项目代码顶级包名称,配置后可以提高搜索效率.
     * @return
     */
    String[] value() default {};

    /**
     * 主代码包名,一般指APP的代码顶级包名,
     * 请勿直接使用Context.getPackageName(), 如果使用 applicationIdSuffix或 applicationId
     * 会到导致无法获取到真实的代码顶级包名,此时需要配置此参数.
     * @return 真实的代码顶级包名.
     */
    String codePackage() default "";
}
