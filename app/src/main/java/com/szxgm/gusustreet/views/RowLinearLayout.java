package com.szxgm.gusustreet.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szxgm.gusustreet.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.base.binding.VerifyFocus;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.data.verify.ProblemExport;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.utils.ViewUtil;

public class RowLinearLayout extends LinearLayout implements TypedBindView, ProblemExport {

    @AttrBind
    private String title;

    @AttrBind(boolDef = true)
    private boolean required;

    @AttrBind(color = true, def = -1)
    private int titleColor = -1;

    @AttrBind(boolDef = true)
    private boolean bottomLine = true;

    @AttrBind(def = LayoutParams.WRAP_CONTENT)
    private int titleWidth = LayoutParams.WRAP_CONTENT;

    @AttrBind(def = -1)
    private int titleSize = -1;

    @ViewBind
    private TextView requiredTextView;

    @ViewBind
    TextView titleTextView;

    VerifyFocus verifyFocus;

    public RowLinearLayout(Context context) {
        super(context);
    }

    public RowLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        String log = ViewUtil.initTyped(this, attrs);
    }

    public RowLinearLayout(Context context,@Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String log = ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.RowLinearLayout;
    }

    @Override
    public void initView() {

        requiredTextView.setVisibility(required ? VISIBLE : GONE);
        if (title != null){
            titleTextView.setText(title);
        }

        if (titleColor != -1){
            titleTextView.setTextColor(titleColor);
        }

        if (titleSize != -1){
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        }

        if(bottomLine){
            setBackground(getContext().getDrawable(R.drawable.shape_bottom_line_padding4_bg));
        }

        LinearLayout titleHeadLayout = findViewById(R.id.titleHead);
        if (titleHeadLayout != null){
            LayoutParams layoutParams = (LayoutParams) titleHeadLayout.getLayoutParams();
            if (titleWidth == -1){
                layoutParams.weight = 1;
                layoutParams.width = LayoutParams.MATCH_PARENT;
            }else if (titleWidth == -2){
                layoutParams.width = LayoutParams.WRAP_CONTENT;
            }else {
                layoutParams.width = titleWidth;
            }
            layoutParams.height = LayoutParams.WRAP_CONTENT;
            titleHeadLayout.setLayoutParams(layoutParams);
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    public void onProblem(Problem problem) {
        verifyFocus = new VerifyFocus(titleTextView, problem, this);
    }

    public void clear() {
        verifyFocus.clear();
    }

    @Override
    public int layoutId() {
        return R.layout.layout_row_head;
    }
}
