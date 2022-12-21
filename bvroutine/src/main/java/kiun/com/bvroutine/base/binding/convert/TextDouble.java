package kiun.com.bvroutine.base.binding.convert;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;

public class TextDouble extends TypeConvert<String, Double>{

    private String pattern = "#.#####";

    public TextDouble(){
    }

    public TextDouble(String pattern){
        this.pattern = pattern;
    }

    @Override
    protected Double convert(String value) {
        return Double.parseDouble(value);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected String convertFrom(Double value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }
}
