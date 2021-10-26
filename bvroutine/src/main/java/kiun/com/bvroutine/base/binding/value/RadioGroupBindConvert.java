package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RadioGroupBindConvert extends BindConvert<RadioGroup, Object, Object> implements RadioGroup.OnCheckedChangeListener {

    private Object selectTag;

    private boolean isSet = false;

    public RadioGroupBindConvert(RadioGroup view) {
        super(view);
        view.setOnCheckedChangeListener(this);
    }

    @Override
    public Object getValue() {
        return selectTag;
    }

    @Override
    public void setValue(Object value) {
        if (value != null){
            RadioButton radioButton = view.findViewWithTag(value);
            isSet = true;
            if (radioButton != null){
                radioButton.setChecked(true);
            }
            isSet = false;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        View view = group.findViewById(checkedId);
        selectTag = view.getTag();

        if (!isSet){
            onChanged();
        }
    }
}