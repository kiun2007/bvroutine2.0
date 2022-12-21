package kiun.com.bvroutine.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.TransmitView;
import kiun.com.bvroutine.base.binding.ActionBinding;
import kiun.com.bvroutine.databinding.ActionBarBinding;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.SystemUtil;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.events.ActionListener;
import kiun.com.bvroutine.views.viewmodel.ActionBarItem;

/**
 * DeviceService
 * popchain
 *
 * @author kiun_2007 on 2018/3/29.
 * Copyright (media_menu) 2017-2018 The Popchain Core Developers
 */
public class NavigatorBar extends LinearLayout implements TypedView, TransmitView {

    private TextView titleTextView;
    private TextView leftTextView;
    private TextView rightButton;

    private ActionBarItem barItem;
    private EventBean eventBean;

    ActionBarBinding dataBinding;

    private OnClickListener rightListener;

    @AttrBind
    private String barTitle;

    @AttrBind(boolDef = true)
    private boolean barWithStatus=true;

    @AttrBind
    private Drawable barRightImage;

    @AttrBind
    private Drawable barLeftImage;

    @AttrBind
    private Drawable titleImage;

    @AttrBind
    private String barLeftTitle;

    @AttrBind
    private String barRightTitle;

    @AttrBind
    private boolean barNoback;

    @AttrBind
    private boolean withChild = false;

    @AttrBind
    private int barStyle = 0;

    @AttrBind
    private String rightTag;

    public NavigatorBar(Context context) {
        super(context);
    }

    public NavigatorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setId(R.id.actionBarViewBinding);

        String error = ViewUtil.initTyped(this, attrs);
    }

    public NavigatorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setId(R.id.actionBarViewBinding);
        String error = ViewUtil.initTyped(this, attrs);
    }

    private void loadView() {

        int initCount = getChildCount();
        View firstChild = null;
        if (initCount >= 1){
            firstChild = getChildAt(0);
            removeView(firstChild);
        }

        if (isInEditMode()){
            LayoutInflater.from(getContext()).inflate(R.layout.action_bar, this, true);
        }else{
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.action_bar, this, true);
            if (barItem == null){
                barItem = new ActionBarItem();
            }
            barItem.setBarNoBack(barNoback);
            barItem.bind(BR.barItem, dataBinding);
            dataBinding.setBarItem(barItem);
        }

        leftTextView = findViewById(R.id.leftTextView);
        titleTextView = findViewById(R.id.titleTextView);
        rightButton = findViewById(R.id.rightButton);

        int colorButtonNormal = SystemUtil.getAttr(getContext(), R.attr.colorButtonNormal).resourceId;
        rightButton.setForegroundTintList(ColorStateList.valueOf(getResources().getColor(colorButtonNormal)));
        rightButton.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(colorButtonNormal)));

        if (rightTag != null){
            rightButton.setTag(rightTag);
        }

        if (firstChild != null){

            LinearLayout centerPanel = findViewById(R.id.centerPanel);
            centerPanel.removeAllViews();
            centerPanel.addView(firstChild);
            rightButton.setVisibility(View.GONE);
        }

        if(barItem == null){
            titleTextView.setText(barTitle);
        }else{
            if (TextUtils.isEmpty(barItem.getTitle())){
                barItem.setTitle(barTitle);
            }
            barItem.setWithStatusBar(barWithStatus);
        }

        if (barRightImage != null) {
            rightButton.setVisibility(VISIBLE);
            if (barItem != null){
                barItem.setRightImage(barRightImage);
            }

            barRightImage.setBounds(0, 0, barRightImage.getIntrinsicWidth(), barRightImage.getIntrinsicHeight());
            rightButton.setCompoundDrawables(barRightImage, null, null, null);
        }

        if (barLeftImage != null) {
            leftTextView.setCompoundDrawables(barLeftImage, null, null, null);
        }

        if (titleImage != null) {
            titleTextView.setBackground(titleImage);
        }

        leftTextView.setText(barLeftTitle);
        if (barRightTitle != null) {
            rightButton.setVisibility(VISIBLE);
            rightButton.setText(barRightTitle);
            if (barItem != null){
                barItem.setRightTitle(barRightTitle);
            }
        }

        leftTextView.setVisibility(barNoback ? GONE : VISIBLE);
        switch (barStyle) {
            case 2:
                titleTextView.setTextColor(0xFF000000);
                leftTextView.setTextColor(0xFF000000);
                setLeftImage(R.drawable.svg_left_arrow_black);
                break;
            case 1:
                titleTextView.setTextColor(0xFFFFFFFF);
                leftTextView.setTextColor(0xFFFFFFFF);
                setLeftImage(R.drawable.svg_left_arrow);
                break;
        }

        rightButton.setOnClickListener((view)-> {
            barItem.getHandler().onClick(getContext(), 102, null);
        });
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (withChild){
            loadView();
        }
    }

    private void setLeftImage(int res){
        Drawable drawable = getResources().getDrawable(res);
        drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        leftTextView.setCompoundDrawables(drawable,null,null,null);
    }

    public ActionBarItem getBarItem() {
        if (barItem == null){
            barItem = new ActionBarItem().bind(BR.barItem, dataBinding);
        }
        return barItem;
    }

    public void setBarItem(ActionBarItem barItem){
        this.barItem = barItem;
        if (dataBinding != null){
            dataBinding.setBarItem(barItem);
        }
    }

    public void setBarNoback(boolean barNoback) {
        this.barNoback = barNoback;
    }

    public String getBarTitle() {
        return barTitle;
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.NavigatorBar;
    }

    @Override
    public void initView() {
        if (!withChild){
            loadView();
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @BindingAdapter("barTitle")
    public static void setBarTitle(NavigatorBar navigatorBar, String title){
        navigatorBar.barItem.setTitle(title);
    }

    @BindingAdapter("barRightTitle")
    public static void setBarRightTitle(NavigatorBar navigatorBar, String title){
        navigatorBar.barItem.setRightTitle(title);
    }

    @BindingAdapter("barRightImage")
    public static void setBarRightImage(NavigatorBar navigatorBar, Drawable drawable){
        navigatorBar.rightButton.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public View child() {
        return rightButton;
    }
}
