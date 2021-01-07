package kiun.com.bvroutine.media.av.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import androidx.annotation.NonNull;
import android.view.Surface;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.interfaces.callers.CallBack;

public class CameraRequest {

    private int templateType;

    private List<Surface> outputTarget = new LinkedList<>();

    private CallBack callBack;

    public CameraRequest(int templateType) {
        this.templateType = templateType;
    }

    public List<Surface> getOutputTarget() {
        return outputTarget;
    }

    public CameraRequest addTarget(Surface surface){
        outputTarget.add(surface);
        return this;
    }

    public CameraRequest setCallBack(CallBack callBack){
        this.callBack = callBack;
        return this;
    }

    public void onSessionCreate(){
        if (this.callBack != null){
            this.callBack.call();
        }
    }

    public CaptureRequest build(@NonNull CameraDevice cameraDevice){

        try {
            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(templateType);
            for (Surface surface : outputTarget){
                builder.addTarget(surface);
            }

//                builder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//                builder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            return builder.build();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}