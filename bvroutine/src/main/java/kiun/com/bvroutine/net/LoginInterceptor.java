package kiun.com.bvroutine.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.ActivityApplication;
import okhttp3.Headers;

/**
 * 登陆操作实现类.
 * @param <APP>
 */
public abstract class LoginInterceptor<APP extends ActivityApplication> {

    public static final String SAVE_KEY = "login_class";

    protected APP application;

    protected String authorizeToken;

    protected Map<String, String> headers = new HashMap<>();

    protected boolean isLogin = false;

    public LoginInterceptor(APP activityApplication){
        application = activityApplication;
    }

    protected String getSessionId(Headers headers){

        String cookie = null;
        for (String setCookie : headers.values("Set-Cookie")) {
            if(setCookie.contains("JSESSIONID")){
                cookie = setCookie;
            }
        }

        if(cookie == null){
            return null;
        }

        String[] params = cookie.split(";");
        if (params.length > 1){
            return params[0].split("=")[1];
        }
        return null;
    }

    protected final Object getBody(String body, Class clz){
        return JSON.parseObject(body, clz);
    }

    /**
     * 清除授权令牌.
     * @return 此次清除是否为有效清除.
     */
    protected boolean clearToken(){
        String clearLast = authorizeToken;
        authorizeToken = null;
        return clearLast != null;
    }

    /**
     * 获取验证令牌.
     * @return 令牌.
     */
    public abstract String getAuthorizeToken();

    /**
     * 提取令牌.
     * @param headers 返回头部信息.
     * @param body 返回body.
     */
    public abstract void refineToken(Headers headers, String body);

    /**
     * 每次请求填入的头部.
     * @return 头部信息.
     */
    public abstract Map<String, String> getHeader();

    /**
     * 清除令牌.
     */
    public abstract void clear();

    /**
     * 获取错误信息
     * @param content
     * @return
     */
    public abstract String errorContent(String content);

    public boolean isLogin(){
        return !TextUtils.isEmpty(authorizeToken);
    }
}
