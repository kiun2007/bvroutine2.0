package kiun.com.bvroutine.cacheline;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kiun.com.bvroutine.net.CacheInterceptor;
import kiun.com.bvroutine.utils.MCString;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Invocation;

public class CacheLineInterceptor implements CacheInterceptor {

    private Map<String, String> header;

    @Override
    public void setLoginHeader(Map<String, String> header) {
        this.header = header;
    }

    @Override
    public void onCacheData(Request request, String body) {
        requestCacheLine(request, body);
    }

    private void fillStandardHeaders(Response.Builder responseHeaders) {

        responseHeaders.addHeader("Transfer-Encoding", "chunked");
        responseHeaders.addHeader("Server", "nginx/1.14.2");
        responseHeaders.addHeader("Access-Control-Allow-Origin", "*");
        responseHeaders.addHeader("Access-Control-Allow-Credentials", "true");
        responseHeaders.addHeader("Access-Control-Allow-Methods", "*");
        responseHeaders.addHeader("Connection", "keep-alive");
        responseHeaders.addHeader("Access-Control-Max-Age", "3600");
        responseHeaders.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,token,persId,source");
        responseHeaders.addHeader("Date", new Date().toGMTString());
        responseHeaders.addHeader("Content-Type", "application/json;charset=utf-8");
    }

    @Override
    public boolean isOffline() {
        return !CacheSettings.isOnline();
    }

    private Object getFieldValue(Object requestBody, String fieldName, Type type) {

        try {
            Field field = requestBody.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object object = field.get(requestBody);
            if (field.getType() == type) {
                return object;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> getMultipartBodyFile(MultipartBody body) {

        if (body == null || body.parts().size() == 0) {
            return null;
        }

        Map<String, Object> multipart = new HashMap<>();

        try {
            Field field = body.part(0).getClass().getDeclaredField("body");
            field.setAccessible(true);
            List<MultipartBody.Part> parts = (List<MultipartBody.Part>) getFieldValue(body, "parts", List.class);

            for (MultipartBody.Part part : parts){
                String disposition = part.headers().get("Content-Disposition");
                Matcher matcher = Pattern.compile("name=\"(.+?)\"").matcher(disposition);

                String name = matcher.find() ? matcher.group(1) : null;

                if (name == null) continue;
                RequestBody partBody = part.body();

                if (partBody != null){

                    if ("application".equals(partBody.contentType().type())){
                        String content = (String) getFieldValue(partBody, "content", String.class);
                        multipart.put(name, content);
                    }else if (MCString.isWith(partBody.contentType().type(), "image", "video")){
                        byte[] content = (byte[]) getFieldValue(partBody, "val$content", byte[].class);
                        multipart.put(name, content);
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return multipart;
    }

    private Response requestCacheLine(Request request, Object body){

        MediaType mediaType = null;
        byte[] bodyBytes = body instanceof String ? ((String) body).getBytes() : null;

        Invocation invocation = request.tag(Invocation.class);

        for (String key : request.headers().names()) {
            header.put(key, request.header(key));
        }

        byte[] responseData = null;

        try {
            responseData = CacheSettings.cacheIn(invocation, header, body == null ? null : bodyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (body != null){
            return null;
        }

        mediaType = MediaType.parse("application/json;charset=utf-8");
        Response.Builder responseBuilder = new Response.Builder();

        fillStandardHeaders(responseBuilder);
        responseBuilder.request(request);
        responseBuilder.protocol(Protocol.HTTP_1_1);

        if (responseData != null){
            responseBuilder.code(200);
            responseBuilder.message("OK");
            responseBuilder.body(ResponseBody.create(mediaType, responseData));
        }else{
            responseBuilder.code(500);
            responseBuilder.message("无缓存数据");
        }
        return responseBuilder.build();
    }

    /**
     * 过滤方法
     * @param chain
     * @return 如果返回非null 将会作为返回值.
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        Invocation invocation = chain.request().tag(Invocation.class);
        CacheSet cacheSet = invocation.method().getAnnotation(CacheSet.class);

        //请求地址.
        if (isOffline() || cacheSet.OnlyCache()){
            Request request = chain.request();
            return requestCacheLine(request, null);
        }
        return null;
    }
}
