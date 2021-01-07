package kiun.com.bvroutine.base.binding;

import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.base.binding.value.EditTextBindConvert;
import kiun.com.bvroutine.base.binding.value.ImageViewBindConvert;
import kiun.com.bvroutine.base.binding.value.SpinnerBindConvert;
import kiun.com.bvroutine.base.binding.value.TextViewBindConvert;
import kiun.com.bvroutine.utils.ClassUtil;
import kiun.com.bvroutine.utils.ObjectUtil;

public class BindConvertBridge {

    private static Map<Class<? extends View>, Class<? extends BindConvert>> classBindConvertMap = new HashMap<>();

    static {
        //整包加载.
        loadPackage("kiun.com.bvroutine.base.binding.value");
        //默认文本编辑器的值转化器.
        classBindConvertMap.put(AppCompatEditText.class, EditTextBindConvert.class);
        classBindConvertMap.put(AppCompatTextView.class, TextViewBindConvert.class);
        classBindConvertMap.put(AppCompatImageView.class, ImageViewBindConvert.class);
        classBindConvertMap.put(AppCompatSpinner.class, SpinnerBindConvert.class);
    }

    static BindConvert getViewBindConvert(View view, Class<? extends BindConvert> convertClass){

        BindConvert bindConvert = (BindConvert) view.getTag(R.id.tagBinConvert);

        if (bindConvert == null){
            if (convertClass == null){
                convertClass = classBindConvertMap.get(view.getClass());
                if (convertClass == null){
                    return null;
                }
            }
            bindConvert = ObjectUtil.newObject(convertClass, view);
            view.setTag(R.id.tagBinConvert, bindConvert);
        }
        return bindConvert;
    }

    public static void loadPackage(String... packageName){
        List<String> clsName = ClassUtil.getClassName(packageName);
        for (String name : clsName){
            try {
                Class clz = Class.forName(name);
                if (clz.getGenericSuperclass() instanceof ParameterizedType){
                    Class viewClz = ObjectUtil.getGenericClass((ParameterizedType)clz.getGenericSuperclass(), 0);
                    if (BindConvert.class.isAssignableFrom(clz)){
                        classBindConvertMap.put(viewClz, clz);
                    }
                }
            } catch (ClassNotFoundException e) {
            }
        }
    }

    public static void set(Class<? extends View> viewClass, Class<? extends BindConvert> convertClass){
        classBindConvertMap.put(viewClass, convertClass);
    }
}
