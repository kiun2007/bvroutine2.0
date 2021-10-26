package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kiun.com.bvroutine.base.EventBean;

public class ContextHandlerVariableSet extends VariableBinding<Object>{

    public ContextHandlerVariableSet(Context context, ViewDataBinding viewDataBinding, Class<Object> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        try {
            Object object = clz.getConstructor(Context.class).newInstance(context);
            setVariable(object);

            if (object instanceof EventBean){
                EventBean eventBean = (EventBean) object;
                eventBean.listener(this::setVariable);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {
    }
}
