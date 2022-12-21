package kiun.com.bvroutine.net;

import android.util.Log;

import java.io.IOException;
import kiun.com.bvroutine.data.json.GsonInstance;
import kiun.com.bvroutine.interfaces.TypeRequest;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class GsonRequestConverter<T> implements Converter<T, RequestBody> {

    @Override
    public RequestBody convert(T value) throws IOException {

        String jsonString = value instanceof String ? (String) value : GsonInstance.getInstance().toJson(value);
        Log.i("RequestBody", jsonString);
        if (value instanceof TypeRequest){
            return new AesRequestBody(jsonString, ((TypeRequest) value).getType());
        }
        return new AesRequestBody(jsonString);
    }
}
