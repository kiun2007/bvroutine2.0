package kiun.com.bvroutine.views.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.MCString;

public class DateTimePickerDialog extends AlertDialog{

    SetCaller<DateTimePickerDialog> clickListener = null;
    DatePicker datePicker;
    TimePicker timePicker;
    int type;

    /**
     * 最小限制, 选择时当限制选择时间不能小于此时间.
     */
    private Date lessLimit;

    /**
     * 最大限制, 选择时当限制选择时间不能大于此时间
     */
    private Date greaterLimit;

    public DateTimePickerDialog(Context context, int type, Date lessLimit, Date greaterLimit) {
        super(context);
        this.type = type;
        this.lessLimit = lessLimit;
        this.greaterLimit = greaterLimit;
        initView();
    }

    public DateTimePickerDialog(Context context, int type) {
        super(context);
        this.type = type;
        initView();
    }

    public Date getDate(){

        Calendar calendar = Calendar.getInstance();

        if ((type & 0x1) != 0){
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            calendar.set(Calendar.SECOND, 0);
        }

        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        return calendar.getTime();
    }

    private void initView(){

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_date_picker, null);
        setView(view);
        TextView cancelButton = view.findViewById(R.id.cancelButton);
        TextView okButton = view.findViewById(R.id.okButton);

        timePicker = view.findViewById(R.id.timePicker);
        datePicker = view.findViewById(R.id.datePicker);

        if (lessLimit != null){
            datePicker.setMinDate(lessLimit.getTime());
        }

        if (greaterLimit != null){
            datePicker.setMaxDate(greaterLimit.getTime());
        }

        if ((type & 0x10) != 0){
            datePicker.setVisibility(View.VISIBLE);
            if ((type & 0x1) != 0){
                okButton.setText("下一步");
            }
        }else {
            datePicker.setVisibility(View.GONE);
            if ((type & 0x1) != 0){
                timePicker.setVisibility(View.VISIBLE);
            }
        }

        cancelButton.setOnClickListener(v -> {
            DateTimePickerDialog.this.dismiss();
        });

        okButton.setOnClickListener(v -> {

            if (type == 0x11){
                type -= 0x10;
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
                okButton.setText("确定");
                return;
            }

            if (greaterLimit != null && getDate().after(greaterLimit)){
                Toast.makeText(getContext(), MCString.formatDate("请选择 yyyy-MM-dd HH:mm 之前的时间", greaterLimit), Toast.LENGTH_LONG).show();
                return;
            }

            if (lessLimit != null && getDate().before(lessLimit)){
                Toast.makeText(getContext(), MCString.formatDate("请选择 yyyy-MM-dd HH:mm 之后的时间", lessLimit), Toast.LENGTH_LONG).show();
                return;
            }

            if (clickListener != null){
                clickListener.call(DateTimePickerDialog.this);
            }
            DateTimePickerDialog.this.dismiss();
        });
    }

    public void setClickListener(SetCaller<DateTimePickerDialog> clickListener) {
        this.clickListener = clickListener;
    }

    public void setLessLimit(Date lessLimit) {
        this.lessLimit = lessLimit;
    }

    public void setGreaterLimit(Date greaterLimit) {
        this.greaterLimit = greaterLimit;
    }
}
