package kiun.com.bvroutine.base.binding.variable;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import java.lang.reflect.Method;
import kiun.com.bvroutine.interfaces.JSON;
import kiun.com.bvroutine.utils.SharedUtil;

public class ShareVariableSet extends VariableBinding<JSON> {

    public ShareVariableSet(Context context, ViewDataBinding viewDataBinding, Class<JSON> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        try {
            JSON defValue = clz.newInstance();
            setVariable(SharedUtil.getValue(clz.getName(), defValue));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {

    }
}
