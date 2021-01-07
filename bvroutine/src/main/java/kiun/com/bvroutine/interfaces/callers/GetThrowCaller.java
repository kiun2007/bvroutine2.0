package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface GetThrowCaller<IN,OUT>{
    OUT call(IN obj) throws Exception;
}