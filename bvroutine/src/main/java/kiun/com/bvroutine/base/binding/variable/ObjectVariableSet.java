package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Method;

public class ObjectVariableSet<T> extends VariableBinding<T>{

    public ObjectVariableSet(Context context, ViewDataBinding viewDataBinding, Class<? extends T> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        try {
            Object object = clz.newInstance();
            setVariable((T) object);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {
    }
}
