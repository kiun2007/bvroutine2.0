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

    public HttpException(Response response){
        super();
        this.response = response;
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

    @Nullable
    @Override
    public String getMessage() {
        return response.request().url().toString() + "\n"
                + codeMap.get(response.code()) + ":" + response.message();
    }
}
