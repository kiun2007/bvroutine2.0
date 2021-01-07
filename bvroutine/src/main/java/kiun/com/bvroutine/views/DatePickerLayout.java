package kiun.com.bvroutine.views;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.InverseBindingListener;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.binding.ValChangedListener;
import kiun.com.bvroutine.interfaces.callers.ObjectSetCaller;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.dialog.DateTimePickerDialog;

public class DatePickerLayout extends AppCompatTextView implements View.OnClickListener, TypedView, ValChangedListener {

    @AttrBind
    String format;

    @AttrBind(def = 0)
    int returnType = 0;

    @AttrBind(def = 1)
    int selectionType = 1;

    @AttrBind(def = 0x11)
    int type;

    @AttrBind(boolDef = true)
    boolean hideArrow;

    InverseBindingListener listener;
    ObjectSetCaller caller;

    Date currentDate;

    String initString;

    /**
     * 最小限制, 选择时当限制选择时间不能小于此时间.
     */
    private Date lessLimit;

    /**
     * 最大限制, 选择时当限制选择时间不能大于此时间
     */
    private Date greaterLimit;

    private static final int DATE_TYPE = 0;
    private static final int LONG_TYPE = 1;
    private static final int FORMAT_TYPE = 2;
    private static final int STRING_TYPE = 3;

    public DatePickerLayout(Context context) {
        super(context);
    }

    public DatePickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public DatePickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    private void showTime(){
        if (format != null && currentDate != null){
            this.setText(MCString.formatDate(format, currentDate));
        }

        if (hideArrow){
            this.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initString = getText().toString();
    }

    @Override
    public void onClick(View view) {

        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.YEAR, 1949);
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR, 20);
        List<Integer> flags = new LinkedList<>();
        int num = 0x1, value, i = 1;

        while (num <= 16){
            value = selectionType & num;
            if (value != 0){
                flags.add(value);
            }
            num = 1 << i++;
        }

        int[] types = new int[flags.size()];
        for (i = 0; i < types.length; i++) {
            types[i] = flags.get(i);
        }

        DateTimePickerDialog dialog = new DateTimePickerDialog(getContext(), type, lessLimit, greaterLimit);

        dialog.setClickListener(d->{

            currentDate = d.getDate();
            showTime();
            if (caller != null){
                caller.call(getVal());
            }
            if (listener != null){
                listener.onChange();
            }
        });
        dialog.show();
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.DatePickerLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        this.setOnClickListener(this);

        if (TextUtils.isEmpty(getHint())){
            currentDate = new Date();
            showTime();
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public Object getVal() {
        if (this.currentDate != null){
            if (this.returnType == DATE_TYPE){
                return this.currentDate;
            }else if (this.returnType == LONG_TYPE){
                return this.currentDate.getTime();
            }else if (this.returnType == FORMAT_TYPE){
                if (this.format != null){
                    return MCString.formatDate(this.format, this.currentDate);
                }
            }else if (this.returnType == STRING_TYPE){
                return String.valueOf(this.currentDate.getTime());
            }
        }
        return null;
    }

    @Override
    public void setVal(Object time) {
        if (time instanceof Long){
            this.currentDate = new Date((Long) time);
        }else if (time instanceof String && !"".equals(time)){
            long longDate = MCString.toNumber((String) time).longValue();
            if (longDate > 1000000){
                this.currentDate = new Date(longDate);
            }else{
                if (this.format != null){
                    this.currentDate = MCString.dateByFormat((String) time, this.format);
                }
            }
        }else if (time instanceof Date){
            this.currentDate = (Date) time;
        }else{
            this.setText(this.initString);
            return;
        }
        this.showTime();
    }

    @Override
    public void setListener(ObjectSetCaller caller) {
        this.caller = caller;
    }

    @Override
    public void setListener(InverseBindingListener listener) {
        this.listener = listener;
    }

    public Date getLessLimit() {
        return lessLimit;
    }

    public void setLessLimit(Date lessLimit) {
        this.lessLimit = lessLimit;
    }

    public Date getGreaterLimit() {
        return greaterLimit;
    }

    public void setGreaterLimit(Date greaterLimit) {
        this.greaterLimit = greaterLimit;
    }
}
