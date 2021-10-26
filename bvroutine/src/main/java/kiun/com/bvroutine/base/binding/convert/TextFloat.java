package kiun.com.bvroutine.base.binding.convert;

public class TextFloat extends TypeConvert<String, Float>{

    @Override
    protected Float convert(String value) {
        return Float.parseFloat(value);
    }

    @Override
    protected String convertFrom(Float value) {
        return String.valueOf(value);
    }
}
