package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.views.PhotoView;

public class PhotoViewBinding {

    @BindingAdapter("android:zoomEnabled")
    public static void setZoomEnabled(PhotoView view, boolean isEnable){
        if (isEnable){
            view.enable();
        }else{
            view.disenable();
        }
    }
}
