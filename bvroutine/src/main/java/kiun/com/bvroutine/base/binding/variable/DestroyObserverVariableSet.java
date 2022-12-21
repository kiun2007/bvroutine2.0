package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Method;

public class DestroyObserverVariableSet extends VariableBinding<DestroyObserver>{

    public DestroyObserverVariableSet(Context context, ViewDataBinding viewDataBinding, Class<? extends DestroyObserver> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        try {
            Object object = clz.newInstance();
            setVariable((DestroyObserver) object);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {
        if(value != null){
            value.onDestroy();
        }
    }
}
