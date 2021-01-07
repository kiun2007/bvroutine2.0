package kiun.com.bvroutine.base.jexl;

import android.content.Context;

import kiun.com.bvroutine.base.jexl.proxy.Enhancer;
import kiun.com.bvroutine.base.jexl.proxy.MethodInterceptor;
import kiun.com.bvroutine.base.jexl.proxy.MethodProxy;
import kiun.com.bvroutine.utils.JexlUtil;

public class ProxyHandler implements MethodInterceptor {

    private ProxyEnable proxyEnable;

    private Object target;

    private ProxyHandler(ProxyEnable proxyEnable, Object target) {
        this.proxyEnable = proxyEnable;
        this.target = target;
    }

    private ProxyHandler(){
    }

    private boolean filter(Object object, Object[] args, MethodProxy methodProxy, Object retValue){

        if (proxyEnable.filter().isEmpty()){
            return true;
        }

        Boolean ret = JexlUtil.run(proxyEnable.filter(), "that", object, "args", args, "methodName", methodProxy.getMethodName(), "oldValue", retValue);
        return ret == null ? false : ret;
    }

    @Override
    public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception {

        Object returnValue = null;

        if (target != null){
            object = target;
        }

        if (!proxyEnable.isAfter()){
            runProxy(object, args, methodProxy, null);
        }

        returnValue = methodProxy.invokeSuper(object, args);

        if (proxyEnable.isAfter()){
            returnValue = runProxy(object, args, methodProxy, returnValue);
        }
        return returnValue;
    }

    private Object runProxy(Object object, Object[] args, MethodProxy methodProxy, Object retValue){

        if (filter(object, args, methodProxy, retValue)){
            return JexlUtil.run(proxyEnable.use(), "that", object, "args", args, "methodName", methodProxy.getMethodName(), "oldValue", retValue);
        }
        return retValue;
    }

    public static<T> T createBy(Context context, Class<T> clz){
        return createBy(context, clz, null);
    }

    public static<T> T createBy(Context context, Class<T> clz, Object target){

        ProxyEnable proxyEnable = clz.getAnnotation(ProxyEnable.class);
        if (proxyEnable != null && !proxyEnable.use().isEmpty()){

            ProxyHandler proxyHandler = new ProxyHandler(proxyEnable, target);

            Enhancer enhancer = new Enhancer(context);
            enhancer.setSuperclass(clz);
            enhancer.setCallback(proxyHandler);
            return (T) enhancer.create();
        }
        return null;
    }
}