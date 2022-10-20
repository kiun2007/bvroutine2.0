package kiun.com.bvroutine.net;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class HttpException extends IOException {

    private int code = 200;
    Response response;
    private Map<Integer, String> codeMap = new HashMap<>();
    private String body;

    public HttpException(Response response){
        super();
        this.response = response;
        try{
            body = response.body().string();
        } catch (Exception ex){

        }
        code = response.code();
        codeMap.put(401, "授权无效!");
        codeMap.put(403, "请求被拒绝!");
        codeMap.put(404, "请求页面不存在!");
        codeMap.put(416, "请求中又没有定义 If-Range 请求头");
        codeMap.put(500, "服务器发生未知错误!");
    }

    public Response getResponse() {
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Nullable
    @Override
    public String getMessage() {
        if (body != null){
            return body;
        }
        return response.request().url().toString() + "\n"
                + codeMap.get(response.code()) + ":" + response.message();
    }
}
