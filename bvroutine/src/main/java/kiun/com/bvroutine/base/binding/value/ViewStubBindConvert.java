package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.view.ViewStub;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import kiun.com.bvroutine.BR;

public class ViewStubBindConvert extends BindConvert<ViewStub, Object, Object> implements ViewStub.OnInflateListener{

    ViewDataBinding viewBinding;

    public ViewStubBindConvert(ViewStub view) {
        super(view);
        view.setOnInflateListener(this);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {
        nowValue = value;
    }

    @Override
    public void onInflate(ViewStub stub, View inflated) {
        viewBinding = DataBindingUtil.getBinding(inflated);
        viewBinding.setVariable(BR.data, nowValue);
    }
}
