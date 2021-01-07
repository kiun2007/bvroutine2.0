package kiun.com.bvroutine.base.binding;

import kiun.com.bvroutine.interfaces.callers.GetObjectCaller;
import okhttp3.MultipartBody;

@FunctionalInterface
public interface PartCaller extends GetObjectCaller<MultipartBody.Part> {
}
