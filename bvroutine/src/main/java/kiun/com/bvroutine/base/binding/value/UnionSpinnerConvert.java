package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Objects;

import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.view.FormView;
import kiun.com.bvroutine.presenters.SpinnerUnion;
import kiun.com.bvroutine.presenters.listener.ListenerController;
import kiun.com.bvroutine.utils.JexlUtil;

public class UnionSpinnerConvert extends BindConvert<Spinner, Object, Object> implements AdapterView.OnItemSelectedListener, FormView {

    /**
     * 值获取接口
     */
    private GetCaller valueGetCaller;

    private SetCaller onValueChanged;

    public void setOnValueChanged(SetCaller onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    public UnionSpinnerConvert(Spinner view, GetCaller caller) {
        super(view);
        ListenerController.regListener(view, this);
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

        int selectIndex = 0;
        for (int i = 0; i < view.getAdapter().getCount(); i++) {
            Object item = view.getAdapter().getItem(i);
            if (item instanceof SpinnerUnion.Empty){
                continue;
            }
            if(item != null){
                Object itemValue = valueGetCaller.call(item);
                if (Objects.equals(o, itemValue)){
                    selectIndex = i;
                    break;
                }
            }
        }

        view.setSelection(selectIndex);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ListenerController.execItemClick(this, parent, view, position, id);
        Object selectItem = parent.getAdapter().getItem(position);
        if (selectItem instanceof SpinnerUnion.Empty){
            onChanged(null);
        } else {
            onChanged(valueGetCaller.call(selectItem));
        }

        if(onValueChanged != null){
            onValueChanged.call(selectItem);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    public static class Builder extends BindConvertBuilder<Spinner> {

        private GetCaller caller;

        public Builder(GetCaller caller) {
            this.caller = caller;
        }

        @Override
        public BindConvert<Spinner, ?, ?> build(Spinner appCompatSpinner) {
            return new UnionSpinnerConvert(appCompatSpinner, caller);
        }
    }
}
