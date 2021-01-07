package kiun.com.bvroutine.net;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Map;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.security.ConstValue;
import kiun.com.bvroutine.security.EncodeType;
import kiun.com.bvroutine.utils.AesEncryptUtils;
import kiun.com.bvroutine.utils.ByteUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.MD5Util;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.SharedUtil;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;

import static kiun.com.bvroutine.net.LoginInterceptor.SAVE_KEY;

public class HttpInterceptor implements Interceptor {

    private LoginInterceptor loginInterceptor;
    private String key;
    private Class<? extends LoginInterceptor> loginClass;
    CacheInterceptor cacheInterceptor;

    public HttpInterceptor(String key, CacheInterceptor cacheInterceptor){
        this.key = SAVE_KEY + ":" + key;
        String loginClass = SharedUtil.getValue(this.key, "");
        loginInterceptor = createLogin(loginClass);
        this.cacheInterceptor = cacheInterceptor;
    }

    public HttpInterceptor(Class<? extends LoginInterceptor> loginClass, CacheInterceptor cacheInterceptor){

        this.loginClass = loginClass;
        ActivityApplication application = ActivityApplication.getApplication();
        if (application != null){
            loginInterceptor = ObjectUtil.newObject(loginClass, application);
        }
        this.cacheInterceptor = cacheInterceptor;
    }

    private boolean loginCreate(String loginClass){

        if (!TextUtils.isEmpty(loginClass)) {
            loginInterceptor = createLogin(loginClass);
            SharedUtil.saveValue(this.key, loginClass);
            return true;
        }
        return false;
    }

    private String getAuthorizeToken(boolean isLogin){
        //非登陆操作时恢复登陆对象.
        if (!isLogin){
            if (loginInterceptor == null) {
                if (loginClass != null){
                    loginInterceptor = ObjectUtil.newObject(loginClass, ActivityApplication.getApplication());
                }else{
                    loginInterceptor = createLogin(SharedUtil.getValue(this.key, ""));
                }
            }

            if (loginInterceptor != null){
                return loginInterceptor.getAuthorizeToken();
            }
        }
        return null;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (cacheInterceptor != null){
            if (cacheInterceptor.isOffline()){
                return cacheInterceptor.intercept(chain);
            }
            cacheInterceptor.intercept(chain);
        }

        Request request = chain.request();
        EncodeType encodeType = EncodeType.getType(request.header(ConstValue.Security));

        System.out.println(String.format("URL: %s", request.url().toString()));
        String random = MCString.randNum(8);
        String loginClass = request.header("login");

        boolean isLogin = loginCreate(loginClass);
        String authorizeToken = getAuthorizeToken(isLogin);

        String session;
        session = (TextUtils.isEmpty(authorizeToken) ? "" : ":session:" + authorizeToken);
        String aesEncode = ByteUtil.bytesToHexString(
                MD5Util.MD5(String.format("pwd:%s:seq:%s%s",ServiceGenerator.AppSecurity, random, session))
        );

        Request.Builder builder = request.newBuilder();

        //随机数一定要加上.
        builder.addHeader(ConstValue.Sequence, random);
        builder.addHeader("accept", "application/json");

        //参数加密.
        if (encodeType == EncodeType.P && request.body() instanceof FormBody){

            FormBody formBody = (FormBody) request.body();
            FormBody.Builder formBuilder = new FormBody.Builder();

            for (int i = 0; i < formBody.size(); i++) {
                String value = formBody.value(i);
                try {
                    value = AesEncryptUtils.encrypt(formBody.value(i), aesEncode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                formBuilder.add(formBody.name(i), value);
            }
            builder.post(formBuilder.build());
        }

        Class itemClz = null;
        if (request.body() instanceof AesRequestBody){

            AesRequestBody aesRequestBody = (AesRequestBody) request.body();
            itemClz = aesRequestBody.getItemClz();

            //body加密.
            if (encodeType == EncodeType.B){
                aesRequestBody.encryption(aesEncode);
            }
        }

        if (authorizeToken != null && loginInterceptor != null){
            Map<String, String> header = loginInterceptor.getHeader();

            if (header != null && !header.isEmpty()){
                for (String key : header.keySet()){
                    builder.addHeader(key, header.get(key));
                }
            }
        }

        if (request.method().equals("POST") || request.method().equals("post")){
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), "\n");
            if (request.body().contentLength() == 0){
                builder.post(requestBody);
            }
        }

        Response response = chain.proceed(builder.build());
        String security = response.header(ConstValue.Security);

        if(!response.isSuccessful()){
            throw new HttpException(response);
        }

        Response.Builder responseBuilder = response.newBuilder();
        if (itemClz != null){
            responseBuilder.header("itemClz", itemClz.getName());
        }

        if (!TextUtils.isEmpty(security)){

            String sendSeq = response.header(ConstValue.Sequence);
            session = (TextUtils.isEmpty(authorizeToken) ? "" : ":session:" + authorizeToken);

            String pwd = String.format("pwd:%s:seq:%s%s:send:%s", ServiceGenerator.AppSecurity, random, session , sendSeq);
            String decode = ByteUtil.bytesToHexString(MD5Util.MD5(pwd));

            try {
                String bodyContent = AesEncryptUtils.decrypt(response.body().string(), decode);
                responseBuilder.body(RealResponseBody.create(response.body().contentType(), bodyContent));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Response newResponse = responseBuilder.build();

        //登陆操作.
        if (isLogin && loginInterceptor != null && TextUtils.isEmpty(security)){

            Response.Builder cloneBuilder = response.newBuilder();
            String body = response.body().string();
            cloneBuilder.body(ResponseBody.create(response.body().contentType(), body));

            loginInterceptor.refineToken(response.headers(), body);
            newResponse = cloneBuilder.build();
        }
        return newResponse;
    }

    private static LoginInterceptor createLogin(String loginClass){

        if (!TextUtils.isEmpty(loginClass)){

            try {
                ActivityApplication application = ActivityApplication.getApplication();
                if (application != null){

                    if (loginClass.startsWith(".")){
                        loginClass = application.getPackageName() + loginClass;
                    }

                    Class loginInterceptorClz = Class.forName(loginClass);
                    if (LoginInterceptor.class.isAssignableFrom(loginInterceptorClz)){
                        return (LoginInterceptor) ObjectUtil.newObject(loginInterceptorClz, application);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public LoginInterceptor getLogin() {
        return loginInterceptor;
    }

    boolean isLogin(){
        return loginInterceptor != null && loginInterceptor.isLogin();
    }

    void clearAuthorize(){
        if (loginInterceptor != null) {
            loginInterceptor.clear();
        }
        loginInterceptor = null;
    }
}