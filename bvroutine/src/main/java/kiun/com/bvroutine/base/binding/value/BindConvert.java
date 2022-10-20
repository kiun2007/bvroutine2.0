package kiun.com.bvroutine.base.binding.value;

import android.view.View;

import androidx.databinding.InverseBindingListener;

import java.util.regex.Pattern;

import kiun.com.bvroutine.base.binding.convert.TypeConvert;
import kiun.com.bvroutine.interfaces.callers.ObjectSetCaller;
import kiun.com.bvroutine.utils.JexlUtil;

/**
 * 绑定转化器.
 */
public abstract class BindConvert<V extends View, F, T> {

    protected V view;

    protected InverseBindingListener listener;

    protected TypeConvert<Object,T> typeConvert;

    protected ObjectSetCaller changedCaller;

    protected T nowValue;

    protected String initParam;

    public abstract T getValue();

    public abstract void setValue(F value);

    public boolean isOnlySet(){
        return false;
    }

    public BindConvert(V view) {
        this.view = view;

        if (view.getTransitionName() != null){
            String className = view.getTransitionName();
            typeConvert = JexlUtil.run(className);
        }
    }

    public void initOfParam(String param){
        initParam = param;
    }

    public void setListener(InverseBindingListener listener) {
        this.listener = listener;
    }

    public void setChangedCaller(ObjectSetCaller changedCaller) {
        this.changedCaller = changedCaller;
    }

    public void replace(BindConvert bindConvert){
        bindConvert.setListener(listener);
        bindConvert.setChangedCaller(changedCaller);
    }

    public void onChanged(){
        if (listener != null){
            listener.onChange();
        }
    }

    public void onChanged(T value){
        nowValue = value;
        if (changedCaller != null){
            changedCaller.call(value);
        }
        onChanged();
    }

    protected T convert(Object object){
        if (typeConvert != null){
            return typeConvert.to(object);
        }
        return (T) object;
    }

    protected F convertFrom(T object){
        if (typeConvert != null){
            return (F) typeConvert.from(object);
        }
        return (F) object;
    }
}
