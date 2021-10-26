package kiun.com.bvroutine.net;

import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;

public interface CacheInterceptor extends Interceptor {

    /**
     *
     * @param header
     */
    void setLoginHeader(Map<String, String> header);

    /**
     * 需要缓存数据
     * @param request 请求对象.
     * @param body 存储数据
     */
    void onCacheData(Request request, String body);

    /**
     * 是否处于离线状态
     * @return
     */
    boolean isOffline();
}
