package kiun.com.bvroutine.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.binding.variable.VariableBinding;
import kiun.com.bvroutine.interfaces.binding.Import;
import kiun.com.bvroutine.interfaces.binding.ImportType;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.utils.auto.AutoVariableLifecycleOwner;

/**
 * 界面绑定工具.
 */
public class ViewBindingUtil {

    /**
     * 自动导入配置数据.
     * @param viewHandler
     * @param binding
     */
    private static void readSetting(Object viewHandler, ViewDataBinding binding){

        List<Field> allField = new LinkedList<>();

        for (Class clz = viewHandler.getClass(); !clz.equals(Object.class); clz = clz.getSuperclass()){
            allField.addAll(Arrays.asList(clz.getDeclaredFields()));
        }

        for (Field field : allField){

            Import importClz = field.getAnnotation(Import.class);

            if(importClz != null){

                field.setAccessible(true);
                try {
                    Integer br = (Integer) field.get(viewHandler);
                    if(br != null){

                        Object value = null;
                        if (importClz.type() == ImportType.Service) {
//                            value = ServiceGenerator.createService(importClz.value());
                        }else if (importClz.type() == ImportType.Save) {
                            value = SharedUtil.getValue(importClz.extra(), importClz.value().newInstance());
                        }

                        if (value != null){
                            binding.setVariable(br, value);
                        }
                    }
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 自动填充服务到ViewDataBinding
     * @param binding 需要填充的 ViewDataBinding.
     */
    private static void fullServiceVariable(ViewDataBinding binding, Context context){
        binding.setLifecycleOwner(new AutoVariableLifecycleOwner(context));
    }

    public static<T extends ViewDataBinding> T setContentView(Activity activity, int layoutId){

         T viewBinding = DataBindingUtil.setContentView(activity, layoutId);
         readSetting(activity, viewBinding);
         fullServiceVariable(viewBinding, activity);
         return viewBinding;
    }

    public static <T extends ViewDataBinding> T inflate(@NonNull LayoutInflater inflater, int layoutId, @Nullable ViewGroup parent, boolean attachToParent){

        T viewBinding = DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent);
        fullServiceVariable(viewBinding, inflater.getContext());
        return viewBinding;
    }
}
