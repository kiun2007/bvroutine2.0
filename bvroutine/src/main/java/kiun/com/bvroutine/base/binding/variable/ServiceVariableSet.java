package kiun.com.bvroutine.base.binding.variable;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Method;

import kiun.com.bvroutine.base.ServiceBinder;

/**
 * Android 服务绑定器.
 */
public class ServiceVariableSet extends VariableBinding<Service> implements ServiceConnection {

    public ServiceVariableSet(Context context, ViewDataBinding viewDataBinding, Class<Service> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        context.bindService(new Intent(context, clz), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void end() {
        context.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        if (service instanceof ServiceBinder){
            setVariable(((ServiceBinder) service).getService());
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }
}
