package kiun.com.bvroutine.base.binding.convert;

public class TextInteger extends TypeConvert<String,Integer>{
    @Override
    protected Integer convert(String value) {
        return Integer.parseInt(value);
    }

    @Override
    protected String convertFrom(Integer value) {
        return String.valueOf(value);
    }
}
