package com.szxgm.gusustreet.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.szxgm.gusustreet.R;

import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.delegate.FlowGroupDelegate;

public class FlowRadioGroup extends RadioGroup implements TypedView {

    @AttrBind
    private String[] titleArray;

    @AttrBind
    private String[] valueArray;

    @AttrBind(def = 0)
    private int itemLayout = 0;

    @AttrBind
    private boolean enableFlow = true;

    private FlowGroupDelegate delegate;

    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
        delegate = new FlowGroupDelegate(this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (enableFlow) {
            delegate.measure(widthMeasureSpec, heightMeasureSpec);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setTitleArray(String[] titleArray) {
        this.titleArray = titleArray;
    }

    public void setValueArray(String[] valueArray) {
        this.valueArray = valueArray;
    }

    public void setEnableFlow(boolean enableFlow) {
        this.enableFlow = enableFlow;
    }

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (enableFlow) {
            delegate.layout(changed, l, t, r, b);
        }else{
            super.onLayout(changed, l, t, r, b);
        }
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.FlowRadioGroup;
    }

    @Override
    public void initView() {

        if (titleArray != null && itemLayout != 0){

            for (int i = 0; i < titleArray.length; i++) {

                RadioButton radioButton = (RadioButton) View.inflate(getContext(), itemLayout, null);
                radioButton.setId(View.generateViewId());

                if (valueArray == null || i >= valueArray.length){
                    radioButton.setTag(String.valueOf(i));
                }else{
                    radioButton.setTag(valueArray[i]);
                }
                radioButton.setText(titleArray[i]);
                radioButton.setEnabled(isEnabled());

                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                if (!enableFlow){
                    layoutParams.width = LayoutParams.MATCH_PARENT;
                    layoutParams.height = LayoutParams.MATCH_PARENT;
                    layoutParams.weight = 1;
                }
                addView(radioButton, layoutParams);
            }
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
