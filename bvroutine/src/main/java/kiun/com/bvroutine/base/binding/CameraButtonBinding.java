package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.views.CameraButton;

public class CameraButtonBinding {

    @BindingAdapter({"recordStart","recordEnd"})
    public static void setCallBack(CameraButton cameraButton, CallBack startCall, CallBack endCall){
        cameraButton.setCallBack(startCall, endCall);
    }

    @BindingAdapter("duration")
    public static void setDuration(CameraButton cameraButton, int duration){
        cameraButton.setDuration(duration);
    }
}
