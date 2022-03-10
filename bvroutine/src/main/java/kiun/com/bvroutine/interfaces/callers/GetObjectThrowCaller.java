package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface GetObjectThrowCaller<T> {

    Object call(Object obj) throws Exception;
}
