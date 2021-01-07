package kiun.com.bvroutine.media.av.camera2;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import androidx.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import static android.hardware.camera2.CameraDevice.TEMPLATE_PREVIEW;
import static android.hardware.camera2.CameraDevice.TEMPLATE_RECORD;

public class CameraHolder extends Camera2Base{

    private Size[] surfaceSizes;
    private Size[] mediaRecorderSizes;

    private boolean isFront = false;
    private boolean isBack = false;

    private Surface surface;

    private int videoConfigIndex = 0;

    private int previewConfigIndex = 0;

    public CameraHolder(CameraCharacteristics characteristics, Context context, String cameraId) {

        super(characteristics, context, cameraId);
        Integer cameraOrientation = characteristics.get(CameraCharacteristics.LENS_FACING);
        Integer sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);

        StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        surfaceSizes = map.getOutputSizes(SurfaceTexture.class);
        mediaRecorderSizes = map.getOutputSizes(MediaRecorder.class);

        isFront = cameraOrientation == CameraCharacteristics.LENS_FACING_FRONT;
        isBack = cameraOrientation == CameraCharacteristics.LENS_FACING_BACK;

        startThread();
    }


    private Size configSize(int index, Size[] sizes){

        if (textureView != null){
            Size size = sizes[index];

            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
            surface = new Surface(surfaceTexture);

            return size;
        }
        return null;
    }

    /**
     * 设置预览配置.
     * @param index 配置的索引.
     */
    public Size configPreview(int index){
        if (surfaceSizes.length > index){
            return configSize(index, surfaceSizes);
        }
        return null;
    }

    public Size configVideo(){
        return configVideo(videoConfigIndex);
    }

    public Size configVideo(int index){

        if (mediaRecorderSizes.length > index) {
            return configSize(index, mediaRecorderSizes);
        }
        return null;
    }

    public Size configPreview(){
        return configPreview(previewConfigIndex);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        videoConfigIndex = matchSizeIndex(mediaRecorderSizes, width, height);
        configPreview(previewConfigIndex = matchSizeIndex(surfaceSizes, width, height));
        preview();
    }

    public void preview(){
        if (surface != null){
            startSession(new CameraRequest(TEMPLATE_PREVIEW).addTarget(surface));
        }
    }

    public void record(@NonNull Surface recordSurface, CallBack callBack){

        if (surface != null && recordSurface != null){
            startSession(new CameraRequest(TEMPLATE_RECORD).addTarget(surface).addTarget(recordSurface).setCallBack(callBack));
        }
    }

    public boolean isBack() {
        return isBack;
    }

    public boolean isFront() {
        return isFront;
    }

    public Size matchVideoSize(int width, int height){
        int index = matchSizeIndex(mediaRecorderSizes, width, height);
        return mediaRecorderSizes[index];
    }

    public static List<CameraHolder> allCameraHolder(Context context) {

        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        List<CameraHolder> list = new ArrayList<>();

        if (cameraManager != null){

            try {
                for (String id : cameraManager.getCameraIdList()){
                    CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                    list.add(new CameraHolder(characteristics, context, id));
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
