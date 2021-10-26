package com.szxgm.gusustreet.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.szxgm.gusustreet.R;

import java.util.List;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public class AutoIndexBar extends IndexBar implements TypedView {

    @AttrBind(value = R.styleable.AutoIndexBar_showTextId, resource = true)
    private int textViewId;


    public AutoIndexBar(Context context) {
        this(context, null);
    }

    public AutoIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.AutoIndexBar;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        TextView textView = findParentById(textViewId);
        setmPressedShowTextView(textView);
    }

    public void setData(List list){
        setmSourceDatas(list).invalidate();
    }

    public void initView(){
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }
}
