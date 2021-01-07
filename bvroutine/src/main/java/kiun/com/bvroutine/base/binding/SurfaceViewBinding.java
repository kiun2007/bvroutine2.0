package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;
import android.view.TextureView;

import kiun.com.bvroutine.media.VideoRecorder;

public class SurfaceViewBinding {

    @BindingAdapter("android:holder")
    public static void setHolder(TextureView view, TextureView.SurfaceTextureListener callback){
        view.setSurfaceTextureListener(callback);
    }

    @BindingAdapter("android:videoRecorder")
    public static void setVideoRecorder(TextureView view, VideoRecorder recorder){
        recorder.setTextureView(view);
    }
}
