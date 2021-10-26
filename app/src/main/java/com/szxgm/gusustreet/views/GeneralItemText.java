package com.szxgm.gusustreet.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.annotation.Nullable;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import java.util.Map;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.utils.image.DrawableUtil;

/**
 * 文 件 名: GeneralItemText
 * 作 者: 刘春杰
 * 创建日期: 2020/5/25 18:23
 * 说明: 通用选项文本
 */
@SuppressLint("AppCompatCustomView")
public class GeneralItemText extends TextView implements TypedView, View.OnClickListener, ValListener {

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String EXTRA = "extra";

    public static final String RETURN = "return";

    @AttrBind
    private String tableName;

    @AttrBind
    private String fieldName;

    @AttrBind
    private BVBaseActivity activity;

    private InverseBindingListener listener;

    private Drawable rightDrawable;

    String id;

    String title;

    Object extra;

    Class<? extends GeneralListActivity> clz;

    Map<String, String> params;

    public GeneralItemText(Context context) {
        super(context);
    }

    public GeneralItemText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public GeneralItemText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    public void setVal(Object val) {
    }

    @Override
    public void setListener(InverseBindingListener listener) {
        this.listener = listener;
    }

    public Object getVal() {
        if (extra != null){
            return extra;
        }
        return id;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        DrawableUtil.setTextDrawable(this, DrawableUtil.RIGHT, enabled ? rightDrawable : null);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.GeneralItemText;
    }

    @Override
    public void initView() {
        setOnClickListener(this);
        rightDrawable = getCompoundDrawables()[DrawableUtil.RIGHT];
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public void onClick(View v) {

        if (clz != null){

            Intent intent = new Intent(getContext(), clz);

            if (params != null){
                for (String key : params.keySet()){
                    intent.putExtra(key, params.get(key));
                }
            }
            intent.putExtra(RETURN, true);

            activity.startForResult(intent, result->{
                extra = result.getParcelableExtra(EXTRA);
                id = result.getStringExtra(ID);
                title = result.getStringExtra(TITLE);
                setText(title.toString());
                if (listener != null){
                    listener.onChange();
                }
            });
        }
    }

    @BindingAdapter("activityClz")
    public static void setActivityClz(GeneralItemText itemText, Class<? extends GeneralListActivity> clz){
        itemText.clz = clz;
    }

    @BindingAdapter("param")
    public static void setParam(GeneralItemText itemText, Map<String, String> keyValues){
        itemText.params = keyValues;
    }
}