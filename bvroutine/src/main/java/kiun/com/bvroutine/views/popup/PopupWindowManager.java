package kiun.com.bvroutine.views.popup;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.databinding.ViewDataBinding;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public class PopupWindowManager extends PopupWindow implements DialogInterface {

    protected View showInView;
    private ViewDataBinding viewDataBinding;

    protected PopupWindowManager(View showInView, int layoutId, Object data){
        super(showInView.getContext());
        this.showInView = showInView;

        viewDataBinding = ViewBindingUtil.inflate(LayoutInflater.from(this.showInView.getContext()), layoutId, null, false);

        if (data instanceof EventBean){
            EventBean eventBean = (EventBean) data;
            eventBean.bind(BR.data, viewDataBinding);
        }
        viewDataBinding.setVariable(BR.data, data);
        viewDataBinding.setVariable(BR.dialog, this);
        setContentView(viewDataBinding.getRoot());
        setBackgroundDrawable(null);
        setOutsideTouchable(true);
    }

    public PopupWindowManager bind(int br, Object data){
        viewDataBinding.setVariable(br, data);
        return this;
    }

    public PopupWindowManager barVertical(){
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        return this;
    }

    public PopupWindowManager barHorizontal(){
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        return this;
    }

    public PopupWindowManager wrap(){
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        return this;
    }

    public PopupWindowManager fill(){
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        return this;
    }

    public void show(){
        showAsDropDown(showInView);
    }

    public static PopupWindowManager create(View view, int layoutId, Object data){
        return new PopupWindowManager(view, layoutId, data);
    }

    @Override
    public void cancel() {
    }
}
