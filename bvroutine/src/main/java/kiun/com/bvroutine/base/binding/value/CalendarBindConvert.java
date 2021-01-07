package kiun.com.bvroutine.base.binding.value;

import java.util.Date;

import kiun.com.bvroutine.views.calendar.Calendar;
import kiun.com.bvroutine.views.calendar.CalendarView;

/**
 * 日历控件value存取器.
 */
public class CalendarBindConvert extends BindConvert<CalendarView, Object, Object> implements CalendarView.OnCalendarSelectListener {

    private Date currentDate;

    public CalendarBindConvert(CalendarView view) {
        super(view);
        view.setOnCalendarSelectListener(this);
    }

    @Override
    public Object getValue() {
        return convert(currentDate);
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        currentDate = calendar.getTime();
        onChanged();
    }
}
