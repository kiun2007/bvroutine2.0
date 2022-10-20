package kiun.com.bvroutine.utils.auto;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.VariableBinding;

public class AutoVariableLifecycleOwner implements LifecycleOwner, LifecycleObserver {

    private Context context;

    private List<VariableBinding> variableBindingList = new LinkedList<>();

    public AutoVariableLifecycleOwner(Context context, LifecycleOwner lifecycleOwner) {
        this.context = context;

        if (lifecycleOwner != null){
            lifecycleOwner.getLifecycle().addObserver(this);
        }

        if (context instanceof LifecycleOwner){
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        for (VariableBinding variableBinding : variableBindingList){
            variableBinding.end();
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return autoVariableLifecycle;
    }

    private void fullServiceVariable(ViewDataBinding binding){

        for (Method method : binding.getClass().getMethods()){

            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1){
                Class<?> parameterClz = method.getParameterTypes()[0];
                AutoImport autoImport = parameterClz.getAnnotation(AutoImport.class);
                if (autoImport != null){
                    try {
                        VariableBinding<?> variableBinding = autoImport.value().
                                getConstructor(Context.class,ViewDataBinding.class,Class.class, Method.class).newInstance(context, binding, parameterClz, method);
                        variableBinding.start();
                        variableBindingList.add(variableBinding);

                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Lifecycle autoVariableLifecycle = new Lifecycle() {

        @Override
        public void addObserver(@NonNull LifecycleObserver observer) {

            ViewDataBinding binding = null;

            for (Field field : observer.getClass().getDeclaredFields()){

                try {
                    if (WeakReference.class.equals(field.getType())){
                        field.setAccessible(true);
                        WeakReference<? extends ViewDataBinding> weakReference = (WeakReference<? extends ViewDataBinding>) field.get(observer);
                        if (weakReference != null){
                            binding = weakReference.get();
                        }
                        break;
                    }

                    if (ViewDataBinding.class.equals(field.getType())){
                        field.setAccessible(true);
                        binding = (ViewDataBinding) field.get(observer);
                        break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (binding != null){
                fullServiceVariable(binding);
            }
        }

        @Override
        public void removeObserver(@NonNull LifecycleObserver observer) {
            int a = 0;
        }

        @NonNull
        @Override
        public State getCurrentState() {
            return State.STARTED;
        }
    };
}
