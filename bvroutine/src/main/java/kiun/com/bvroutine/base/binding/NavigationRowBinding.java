package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.views.NavigationRow;

public class NavigationRowBinding {

    @BindingAdapter("title")
    public static void setTitle(NavigationRow view, String title){
        view.setTitle(title);
    }
}
