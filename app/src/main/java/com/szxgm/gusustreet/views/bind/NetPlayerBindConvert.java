package com.szxgm.gusustreet.views.bind;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import androidx.annotation.NonNull;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.dto.CameraInfo;
import com.szxgm.gusustreet.net.services.MonitorService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.BitmapUtil;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;
import veg.mediaplayer.sdk.MediaPlayer;

import static veg.mediaplayer.sdk.MediaPlayer.PlayerNotifyCodes.CP_CONNECT_STARTING;
import static veg.mediaplayer.sdk.MediaPlayer.PlayerNotifyCodes.CP_ERROR_NODATA_TIMEOUT;
import static veg.mediaplayer.sdk.MediaPlayer.PlayerNotifyCodes.VRP_SURFACE_ACQUIRE;

public class NetPlayerBindConvert extends BindConvert<MediaPlayer, CameraInfo, CameraInfo> implements MediaPlayer.MediaPlayerCallback {

    CameraInfo cameraInfo;

    MCDialogManager loadingDialog;

    Handler messageHandler;

    private String videoUrl;

    @Override
    public CameraInfo getValue() {
        return null;
    }

    @Override
    public void setValue(CameraInfo value) {
        cameraInfo = value;
        getVideoStream();
    }

    private void getVideoStream(){
        String code = cameraInfo != null ? cameraInfo.getCode() : null;
        if (!TextUtils.isEmpty(code) && view.getContext() instanceof RequestBVActivity){
            if (loadingDialog == null){
                createDialog();
            }
            RequestBVActivity activity = (RequestBVActivity) view.getContext();
            activity.addRequest(()-> RetrofitUtil.callServiceData(MonitorService.class, s -> s.getStreamUrl(code)), this::urlCallBack);
        }
    }

    private void finish(DialogInterface dialog, int which){
        RequestBVActivity activity = (RequestBVActivity) view.getContext();
        activity.finish();
    }

    private void urlCallBack(Object url){

        if (url != null){
            videoUrl = url.toString();
            replay();
        }else{
            if (loadingDialog != null){
                loadingDialog.dismiss();
                loadingDialog = null;
            }
            AlertUtil.build(view.getContext(), "获取视频流失败").setPositiveButton("退出", this::finish).show();
        }
    }

    private void replay(){
        if (videoUrl != null){
            view.Open(videoUrl, -1, 3000, 3000, 1,
                    1, 1,0,1,1,
                    5000,0,this);
        }
    }

    private void createDialog(){
        loadingDialog = MCDialogManager.create(view.getContext(), R.layout.dialog_video_loading, new KeyValue<>(0, "正在获取视频流..."))
                .transparent()
                .setGravity(Gravity.CENTER)
                .show()
                .setOnCancelListener(dialog -> {
                    finish(dialog, 0);
                })
                .setCaller(v -> {
                    if ("1".equals(v)){
                        getVideoStream();
                        loadingDialog = null;
                    }
                });
    }

    public NetPlayerBindConvert(MediaPlayer view) {
        super(view);
        messageHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void dispatchMessage(@NonNull Message msg) {

                if (msg.what == MediaPlayer.PlayerNotifyCodes.forType(CP_CONNECT_STARTING)){
                    //开始连接.
                    if (loadingDialog == null){
                        createDialog();
                    }
                    loadingDialog.bindValue(BR.data, new KeyValue<>(0, "视频加载中..."));
                }else if (msg.what == MediaPlayer.PlayerNotifyCodes.forType(CP_ERROR_NODATA_TIMEOUT)){
                    //连接超时.
                    if (loadingDialog == null){
                        createDialog();
                    }
                    loadingDialog.bindValue(BR.data, new KeyValue<>(1, "视频连接失败,请重试"));
                    view.Close();
                }else if (msg.what == MediaPlayer.PlayerNotifyCodes.forType(VRP_SURFACE_ACQUIRE)){
                    if (loadingDialog != null){
                        loadingDialog.dismiss();
                        loadingDialog = null;
                    }
                }
            }
        };
    }

    @Override
    public int Status(int i) {

        messageHandler.sendEmptyMessage(i);

        MediaPlayer.PlayerNotifyCodes status = MediaPlayer.PlayerNotifyCodes.forValue(i);
        if (status == MediaPlayer.PlayerNotifyCodes.VRP_SURFACE_ACQUIRE && cameraInfo != null){

            //截取画面.
            MediaPlayer.VideoShot vs = view.getVideoShot(-1, -1);
            int width = vs.getWidth();
            int height = vs.getHeight();
            ByteBuffer byteBuffer = vs.getData();

            File dir = new File(view.getContext().getCacheDir(), "snapshot");
            if (!dir.exists()){
                dir.mkdir();
            }

            File snapshotFile = new File(dir, cameraInfo.getId() + ".jpg");
            take_snapshot(snapshotFile, byteBuffer, width, height);
        }
        return 0;
    }

    private boolean take_snapshot(File image_file, ByteBuffer frame, int width, int height) {

        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        frame.rewind();
        bm.copyPixelsFromBuffer(frame);

        bm = BitmapUtil.postScale(bm, 360);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(image_file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bm != null)
            bm.recycle();
        return true;
    }

    @Override
    public int OnReceiveData(ByteBuffer byteBuffer, int i, long l) {
        return 0;
    }
}
