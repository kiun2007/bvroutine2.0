package kiun.com.bvroutine.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import retrofit2.Converter;

public class FastStringConverter implements Converter<Object, String> {

    @Override
    public String convert(Object value) throws IOException {

        if (value instanceof String){
            return (String) value;
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(value);
        StringBuilder builder = new StringBuilder();
        for (String key : jsonObject.keySet()){
            builder.append(key).append("=").append(jsonObject.getString(key)).append("&");
        }

        if (builder.length() > 1){
            return builder.substring(0, builder.length() - 1);
        }
        return builder.toString();
    }
}
