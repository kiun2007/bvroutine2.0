package kiun.com.bvroutine.base.binding.convert;

import android.annotation.SuppressLint;

public class TextDouble extends TypeConvert<String, Double>{

    @Override
    protected Double convert(String value) {
        return Double.parseDouble(value);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected String convertFrom(Double value) {
        return String.format("%.5f", value);
    }
}
