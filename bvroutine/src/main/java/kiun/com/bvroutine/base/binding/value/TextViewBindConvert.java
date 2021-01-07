package kiun.com.bvroutine.base.binding.value;

import android.widget.TextView;

public class TextViewBindConvert extends BindConvert<TextView, Object, Object>{

    public TextViewBindConvert(TextView view) {
        super(view);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {
        view.setText((CharSequence) convertFrom(value));
    }
}
