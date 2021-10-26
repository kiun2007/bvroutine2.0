package kiun.com.bvroutine.net;

import java.io.IOException;

import retrofit2.Converter;

public class ValueFactory implements Converter<Object, String> {

    @Override
    public String convert(Object value) throws IOException {
        if (value == null){
            return "";
        }
        return value.toString();
    }
}
