package kiun.com.bvroutine.cacheline.body;


import kiun.com.bvroutine.utils.MCString;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BytesBodyBuilder extends BaseBodyBuilder<byte[]> {

    @Override
    public RequestBody convert(byte[] value) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", MCString.randUUID(), RequestBody.create(MediaType.parse("multipart/form-data"), value));
        builder.addPart(body);
        return builder.build();
    }
}
