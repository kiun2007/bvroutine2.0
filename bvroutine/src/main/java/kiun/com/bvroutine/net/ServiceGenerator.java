package kiun.com.bvroutine.net;

import android.os.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kiun.com.bvroutine.interfaces.callers.SetCaller;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ServiceGenerator {

    private static final String TAG = "ServiceGenerator";
    private static final String CHECK_PARAMS = "checkParams";
    public static String AppSecurity = "a.8&media_menu@12Ne^qe890-com";
    public static final String MAIN = "MAIN_SERVICE_BUILDER";

    private static Map<String, ServiceGenerator> allServices = new HashMap<>();

    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private String basePrefix;
    private Interceptor interceptor;
    private OkHttpClient okHttpClient;

    private ServiceGenerator(Interceptor interceptor, String prefix, String key, Class<? extends LoginInterceptor> loginClz, SetCaller<OkHttpClient.Builder> builderSetCaller){

        basePrefix = prefix;

        // 初始化http请求配置
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // 连接超时时间
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        // 连接写入超时时间
        httpClient.writeTimeout(120, TimeUnit.SECONDS);
        // 链接读取超时时间
        httpClient.readTimeout(120, TimeUnit.SECONDS);

        if (interceptor == null || (interceptor instanceof CacheInterceptor)){

            CacheInterceptor cacheInterceptor = (interceptor != null) ? (CacheInterceptor) interceptor : null;
            if (loginClz != null){
                interceptor = new HttpInterceptor(loginClz, cacheInterceptor);
            } else {
                interceptor = new HttpInterceptor(key, cacheInterceptor);
            }
        }

        if (builderSetCaller != null){
            builderSetCaller.call(httpClient);
        }

        // http缓存大小
        httpClient.cache(new Cache(Environment.getDataDirectory(), 8 * 1024 * 1024));
        httpClient.addInterceptor(interceptor);
        httpClient.retryOnConnectionFailure(true);

        okHttpClient = httpClient.build();

        builder = new Retrofit.Builder().baseUrl(prefix).addConverterFactory(new FastJSONFactory());
        retrofit = builder.client(okHttpClient).build();

        this.interceptor = interceptor;
    }

    public static void putBuild(Interceptor interceptor, String prefix, String key,
                                Class<? extends LoginInterceptor> loginClz,
                                SetCaller<OkHttpClient.Builder> builderSetCaller){

        ServiceGenerator serviceGenerator = new ServiceGenerator(interceptor, prefix, key, loginClz, builderSetCaller);
        allServices.put(key, serviceGenerator);
    }

    public static void putBuild(Interceptor interceptor, String prefix, String key, Class<? extends LoginInterceptor> loginClz){
        putBuild(interceptor, prefix, key, loginClz, null);
    }

    public static void putBuild(Interceptor interceptor, String prefix, String key){
        putBuild(interceptor, prefix, key, null);
    }

    public static void putBuild(String prefix, String key){
        putBuild(null, prefix, key);
    }

    public static void putBuild(String prefix){
        putBuild(prefix, MAIN);
    }

    public static String getBasePrefix(String key) {

        if (allServices.get(key) != null){
            return allServices.get(key).basePrefix;
        }
        return null;
    }

    public static String getBasePrefix(){
        return getBasePrefix(MAIN);
    }

    public static String getUrl(String key, String path){
        return getBasePrefix(key) + path;
    }

    public static String getUrl(String path){
        return getBasePrefix(MAIN) + path;
    }

    /**
     * 清除全部授权.
     */
    public static void clearAuthorize() {
        for (ServiceGenerator serviceGenerator : allServices.values()){
            if (serviceGenerator.interceptor instanceof HttpInterceptor){
                ((HttpInterceptor) serviceGenerator.interceptor).clearAuthorize();
            }
        }
    }

    public static OkHttpClient getMainClient(){
        if (!allServices.containsKey(MAIN)){
            return null;
        }
        return allServices.get(MAIN).okHttpClient;
    }

    /**
     * 清除指定授权.
     * @param clz
     */
    public static void clearAuthorize(Class clz){

        String key = getKeyName(clz);
        ServiceGenerator serviceGenerator = allServices.get(key);

        if (serviceGenerator != null){
            ((HttpInterceptor) serviceGenerator.interceptor).clearAuthorize();
        }
    }

    public static LoginInterceptor getLogin(String key){
        if (allServices.get(key) != null && allServices.get(key).interceptor instanceof HttpInterceptor) {
            return  ((HttpInterceptor) allServices.get(key).interceptor).getLogin();
        }
        return null;
    }

    public static LoginInterceptor getLogin(){
        return getLogin(MAIN);
    }

    /**
     * 主服务构建器是否登陆.
     * @return true已登陆.
     */
    public static boolean isLogin(){
        return isLogin(MAIN);
    }

    /**
     * 服务构建器是否登陆.
     * @param key 服务构建器名称.
     * @return
     */
    public static boolean isLogin(String key){
        if (allServices.get(key) != null && allServices.get(key).interceptor instanceof HttpInterceptor){
            return ((HttpInterceptor) allServices.get(key).interceptor).isLogin();
        }
        return false;
    }

    private static String getKeyName(Class clz){
        Builder builder = (Builder) clz.getAnnotation(Builder.class);
        String key = MAIN;

        if (builder != null){
            key = builder.value();
        }
        return key;
    }

    public static <S> S createService(Class<S> serviceClass) {
        String key = getKeyName(serviceClass);
        if (allServices.get(key) != null){
            return allServices.get(key).retrofit.create(serviceClass);
        }
        return null;
    }
}
