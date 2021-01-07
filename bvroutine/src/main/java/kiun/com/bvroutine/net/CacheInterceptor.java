package kiun.com.bvroutine.net;

import okhttp3.Interceptor;

public interface CacheInterceptor extends Interceptor {

    /**
     * 是否处于离线状态
     * @return
     */
    boolean isOffline();
}
