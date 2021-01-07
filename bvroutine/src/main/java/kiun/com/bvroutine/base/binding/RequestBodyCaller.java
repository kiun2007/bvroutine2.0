package kiun.com.bvroutine.base.binding;

import kiun.com.bvroutine.interfaces.callers.GetObjectCaller;
import okhttp3.RequestBody;

@FunctionalInterface
public interface RequestBodyCaller extends GetObjectCaller<RequestBody> {
}
