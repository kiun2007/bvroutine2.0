package kiun.com.bvroutine.base.binding;


import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.utils.view.ViewGroupUtil;

public class ViewGroupBinding {

    @BindingAdapter("android:enabled")
    public static void setEnabled(ViewGroup viewGroup, boolean enabled){
        ViewGroupUtil.setEnabled(viewGroup, enabled);
    }
}
