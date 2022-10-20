package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Method;

import kiun.com.bvroutine.interfaces.handler.ViewLoadHandler;

public class ViewLoadVariableSet extends ObjectVariableSet<ViewLoadHandler> {

    private OnRebindCallback onRebindCallback = new OnRebindCallback() {
        @Override
        public void onBound(ViewDataBinding binding) {
            super.onBound(binding);
            viewDataBinding.removeOnRebindCallback(onRebindCallback);
            if (value.isInitStart()){
                value.start();
            }
        }
    };

    public ViewLoadVariableSet(Context context, ViewDataBinding viewDataBinding, Class<? extends ViewLoadHandler> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        super.start();
        value.setContext(context);
        viewDataBinding.addOnRebindCallback(onRebindCallback);
    }
}
