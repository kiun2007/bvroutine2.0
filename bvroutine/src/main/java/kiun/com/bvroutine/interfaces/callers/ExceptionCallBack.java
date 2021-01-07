package kiun.com.bvroutine.interfaces.callers;

@FunctionalInterface
public interface ExceptionCallBack {
    void call() throws Exception;
}
