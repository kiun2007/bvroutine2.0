package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kiun.com.bvroutine.base.EventBean;

public abstract class VariableBinding<C> {

    /**
     * 上下文
     */
    protected Context context;

    /**
     * 绑定对象.
     */
    protected ViewDataBinding viewDataBinding;

    /**
     * 需要绑定对象的类.
     */
    protected Class<? extends C> clz;

    protected C value;

    /**
     * 绑定对象的set方法.
     */
    protected Method method;

    public VariableBinding(Context context, ViewDataBinding viewDataBinding, Class<? extends C> clz, Method method) {
        this.context = context;
        this.viewDataBinding = viewDataBinding;
        this.clz = clz;
        this.method = method;
    }

    /**
     * 开始绑定.
     */
    public abstract void start();

    /**
     * 结束绑定.
     */
    public abstract void end();

    protected void setVariable(C value){
        try {
            method.invoke(viewDataBinding, this.value = value);

            if (value instanceof EventBean){
                ((EventBean) value).bind(0, viewDataBinding);
            }

            if (context instanceof AutoImportHandler){
                ((AutoImportHandler) context).onImportComplete(value);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
