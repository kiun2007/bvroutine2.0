package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Objects;

import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class UnionSpinnerConvert extends BindConvert<AppCompatSpinner, Object, Object> implements AdapterView.OnItemSelectedListener {

    /**
     * 值获取接口
     */
    private GetCaller valueGetCaller;

    private SetCaller onValueChanged;

    public void setOnValueChanged(SetCaller onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    public UnionSpinnerConvert(AppCompatSpinner view, GetCaller caller) {
        super(view);
        view.setOnItemSelectedListener(this);
        this.valueGetCaller = caller;
    }

    public UnionSpinnerConvert(AppCompatSpinner view) {
        super(view);
    }

    @Override
    public Object getValue() {
        return nowValue;
    }

    @Override
    public void setValue(Object o) {
        if(view.getAdapter() == null){
            return;
        }

        for (int i = 0; i < view.getAdapter().getCount(); i++) {
            Object item = view.getAdapter().getItem(i);
            if(item != null){
                Object itemValue = valueGetCaller.call(item);
                if (Objects.equals(o, itemValue)){
                    view.setSelection(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object selectItem = parent.getAdapter().getItem(position);
        onChanged(valueGetCaller.call(selectItem));

        if(onValueChanged != null){
            onValueChanged.call(selectItem);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public static class Builder extends BindConvertBuilder<AppCompatSpinner> {

        private GetCaller caller;

        public Builder(GetCaller caller) {
            this.caller = caller;
        }

        @Override
        public BindConvert<AppCompatSpinner, ?, ?> build(AppCompatSpinner appCompatSpinner) {
            return new UnionSpinnerConvert(appCompatSpinner, caller);
        }
    }
}
