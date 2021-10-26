package kiun.com.bvroutine.base.binding.value;

import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.views.text.DrawTextView;

public class DrawTextViewBindConvert extends BindConvert<DrawTextView, Object, Integer> {

    public DrawTextViewBindConvert(DrawTextView view) {
        super(view);
    }

    @Override
    public Integer getValue() {
        return nowValue;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Integer){
            view.setIndex((Integer) value);
        }else if (value instanceof String){
            view.setIndex(MCString.toNumber((String) value).intValue());
        }
    }
}
