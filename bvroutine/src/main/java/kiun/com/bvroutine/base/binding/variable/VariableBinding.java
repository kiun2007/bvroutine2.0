package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.type.ClassUtil;

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

    protected String getFieldName(){
        if (this.method != null){
            return MCString.getFieldName(this.method.getName());
        }
        return null;
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

            String packageName = context.getPackageName();
            if (ActivityApplication.getAppPackageName() != null){
                packageName = ActivityApplication.getAppPackageName();
            }

            int br = -1;

            try {
                Class BRClz = Class.forName(packageName+".BR");
                br = (int) BRClz.getField(getFieldName()).get(null);
            } catch (NoSuchFieldException|ClassNotFoundException e) {
            }

            if (value instanceof EventBean){
                ((EventBean) value).bind(br, viewDataBinding);
            }

            if (context instanceof AutoImportHandler){
                Class clz = ClassUtil.findInterfaceGenericType(context.getClass(), AutoImportHandler.class, 0);
                if (value != null){
                    if (clz == null) clz = Object.class;

                    if (Objects.equals(clz, value.getClass())){
                        ((AutoImportHandler) context).onImportComplete(value);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
