package kiun.com.bvroutine.base.binding.value;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextBindConvert extends BindConvert<EditText, Object, Object> implements TextWatcher {

    private Object lastValue = null;

    public EditTextBindConvert(EditText view) {
        super(view);
        view.addTextChangedListener(this);
    }

    @Override
    public Object getValue() {

        if (view.getText().length() == 0){
            return null;
        }

        String value = view.getText().toString();
        return lastValue = convert(value);
    }

    @Override
    public void setValue(Object value) {
        if (value != null && !value.equals(lastValue)) {
            view.setText((String)convertFrom(value));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (listener != null && view.isEnabled()){
            listener.onChange();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        
    }
}
