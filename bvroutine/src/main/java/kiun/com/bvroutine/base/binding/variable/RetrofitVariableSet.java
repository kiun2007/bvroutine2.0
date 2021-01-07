package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Method;

import kiun.com.bvroutine.net.ServiceGenerator;

/**
 * Retrofit2 服务通用创建器.
 */
public class RetrofitVariableSet extends VariableBinding<Object>{

    public RetrofitVariableSet(Context context, ViewDataBinding viewDataBinding, Class<Object> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        setVariable(ServiceGenerator.createService(clz));
    }

    @Override
    public void end() {

    }
}
