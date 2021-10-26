package kiun.com.bvroutine.base.binding.convert;

public class ObjectToString extends TypeConvert<Object, String>{

    @Override
    protected String convert(Object value) {
        return value.toString();
    }

    @Override
    protected Object convertFrom(String value) {
        return null;
    }
}
