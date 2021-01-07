package kiun.com.bvroutine.media.av.camera2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import android.util.Size;
import android.view.TextureView;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.utils.AgileThread;

public class Camera2Base implements TextureView.SurfaceTextureListener {

    protected CameraCharacteristics characteristics;

    protected Context context;

    protected String cameraId;

    protected CameraDevice cameraDevice;

    protected TextureView textureView;

    protected int width, height;

    /**
     * 拍照会话.
     */
    protected CameraCaptureSession cameraCaptureSession;

    protected Handler backgroundHandler;
    protected HandlerThread backgroundThread;

    private CallBack readyCall;

    public Camera2Base(Context context){
        this.context = context;
    }

    public Camera2Base(CameraCharacteristics characteristics, Context context, String cameraId) {
        this(context);
        this.characteristics = characteristics;
        this.cameraId = cameraId;
    }

    public void init(TextureView textureView){
        this.textureView = textureView;
        if (textureView.isAvailable()){
            onSurfaceTextureAvailable(textureView.getSurfaceTexture(), textureView.getWidth(), textureView.getHeight());
        }else{
            textureView.setSurfaceTextureListener(this);
        }
    }

    public void setReadyCall(CallBack readyCall) {
        this.readyCall = readyCall;
    }

    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            if (readyCall != null){
                readyCall.call();
                readyCall = null;
            }
        }
    };

    @SuppressLint("MissingPermission")
    protected void startSession(@NonNull CameraRequest request){

        new AgileThread(context, (thread)->{

            if (cameraDevice == null){
                //设备未连接时先连接到设备.
                cameraDevice = thread.intoWait(false, ()->{

                    CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    try {
                        cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                            @Override
                            public void onOpened(@NonNull CameraDevice camera) {
                                thread.notify(camera);
                            }
                            @Override
                            public void onDisconnected(@NonNull CameraDevice camera) {
                                cameraDevice = null;
                            }
                            @Override
                            public void onError(@NonNull CameraDevice camera, int error) {
                                camera.close();
                                cameraDevice = null;
                            }
                        }, backgroundHandler);
                    }catch (CameraAccessException e){
                    }
                });
            }

            closeSession();
            try {
                //创建事务对话.
                cameraDevice.createCaptureSession(request.getOutputTarget(), new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {

                        cameraCaptureSession = session;
                        try {
                            session.setRepeatingRequest(request.build(cameraDevice), captureCallback, backgroundHandler);
                            request.onSessionCreate();

                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    }
                }, backgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }).start();
    }

    protected void startThread(){
        backgroundThread = new HandlerThread(getClass().getSimpleName());
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    protected void stopThread() {
        if (backgroundThread != null) {
            backgroundThread.quitSafely();
            try {
                backgroundThread.join();
                backgroundThread = null;
                backgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void closeSession(){
        //如果存在会话, 关闭会话.
        if (cameraCaptureSession != null){
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
    }

    protected float matchValue(Size sizeOne, Size sizeTow){
        int sw = sizeOne.getWidth(), sh = sizeOne.getHeight();
        int dw = sizeTow.getWidth(), dh = sizeTow.getHeight();
        return Math.abs(((float) dw/dh) - ((float) sw/sh)) * 10000000 + Math.abs((dw*dh-sw*sh));
    }

    protected int matchSizeIndex(Size[] choices, int width, int height) {

        Size surfaceSize = new Size(width, height);

        float mValue = matchValue(choices[0], surfaceSize);
        int selectIndex = 0;
        for (int i = 1; i < choices.length; i++) {

            float xValue = matchValue(choices[i], surfaceSize);
            if (mValue < xValue){
                selectIndex = i;
                mValue = xValue;
            }
        }
        return selectIndex;
    }

    public void close(){

        closeSession();
        if (cameraDevice != null){
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
