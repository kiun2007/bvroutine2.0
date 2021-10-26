package kiun.com.bvroutine.base.jexl.proxy;

public interface EnhancerInterface {
	
	void setMethodInterceptor$Enhancer$(MethodInterceptor methodInterceptor);
	
	@SuppressWarnings("rawtypes")
	Object executeSuperMethod$Enhancer$(String methodName, Class[] argsType, Object[] argsValue);

	/**
	 *
     */
	void setCallBacksMethod$Enhancer$(MethodInterceptor[] methodInterceptor);

	/**
	 * filter
     */
	void setCallBackFilterMethod$Enhancer$(CallbackFilter callbackFilter);

}
