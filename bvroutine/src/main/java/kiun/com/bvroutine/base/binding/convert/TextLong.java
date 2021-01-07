package kiun.com.bvroutine.base.binding.convert;

public class TextLong extends TypeConvert<String, Long>{

    @Override
    protected Long convert(String value) {
        return Long.parseLong(value);
    }

    @Override
    protected String convertFrom(Long value) {
        return String.valueOf(value);
    }
}
