package kiun.com.bvroutine.base.jexl.proxy;

public interface MethodInterceptor {
	
	public Object intercept(Object object, Object[] args, MethodProxy methodProxy)
			throws Exception;

}
