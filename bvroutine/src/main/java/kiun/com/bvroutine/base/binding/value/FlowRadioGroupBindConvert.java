package kiun.com.bvroutine.base.binding.value;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.views.section.FlowRadioGroup;

public class FlowRadioGroupBindConvert extends BindConvert<FlowRadioGroup, String, String> implements RadioGroup.OnCheckedChangeListener {

    private String splitChar = ",";

    public FlowRadioGroupBindConvert(FlowRadioGroup view) {
        super(view);
        view.setOnCheckedChangeListener(this);
        String name = view.getTransitionName();

        if (name != null){
            splitChar = name;
        }
    }

    @Override
    public String getValue() {
        return nowValue;
    }

    @Override
    public void setValue(String value) {
        if (value != null){

            String[] values = value.split(splitChar);
            for (String item : values){
                CompoundButton radioButton = view.findViewWithTag(item);
                if (radioButton != null){
                    radioButton.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        TextView textView = group.findViewById(checkedId);
        Object selectedTag = textView.getTag();

        if (selectedTag instanceof String){
            if (textView instanceof RadioButton){
                nowValue = (String) selectedTag;
                onChanged();
            } else if (textView instanceof CheckBox){

                List<String> checkList = new LinkedList<>();
                for (int i = 0; i < group.getChildCount(); i++) {

                    if (group.getChildAt(i) instanceof CheckBox){
                        CheckBox checkBox = (CheckBox) group.getChildAt(i);
                        if (checkBox.isChecked() && checkBox.getTag() instanceof String){
                            checkList.add((String) checkBox.getTag());
                        }
                    }
                }
                nowValue = ListUtil.join(checkList,splitChar);
                onChanged();
            }
        }
    }
}
