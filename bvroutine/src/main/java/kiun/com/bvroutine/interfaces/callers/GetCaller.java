package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface GetCaller<IN,OUT>{
    OUT call(IN obj);
}