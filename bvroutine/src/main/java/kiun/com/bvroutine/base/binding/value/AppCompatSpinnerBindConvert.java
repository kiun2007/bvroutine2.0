package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Arrays;
import java.util.Collection;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.binding.ValueSetter;
import kiun.com.bvroutine.interfaces.view.FormView;
import kiun.com.bvroutine.presenters.listener.ListenerController;
import kiun.com.bvroutine.utils.JexlUtil;

public class AppCompatSpinnerBindConvert extends BindConvert<AppCompatSpinner, Object, Object> implements AdapterView.OnItemSelectedListener, ValueSetter, FormView {

    private Object[] values;

    public AppCompatSpinnerBindConvert(AppCompatSpinner view) {
        super(view);
        ListenerController.regListener(view, this);
        view.setOnItemSelectedListener(this);
        values = view.getTag(R.id.tagSpinnerValues) == null ? null: ((Collection) view.getTag(R.id.tagSpinnerValues)).toArray();

        if (values == null){
            values = new String[999];
            for (int i = 0; i < 999; i++) {
                values[i] = String.valueOf(i + 1);
            }
        }
    }

    @Override
    public Object getValue() {
        return nowValue;
    }

    @Override
    public void setValue(Object value) {

        if (values == null){
            return;
        }

        int index = Arrays.asList(values).indexOf(value);

        if (index >= 0){
            view.setSelection(index);
        }
        nowValue = value;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ListenerController.execItemClick(this, parent, view, position, id);
        if (values != null && values.length > position){
            nowValue = values[position];
            onChanged(nowValue);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public void setValues(Collection values) {
        if (values != null){
            this.values = values.toArray();
            setValue(nowValue);
        }
    }

    @Override
    public String string(String name) {

        if (name != null){
            return JexlUtil.run(name, "select", nowValue);
        }
        return null;
    }

    @Override
    public String pwd(String name) {
        return null;
    }

    @Override
    public int intValue(String name) {
        return 0;
    }
}
