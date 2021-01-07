package kiun.com.bvroutine.media.av;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Size;
import android.view.TextureView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.media.MediaPlayActivityHandler;
import kiun.com.bvroutine.media.MediaState;
import kiun.com.bvroutine.media.VideoRecorder;
import kiun.com.bvroutine.media.av.camera2.CameraHolder;

public class MediaMp4Recorder extends BaseRecorder implements VideoRecorder{

    private MediaRecorder mediaRecorder;
    private CameraHolder cameraHolder;
    private List<CameraHolder> cameraHolderList;
    private int cameraIndex = 0;
    private boolean isReady = false;
    public static final int MEDIA_QUALITY_HIGH = 30 * 100000;

    private TextureView textureView;

    public MediaMp4Recorder(Context context, int maxDuration){
        super(context, maxDuration);

        cameraHolderList = CameraHolder.allCameraHolder(context);
        if (cameraHolderList != null && cameraHolderList.size() > 0){
            cameraHolder = cameraHolderList.get(cameraIndex);
            cameraHolder.setReadyCall(this::ready);
        }
        setCallBack(this::finishActivity);

        if (context instanceof BVBaseActivity){
            ((BVBaseActivity) context).startPermission(()->{
                isReady = true;
            }, Manifest.permission.RECORD_AUDIO);
        }
    }

    private void ready(){
        changState(MediaState.Ready);
    }

    @Override
    public void startRecord() {

        if (cameraHolder == null){
            return;
        }

        if (!isReady){
            Toast.makeText(context, "未取得录音权限无法录取声音!", Toast.LENGTH_LONG).show();
        }

        Size size = cameraHolder.configVideo();
        createFile("mp4");

        mediaRecorder = new MediaRecorder();

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        if (isReady){
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        }
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        if (isReady) {
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        }
        mediaRecorder.setVideoEncodingBitRate(MEDIA_QUALITY_HIGH);

        mediaRecorder.setVideoSize(size.getWidth(), size.getHeight());
        mediaRecorder.setVideoFrameRate(30);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaRecorder.setOutputFile(mFilePath);
        }else{
            mediaRecorder.setOutputFile(mFilePath.getAbsolutePath());
        }
        mediaRecorder.setOrientationHint(90);

        try {
            mediaRecorder.prepare();
            cameraHolder.record(mediaRecorder.getSurface(), ()->{
                mediaRecorder.start();
                changState(MediaState.Recording);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopRecord() {

        if (mediaRecorder != null){
            mediaRecorder.stop();
        }
        mediaRecorder = null;

        if (cameraHolder != null){
            cameraHolder.close();
        }
        changState(MediaState.Recorded);
        replay();
    }

    private void finishActivity(Intent intent){
        if (context instanceof Activity){
            ((Activity) context).setResult(Activity.RESULT_OK, intent);
            ((Activity) context).finish();
        }
    }

    @Override
    public void cancel(DialogInterface dialog) {
        dialog.cancel();
    }

    @Override
    public void replay() {
        if (getCurrent() == null || !getCurrent().exists()){
            return;
        }

        if (context instanceof BVBaseActivity){
            Intent data = MediaPlayActivityHandler.openActivityIntent(context, getCurrent().getAbsolutePath());
            ((BVBaseActivity) context).startForResult(data, intent->{
                upload(null);
            });
        }
    }

    @Override
    public void setTextureView(TextureView textureView) {

        this.textureView = textureView;
        cameraHolder.init(textureView);
    }

    @Override
    public void flip() {

        changState(MediaState.Nothing);
        cameraIndex++;
        if (cameraIndex >= cameraHolderList.size()){
            cameraIndex = 0;
        }

        cameraHolder.close();
        cameraHolder = cameraHolderList.get(cameraIndex);
        cameraHolder.init(textureView);
        cameraHolder.setReadyCall(this::ready);
    }

    @Override
    public void onResume() {
        if (stateField.get() != MediaState.Recorded){
            cameraHolder.preview();
        }
    }

    @Override
    public void onStop() {
        if (stateField.get() != MediaState.Recorded) {
            cameraHolder.close();
        }
    }
}
