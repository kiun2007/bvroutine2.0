package kiun.com.bvroutine.base.binding;

import android.view.View;
import android.view.ViewStub;

import androidx.databinding.BindingAdapter;

public class ViewStubBinding {

    @BindingAdapter("android:layout")
    public static void setLayout(ViewStub viewStub, int layoutId){

        if (layoutId > 0){
            viewStub.setLayoutResource(layoutId);
            viewStub.setVisibility(View.VISIBLE);
        }
    }
}
