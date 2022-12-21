package kiun.com.bvroutine.views.delegate;

import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.utils.ObjectUtil;

/**
 * bind转换器解析委托
 */
public class BindConvertDelegate extends ViewDelegate<View> {

    @AttrBind
    private String bindConvertType;

    @AttrBind
    private String initParam;

    public BindConvertDelegate(@NonNull View view, AttributeSet attributeSet) {
        super(view, attributeSet);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.BindConvertDelegate;
    }

    @Override
    public void initView() {

        if (bindConvertType != null){
            try {
                Class clz = Class.forName(bindConvertType);
                if (BindConvert.class.isAssignableFrom(clz)){
                    BindConvert convert = (BindConvert) ObjectUtil.newObject(clz, view);
                    convert.initOfParam(initParam);
                    view.setTag(R.id.tagBinConvert, convert);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
