package kiun.com.bvroutine.net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import okhttp3.Interceptor;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 服务构建选择.
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface BuilderSet {

    /**
     * 服务构建选择名称.
     * @return 名称.
     */
    String key() default ServiceGenerator.MAIN;

    /**
     * 服务器前缀地址.
     * @return
     */
    String prefix();

    /**
     * 登陆拦截类,操作包括如何获取令牌，保存令牌，填写令牌，登陆失效操作.
     * 必须继承 {@link LoginInterceptor kiun.com.bvroutine.net.LoginInterceptor}
     * @return 登陆类.
     */
    Class<? extends LoginInterceptor> loginClass();

    /**
     * 拦截器类.
     * @return
     */
    Class<? extends Interceptor> interceptorClz() default HttpInterceptor.class;
}
