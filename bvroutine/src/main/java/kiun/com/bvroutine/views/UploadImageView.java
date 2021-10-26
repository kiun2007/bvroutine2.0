package kiun.com.bvroutine.views;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.databinding.InverseBindingListener;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.DoublePartCaller;
import kiun.com.bvroutine.base.binding.PartCaller;
import kiun.com.bvroutine.base.binding.RequestBodyCaller;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.interfaces.view.UploadView;
import kiun.com.bvroutine.presenters.OnClickUploadPresenter;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 文 件 名: UploadImageView
 * 作 者: 刘春杰
 * 创建日期: 2020/5/22 18:22
 * 说明: 单张图片上传控件
 */
@SuppressLint("AppCompatCustomView")
public class UploadImageView extends ImageView implements TypedView, UploadView, ValListener<String> {

    @AttrBind
    RequestBVActivity activity;

    private InverseBindingListener listener;

    OnClickUploadPresenter<String> uploadPresenter;

    public UploadImageView(Context context) {
        super(context);
    }

    public UploadImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public UploadImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.UploadImageView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        uploadPresenter = new OnClickUploadPresenter<>(1, getContext());
        uploadPresenter.init(activity, listener);
        this.setOnClickListener(uploadPresenter);
    }

    public void setVal(String url){

    }

    @Override
    public void setListener(InverseBindingListener listener) {
        this.listener = listener;
    }

    public String getVal(){
        return null;
    }

    @Override
    public void setService(PartCaller call) {
        uploadPresenter.setNetCall(call);
    }

    @Override
    public void setThumbService(DoublePartCaller caller) {
        uploadPresenter.setNetCall(caller);
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

}