package kiun.com.bvroutine.views;

import android.content.Context;
import androidx.databinding.InverseBindingListener;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 文 件 名: MonthSwitchView
 * 作 者: 刘春杰
 * 创建日期: 2020/6/5 13:04
 * 说明: 月份选择器
 */
public class MonthSwitchView extends LinearLayout implements TypedBindView, ValListener<String> {

    InverseBindingListener listener;

    @ViewBind
    TextView monthText;

    @ViewBind
    ImageView leftImage;

    @ViewBind
    ImageView rightImage;

    @AttrBind(boolDef = false)
    boolean future;

    @AttrBind
    String format = "yyyy-MM";

    Calendar calendar = Calendar.getInstance();

    public MonthSwitchView(Context context) {
        super(context);
    }

    public MonthSwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public MonthSwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.MonthSwitchView;
    }

    private void showDate(){
        monthText.setText(MCString.formatDate(format, calendar.getTime()));
    }

    public String getVal(){
        return MCString.formatDate(format, calendar.getTime());
    }

    public void setVal(String val){
    }

    @Override
    public void setListener(InverseBindingListener listener) {
        this.listener = listener;
    }

    @Override
    public void initView() {

        leftImage.setOnClickListener(view->{
            calendar.add(Calendar.MONTH, -1);
            showDate();
            ViewUtil.inverse(listener);
        });

        rightImage.setOnClickListener(view->{
            calendar.add(Calendar.MONTH, 1);
            if (!future && calendar.after(Calendar.getInstance())){
                calendar.add(Calendar.MONTH, -1);
                Toast.makeText(getContext(), "不能选择未来的时间", Toast.LENGTH_LONG).show();
            }
            showDate();
            ViewUtil.inverse(listener);
        });
        showDate();
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public int layoutId() {
        return R.layout.layout_month_switch;
    }
}