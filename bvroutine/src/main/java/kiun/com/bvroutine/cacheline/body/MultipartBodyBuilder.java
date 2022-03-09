package kiun.com.bvroutine.cacheline.body;

import java.io.File;
import java.util.Map;

import kiun.com.bvroutine.utils.MCString;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MultipartBodyBuilder extends BaseBodyBuilder<Map<String,Object>> {

    public MultipartBody.Part createMultipartBody(byte[] file, String keyName){
        return createMultipartBody(file, keyName, MCString.randUUID() + ".jpg");
    }

    public MultipartBody.Part createMultipartBody(byte[] file, String keyName, String fileName){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(keyName, fileName, requestFile);
    }

    public MultipartBody.Part createMultipartBody(File file, String keyName, String fileName){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(keyName, fileName, requestFile);
    }

    @Override
    public RequestBody convert(Map<String, Object> value) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (String key : value.keySet()){
            Object v = value.get(key);
            if (v instanceof byte[]){
                builder.addPart(createMultipartBody((byte[]) v, key));
            } else {
                builder.addPart(MultipartBody.Part.createFormData(key, null, RequestBody.create(MediaType.parse("application/json"), String.valueOf(v))));
            }
        }
        return builder.build();
    }
}
