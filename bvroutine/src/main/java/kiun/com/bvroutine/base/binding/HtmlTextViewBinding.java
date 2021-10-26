package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.views.text.HtmlTextView;

public class HtmlTextViewBinding {

    @BindingAdapter("android:argument")
    public static void setArgument(HtmlTextView htmlTextView, Object[] value){
        htmlTextView.setArgs(value);
    }
}
