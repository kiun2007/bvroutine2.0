package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.widget.RadioGroup;

import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.views.section.RadioGroupRow;

public class RadioGroupRowBindConvert extends BindConvert<RadioGroupRow, String, String> implements RadioGroup.OnCheckedChangeListener {

    public RadioGroupRowBindConvert(RadioGroupRow view) {
        super(view);
        view.setOnCheckedChangeListener(this);
    }

    @Override
    public String getValue() {
        return nowValue;
    }

    @Override
    public void setValue(String value) {
        nowValue = value;
        view.setValue(value);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        View selected = group.findViewById(checkedId);
        if (selected != null && selected.isPressed()){
            onChanged((String)selected.getTag());
        }
    }
}
