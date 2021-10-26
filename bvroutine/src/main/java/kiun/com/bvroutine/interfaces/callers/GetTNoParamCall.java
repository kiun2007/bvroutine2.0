package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface GetTNoParamCall<T>{
    T call() throws Exception;
}