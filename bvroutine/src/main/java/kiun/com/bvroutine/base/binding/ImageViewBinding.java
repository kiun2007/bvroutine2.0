package kiun.com.bvroutine.base.binding;

import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.io.File;

import kiun.com.bvroutine.utils.GlideUtil;

public class ImageViewBinding {

    private static class ImageViewScaleGesture implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

        private ScaleGestureDetector scaleGestureDetector;
        private float mScaleFactor = 1.0f;
        private ImageView view;
        private int sourceWidth;
        private int sourceHeight;

        public void setScaleGestureDetector(ScaleGestureDetector scaleGestureDetector) {
            this.scaleGestureDetector = scaleGestureDetector;
        }

        public ImageViewScaleGesture(ImageView imageView){
            view = imageView;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (scaleGestureDetector != null){
                scaleGestureDetector.onTouchEvent(event);
                sourceWidth = v.getWidth();
                sourceHeight = v.getHeight();
            }
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor = detector.getScaleFactor();
            if (view != null){
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    @BindingAdapter(value = {"android:backgroundUrl", "android:url", "android:thumbUrl", "android:isAuthentication"}, requireAll = false)
    public static void setBackgroundUrlAndAuthenticationThumb(ImageView imageView, String backgroundUrl, String url,  String thumb, Boolean isAuthentication){

        if (isAuthentication == null){
            isAuthentication = true;
        }

        if (!TextUtils.isEmpty(backgroundUrl)){
            GlideUtil.into(imageView, backgroundUrl, thumb,true, !isAuthentication);
        }

        if (url != null){
            GlideUtil.into(imageView, url, thumb,false, !isAuthentication);
        }
    }

    @BindingAdapter("android:file")
    public static void setFile(ImageView imageView, File file){

        if (file != null && file.exists()){
            imageView.setImageURI(Uri.fromFile(file));
        }else{
            imageView.setImageDrawable(null);
        }
    }

    @BindingAdapter("android:isScaleGesture")
    public static void setIsScaleGesture(ImageView imageView, boolean withScaleGesture){

//        if (withScaleGesture){
//            ImageViewScaleGesture viewScaleGesture = new ImageViewScaleGesture(imageView);
//            viewScaleGesture.setScaleGestureDetector(new ScaleGestureDetector(imageView.getContext(), viewScaleGesture));
//            imageView.setOnTouchListener(viewScaleGesture);
//        }else{
//            imageView.setOnTouchListener(null);
//        }
    }
}
