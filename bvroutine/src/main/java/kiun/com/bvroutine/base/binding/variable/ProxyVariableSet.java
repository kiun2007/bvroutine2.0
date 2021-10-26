package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Method;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.jexl.ProxyHandler;

/**
 * 代理自动导入类,实现自动调用父类方法.
 * @param <T>
 */
public class ProxyVariableSet<T> extends VariableBinding<T>{

    public ProxyVariableSet(Context context, ViewDataBinding viewDataBinding, Class<? extends T> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {

        T value = ProxyHandler.createBy(context, clz);

        if (value instanceof EventBean){
            EventBean eventBean = (EventBean) value;
            eventBean.listener(v -> this.setVariable((T) v));
        }
        setVariable(value);
    }

    @Override
    public void end() {
    }
}
