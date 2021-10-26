package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;
import android.view.View;

import kiun.com.bvroutine.interfaces.view.UploadView;

public class UploadViewBinding {

    @BindingAdapter("android:service")
    public static void setService(View view, PartCaller call){
        if (view instanceof UploadView){
            ((UploadView) view).setService(call);
        }
    }

    @BindingAdapter("android:serviceThumb")
    public static void setThumbService(View view, DoublePartCaller call){
        if (view instanceof UploadView){
            ((UploadView) view).setThumbService(call);
        }
    }
}
