package kiun.com.bvroutine.base.binding.variable;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import kiun.com.bvroutine.base.ServiceBinder;

/**
 * 服务持有者
 */
public class ServiceHandlerVariableSet extends VariableBinding<Object> implements ServiceConnection {

    private Constructor constructor;
    private Class selectClz;

    public ServiceHandlerVariableSet(Context context, ViewDataBinding viewDataBinding, Class<?> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {

        for (Constructor constructor : clz.getConstructors()){
            Class<?>[] parameter = constructor.getParameterTypes();
            if (parameter.length == 1 && Service.class.isAssignableFrom(parameter[0])){
                context.bindService(new Intent(context, parameter[0]), this, Context.BIND_AUTO_CREATE);
                selectClz = parameter[0];
                this.constructor = constructor;
                break;
            }
        }
    }

    @Override
    public void end() {

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof ServiceBinder){
            Service bindService = ((ServiceBinder) service).getService();
            if (Objects.equals(selectClz, bindService.getClass())){
                try {
                    setVariable(constructor.newInstance(bindService));
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
