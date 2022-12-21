package kiun.com.bvroutine.views.section;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.delegate.FlowGroupDelegate;

public class FlowRadioGroup extends RadioGroup implements TypedView, CompoundButton.OnCheckedChangeListener{

    @AttrBind
    private String[] titleArray;

    @AttrBind
    private String[] valueArray;

    private Object[] valueArrayData;

    @AttrBind(def = 0)
    private int itemLayout = 0;

    @AttrBind
    private boolean enableFlow = true;

    private FlowGroupDelegate delegate;

    private OnCheckedChangeListener onCheckedChangeListener;

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
        initView();
    }

    public void setValueArray(Object[] valueArray) {
        this.valueArrayData = valueArray;
        initView();
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

        if (valueArrayData == null){
            valueArrayData = valueArray;
        }

        if (titleArray != null && itemLayout != 0){

            removeAllViews();
            for (int i = 0; i < titleArray.length; i++) {

                View itemView = View.inflate(getContext(), itemLayout, null);
                if (itemView instanceof RadioButton || itemView instanceof CheckBox){

                    TextView radioButton = (TextView) View.inflate(getContext(), itemLayout, null);
                    radioButton.setId(View.generateViewId());

                    if (valueArrayData == null || i >= valueArrayData.length){
                        radioButton.setTag(String.valueOf(i));
                    }else{
                        radioButton.setTag(valueArrayData[i]);
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
                    if (radioButton instanceof CheckBox){
                        ((CheckBox) radioButton).setOnCheckedChangeListener(this);
                    }
                }
            }
        }
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);
        onCheckedChangeListener = listener;
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onCheckedChangeListener != null){
            onCheckedChangeListener.onCheckedChanged(this, buttonView.getId());
        }
    }
}
