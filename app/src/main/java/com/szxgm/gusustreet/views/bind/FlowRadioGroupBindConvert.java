package com.szxgm.gusustreet.views.bind;

import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.szxgm.gusustreet.views.FlowRadioGroup;

import kiun.com.bvroutine.base.binding.value.BindConvert;

public class FlowRadioGroupBindConvert extends BindConvert<FlowRadioGroup, String, String> implements RadioGroup.OnCheckedChangeListener {

    public FlowRadioGroupBindConvert(FlowRadioGroup view) {
        super(view);
        view.setOnCheckedChangeListener(this);
    }

    @Override
    public String getValue() {
        return nowValue;
    }

    @Override
    public void setValue(String value) {
        if (value != null){
            RadioButton radioButton = view.findViewWithTag(value);
            if (radioButton != null){
                radioButton.setChecked(true);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        Object selectedTag = group.findViewById(checkedId).getTag();

        if (selectedTag instanceof String){
            nowValue = (String) selectedTag;
            onChanged();
        }
    }
}
