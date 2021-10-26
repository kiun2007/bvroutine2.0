package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;

import java.util.Date;

import kiun.com.bvroutine.views.DatePickerLayout;

public class DatePickerLayoutBinding {

    @BindingAdapter("android:lessLimit")
    public static void setLessLimit(DatePickerLayout view, Date lessLimit){
        view.setLessLimit(lessLimit);
    }

    @BindingAdapter("android:greaterLimit")
    public static void setGreaterLimit(DatePickerLayout view, Date greaterLimit){
        view.setGreaterLimit(greaterLimit);
    }
}
