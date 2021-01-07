package kiun.com.bvroutine.net;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import kiun.com.bvroutine.utils.AesEncryptUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;

public class AesRequestBody extends RequestBody {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private String content;

    private byte[] body;

    /**
     * 返回类型.
     */
    private Class itemClz;

    Charset charset = StandardCharsets.UTF_8;

    public AesRequestBody(String content) {
        this.content = content;
        body = content.getBytes(charset);
    }

    public AesRequestBody(String content, Class clz){
        this(content);
        this.itemClz = clz;
    }

    @Override
    public MediaType contentType() {
        return MEDIA_TYPE;
    }

    public Class getItemClz() {
        return itemClz;
    }

    @Override
    public long contentLength() throws IOException {
        return body == null ? -1 : body.length;
    }

    public void encryption(String encode){
        try {
            content = AesEncryptUtils.encrypt(content, encode);
            body = content.getBytes(charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.write(body, 0, body.length);
    }
}
