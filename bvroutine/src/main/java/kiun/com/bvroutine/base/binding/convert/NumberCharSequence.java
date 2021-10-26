package kiun.com.bvroutine.base.binding.convert;

import kiun.com.bvroutine.utils.MCString;

public class NumberCharSequence extends TypeConvert<CharSequence, Number>{

    private int retain = 2;

    public NumberCharSequence(){}

    public NumberCharSequence(int retain) {
        this();
        this.retain = retain;
    }

    @Override
    protected Number convert(CharSequence value) {
        return Double.parseDouble(value.toString());
    }

    @Override
    protected CharSequence convertFrom(Number value) {
        return MCString.decimalFormat(value, retain);
    }
}
