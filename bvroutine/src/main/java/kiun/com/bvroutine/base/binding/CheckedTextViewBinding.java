package kiun.com.bvroutine.base.binding;

import android.widget.CheckedTextView;

import androidx.databinding.BindingAdapter;

public class CheckedTextViewBinding {

    @BindingAdapter("android:checked")
    public static void setChecked(CheckedTextView textView, boolean isChecked){
        textView.setChecked(isChecked);
    }
}
