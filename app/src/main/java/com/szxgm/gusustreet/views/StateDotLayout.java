package com.szxgm.gusustreet.views;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import com.szxgm.gusustreet.R;
import java.util.Date;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 文 件 名: StateDotLayout
 * 作 者: 刘春杰
 * 创建日期: 2020/5/23 15:54
 * 说明: 打卡状态按钮
 */
public class StateDotLayout extends LinearLayout implements TypedBindView {

    @ViewBind(R.id.stateTextView)
    TextView stateTextView;

    @ViewBind(R.id.dateTextView)
    TextView dateTextView;

    @ViewBind(R.id.dotLayout)
    LinearLayout dotLayout;

    @AttrBind
    String stateLabel;

    @AttrBind(resource = true, def = -1)
    int lessBackground;

    @AttrBind(resource = true, def = -1)
    int greaterBackground;

    @AttrBind
    String lessTitle;

    @AttrBind
    String greaterTitle;

    @AttrBind
    String invalidTitle;

    boolean invalid;

    private Date compareTime;

    public StateDotLayout(Context context) {
        super(context);
    }

    public StateDotLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public StateDotLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    private Handler timeHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {

            Date date = new Date();
            if (isEnabled() && compareTime != null){
                if (date.getTime() < compareTime.getTime() && lessBackground > -1){
                    dotLayout.setBackgroundResource(lessBackground);
                    if (lessTitle != null){
                        stateTextView.setText(lessTitle);
                    }
                }

                if (date.getTime() > compareTime.getTime() && greaterBackground > -1){
                    dotLayout.setBackgroundResource(greaterBackground);
                    if (greaterTitle != null){
                        stateTextView.setText(greaterTitle);
                    }
                }
            }
            dateTextView.setText(MCString.formatDate("HH:mm:ss", date));
            timeHandler.sendEmptyMessageDelayed(0,1000);
        }
    };

    @Override
    public int[] getStyleableId() {
        return R.styleable.StateDotLayout;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
        dotLayout.setEnabled(invalid);

        if (!invalid && invalidTitle != null){
            stateTextView.setText(invalidTitle);
        }
    }

    @Override
    public void initView() {
        timeHandler.sendEmptyMessage(0);
        stateTextView.setText(stateLabel);
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public int layoutId() {
        return R.layout.layout_state_dot;
    }

    @BindingAdapter("compareTime")
    public static void setCompareTime(StateDotLayout dotLayout, String compareTime){

        String dateTime = String.format("%s %s", MCString.formatDate("yyyy-MM-dd", new Date()), compareTime);
        dotLayout.compareTime = MCString.dateByFormat(dateTime, "yyyy-MM-dd HH:mm:ss");
        dotLayout.timeHandler.removeMessages(0);
        dotLayout.timeHandler.sendEmptyMessage(0);
    }

    @BindingAdapter("invalid")
    public static void setInvalid(StateDotLayout view, boolean invalid){
        view.setInvalid(invalid);
    }
}