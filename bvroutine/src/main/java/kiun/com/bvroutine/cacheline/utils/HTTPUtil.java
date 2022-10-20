package kiun.com.bvroutine.cacheline.utils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kiun.com.bvroutine.cacheline.BodyConvertBridge;
import kiun.com.bvroutine.cacheline.body.BaseBodyBuilder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kiun_2007 on 2018/7/23.
 */
public class HTTPUtil {

    public static class Code{
        int code = 0;
        public Code(int code){
            this.code = code;
        }
        public int setCode(int code){
            return this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public static Map<String, Object> upload(String uploadUrl, Map<String, String> header,@NonNull Object body){
         return upload(uploadUrl, header, body, BodyConvertBridge.getBodyBuilder(body.getClass()));
    }

    public static Map<String, Object> upload(String uploadUrl, Map<String, String> header,@NonNull Object body, @NonNull BaseBodyBuilder convert) {

        // 初始化http请求配置
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // 连接超时时间
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        // 连接写入超时时间
        httpClient.writeTimeout(120, TimeUnit.SECONDS);
        // 链接读取超时时间
        httpClient.readTimeout(120, TimeUnit.SECONDS);

        List protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_1_0);
        protocols.add(Protocol.HTTP_1_1);
        protocols.add(Protocol.HTTP_2);
        protocols.add(Protocol.SPDY_3);
        protocols.add(Protocol.QUIC);
        httpClient.protocols(protocols);

        Request.Builder requestBuilder = new Request.Builder().url(uploadUrl);

        for (String key : header.keySet()) {
            requestBuilder.addHeader(key, (String) header.get(key));
        }

        Request request = requestBuilder.post(convert.getBody(body)).build();
        Call call = httpClient.build().newCall(request);
        try {
            Response response = call.execute();
            if(response.code() == 200){
                return getJSONData(response.body().string());
            }else{
                return JSONUtil.createStandardError(response.code(), response.message());
            }
        } catch (IOException e) {
            return JSONUtil.createStandardError(1001, e.getMessage());
        }
    }

    private static Map<String, Object> getJSONData(String json){
        return (Map<String, Object>) JSON.parse(json);
    }

    public static Map postURLJSON(String url, Map<String, Object> data, Map<String, String> header){

        Code code = new Code(1001);
        String responseValue = postURL(url, data.toString(), header, code);
        if(code.getCode() != 200){
            return JSONUtil.createStandardError(code.getCode(), responseValue);
        }
        return getJSONData(responseValue);
    }

    public static String postURL(String url, String data, Map<String, String> header, Code code){

        if(code == null){
            code = new Code(0);
        }
        try {
            URL getterUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getterUrl.openConnection();
            connection.setRequestMethod("POST");
            if (header != null){
                for (String key : header.keySet()) {
                    connection.setRequestProperty(key, (String) header.get(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(60*1000);
            connection.setReadTimeout(60*1000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            if(data != null){
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(data);
                out.flush();
                out.close();
            }

            InputStream in = null;
            if((code.setCode(connection.getResponseCode())) == 200){
                in = connection.getInputStream();
            }else{
                in = connection.getErrorStream();
            }

            //下面对获取到的输入流进行读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!=null){
                response.append(line);
            }
            return response.toString();
        } catch (MalformedURLException e) {
            code.setCode(1002);
            return e.getMessage();
        } catch (IOException e) {
            code.setCode(1001);
            return e.getMessage();
        }
    }

    public static String postURL(String url, String data){
        return postURL(url, data, null, null);
    }
}
