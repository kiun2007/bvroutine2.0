package kiun.com.bvroutine.interfaces.callers;

import retrofit2.Call;

@FunctionalInterface
public interface ServiceCaller<T> {
    Call requestCall(T s);
}