package kiun.com.bvroutine.views.section;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.base.binding.VerifyFocus;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.data.verify.ProblemExport;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 单项选择控件.
 */
public class RadioGroupRow extends LinearLayout implements TypedBindView, RadioGroup.OnCheckedChangeListener, ProblemExport {

    @AttrBind
    private String problemValue;

    @AttrBind
    private String proofValue;

    @AttrBind
    private String title;

    @ViewBind
    private TextView proofTextView;

    @AttrBind
    private String[] titleArray;

    @AttrBind
    private String[] valueArray;

    @AttrBind(def = 0)
    private int itemLayout = 0;

    @AttrBind
    private boolean enableFlow = true;

    @AttrBind(def = 0)
    private float titleWeight = 0f;

    @AttrBind
    private int layout = R.layout.layout_radio_group_row;

    @ViewBind
    private TextView titleTextView;

    @ViewBind
    private FlowRadioGroup flowRadioGroup;

    private Object value;

    VerifyFocus verifyFocus;

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;

    public RadioGroupRow(Context context) {
        super(context);
    }

    public RadioGroupRow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public RadioGroupRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int layoutId() {
        return layout;
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.RadioGroupRow;
    }

    public List<View> getAllViews(){
        if (flowRadioGroup == null) return null;

        List<View> views = new LinkedList<>();
        for (int i = 0; i < flowRadioGroup.getChildCount(); i++) {
            views.add(flowRadioGroup.getChildAt(i));
        }
        return views;
    }

    @Override
    public void initView() {

        titleTextView.setText(title);
        ViewGroup.LayoutParams layoutParams = titleTextView.getLayoutParams();
        if (layoutParams instanceof LayoutParams && titleWeight > 0){
            ((LayoutParams) layoutParams).weight = titleWeight;
            ((LayoutParams) layoutParams).width = LayoutParams.MATCH_PARENT;
            ((LayoutParams) layoutParams).height = LayoutParams.WRAP_CONTENT;
            titleTextView.setLayoutParams(layoutParams);
        }

        if (TextUtils.isEmpty(title)){
            titleTextView.setVisibility(GONE);
        }

        flowRadioGroup.setOnCheckedChangeListener(this);

        flowRadioGroup.setEnableFlow(enableFlow);
        flowRadioGroup.setTitleArray(titleArray);
        flowRadioGroup.setValueArray(valueArray);
        flowRadioGroup.setItemLayout(itemLayout);
        flowRadioGroup.initView();
    }

    public void setTitleArray(String[] titleArray) {
        flowRadioGroup.setTitleArray(titleArray);
    }

    public void setValueArray(Object[] valueArray) {
        flowRadioGroup.setValueArray(valueArray);
    }

    public void setProblemValue(String problemValue) {
        this.problemValue = problemValue;
    }

    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setOnProofClickListener(OnClickListener onProofClickListener){
        proofTextView.setOnClickListener(onProofClickListener);
    }

    public void setValue(Object value){

        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof RadioButton){
                ((RadioButton) getChildAt(i)).setChecked(false);
            }
        }

        RadioButton radioButton = findViewWithTag(value);
        if (radioButton != null){
            radioButton.setChecked(true);
        }
        this.value = value;
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (isInEditMode()){
            return;
        }

        Object selectedTag = group.findViewById(checkedId).getTag();

        if (mOnCheckedChangeListener != null){
            mOnCheckedChangeListener.onCheckedChanged(group, checkedId);
        }
    }

    @Override
    public void onProblem(Problem problem) {
        verifyFocus = new VerifyFocus(titleTextView, problem, this);
    }

    @Override
    public void clear() {
        if (verifyFocus != null){
            verifyFocus.clear();
        }
    }

    @BindingAdapter({"titles", "values"})
    public static void setRadioGroupRowTitleAndValue(RadioGroupRow view, String[] titles, Object[] values){
        view.setTitleArray(titles);
        view.setValueArray(values);

        if (view.value != null){
            view.setValue(view.value);
        }
    }
}