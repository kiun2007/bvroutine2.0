package kiun.com.bvroutine.base.binding.convert;

import java.util.Date;

import kiun.com.bvroutine.utils.MCString;

public class DateFormatYMD extends TypeConvert<Date, String> {

    @Override
    protected String convert(Date value) {
        if (value != null){
            return MCString.formatDate("yyyy-MM-dd", value);
        }
        return null;
    }

    @Override
    protected Date convertFrom(String value) {
        return MCString.dateByFormat(value, "yyyy-MM-dd");
    }
}
