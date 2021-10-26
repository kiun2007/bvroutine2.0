package kiun.com.bvroutine.base.binding;

import kiun.com.bvroutine.interfaces.callers.DefGet;
import okhttp3.MultipartBody;
import retrofit2.Call;

@FunctionalInterface
public interface DoublePartCaller extends DefGet<Call, MultipartBody.Part, MultipartBody.Part> {
}