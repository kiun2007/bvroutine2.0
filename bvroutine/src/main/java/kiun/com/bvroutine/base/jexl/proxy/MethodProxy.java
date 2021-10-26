package kiun.com.bvroutine.base.jexl.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodProxy {

	private Class subClass;
	private String methodName;
	private Class[] argsType;
	
	@SuppressWarnings("rawtypes")
	public MethodProxy(Class subClass, String methodName, Class[] argsType) {
		this.subClass = subClass;
		this.methodName = methodName;
		this.argsType = argsType;
	}

	public String getMethodName() {
		return methodName;
	}
	
	@SuppressWarnings("unchecked")
	public Method getOriginalMethod() {
		try {
			return subClass.getDeclaredMethod(methodName, argsType);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Method getProxyMethod() {
		try {
			return subClass.getDeclaredMethod(methodName + Const.SUBCLASS_INVOKE_SUPER_SUFFIX, argsType);
		} catch (NoSuchMethodException e) {
			throw new ProxyException(e.getMessage());
		}
	}
	
	public Object invokeSuper(Object object, Object[] argsValue) {

		if (object instanceof EnhancerInterface){
			return ((EnhancerInterface) object).executeSuperMethod$Enhancer$(methodName, argsType, argsValue);
		}

		//使用父类方法.
		Method originalMethod = null;
		do {
			originalMethod = getOriginalMethod();
			subClass = subClass.getSuperclass();
		}while (!subClass.equals(Object.class) && originalMethod == null);

		if (originalMethod != null){
			try {
				return originalMethod.invoke(object, argsValue);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
