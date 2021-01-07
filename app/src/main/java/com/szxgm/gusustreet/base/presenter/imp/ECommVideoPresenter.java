package com.szxgm.gusustreet.base.presenter.imp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.szxgm.gusustreet.base.SdkBVideoCallback;
import com.szxgm.gusustreet.base.presenter.ECommVideo;
import com.szxgm.gusustreet.model.base.CallInfoFrom;
import java.util.List;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import lte.trunk.tapp.sdk.common.RuntimeEnv;
import lte.trunk.tapp.sdk.media.Format;
import lte.trunk.tapp.sdk.media.IProxyCamera;
import lte.trunk.tapp.sdk.media.IProxyRecorder;
import lte.trunk.tapp.sdk.media.MediaConstants;
import lte.trunk.tapp.sdk.media.MediaServiceProxy;
import lte.trunk.tapp.sdk.media.Size;
import lte.trunk.tapp.sdk.media.SurfaceView;
import lte.trunk.tapp.sdk.poc.PocManager;
import lte.trunk.tapp.sdk.video.CallInfoUi;
import lte.trunk.tapp.sdk.video.IVideoCallback;
import lte.trunk.tapp.sdk.video.MediaManager;
import lte.trunk.tapp.sdk.video.VideoConstants;
import lte.trunk.tapp.sdk.video.VideoManager;
import lte.trunk.tapp.sdk.video.VideoManagerForAudioCall;

public class ECommVideoPresenter implements ECommVideo {

    //全局变量，记录当前通话会话id，-1表示无效id（即当前无会话）
    public static int sCallSid = -1;

    Activity activity;

    private SurfaceView remoteSurface;

    private SurfaceView localSurface;

    private SurfaceHolder mRemoteFaceHolder;

    private SurfaceHolder mLocalFaceHolder;

    //视频管理器
    private VideoManager mVideoManager;
    //音频管理器
    private VideoManagerForAudioCall mAudioManager;
    //相机代理实例
    private IProxyCamera mCameraManager;
    // 录制器代理实例
    private IProxyRecorder mProxyRecorder;

    //获取相机支持的分辨率
    protected List<Format> mSupportFmt;

    //视频业务进行时的实体类
    private CallInfoUi mCallinfo = null;

    //记录是否是VOIP业务
    private boolean isVoipCall = false;

    private boolean isMute = false;

    private PocManager pocManager;

    private CallBack callBack;

    private String remoteNum = null;

    private CallInfoUi callInfoFrom;

    //当前相机运行模式，-1未启动业务，0后置摄像头，1前置摄像头
    private int mICameraType = -1;

    public ECommVideoPresenter(SurfaceView remoteSurface, SurfaceView localSurface, Activity activity) {
        this.remoteSurface = remoteSurface;
        this.localSurface = localSurface;
        this.activity = activity;

        mRemoteFaceHolder = remoteSurface.getHolder();
        mRemoteFaceHolder.addCallback(mRecorderCallback);
        mRemoteFaceHolder.setFormat(PixelFormat.TRANSPARENT);

        mLocalFaceHolder = localSurface.getHolder();
        mLocalFaceHolder.addCallback(mPlayerCallback);
        mLocalFaceHolder.setFormat(PixelFormat.TRANSPARENT);

        initMediaAndVideo();
        pocManager = new PocManager(activity.getApplicationContext());
    }

    /**
     * 主动呼叫对方.
     * @param remoteUser
     */
    public void call(String remoteUser, int type){

        CallInfoUi mCallInfoUi = new CallInfoUi(remoteUser, type, MediaConstants.REAR_CAMERA, MediaConstants.PIX_CIF,
                                                VideoConstants.CALL_MANU_CONFIRM, VideoConstants.AUDIO_NORMAL, VideoConstants.CALL_NORMAL_MODE);

        if (type == VideoConstants.AUDIO_CALL){
            sCallSid = mAudioManager.videoCall(mCallInfoUi);
            return;
        }
        sCallSid = mVideoManager.videoCall(mCallInfoUi);
    }

    public void call(String remoteUser){
        call(remoteUser, VideoConstants.VIDEO_CALL);
    }

    public void call(String remoteUser, boolean monitor){
        call(remoteUser, monitor ? VideoConstants.VIDEO_MONITOR : VideoConstants.VIDEO_CALL);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 尝试处理来电请求,来电广播收到通知也会跳转到本页面来进行处理
     */
    public boolean tryPcsIncomingCall() {
        Intent intent = activity.getIntent();
        if (intent == null) {
            return false;
        }
        Bundle extraData = intent.getExtras();
        if (extraData == null) {
            return false;
        }
        CallInfoFrom callInfoFrom = new CallInfoFrom();

        callInfoFrom.sid = extraData.getInt(VideoConstants.SESSION_ID);
        remoteNum = callInfoFrom.remoteNum = extraData.getString(VideoConstants.CALL_NUM);
        callInfoFrom.videoType = extraData.getInt(VideoConstants.VIDEO_TYPE);
        callInfoFrom.cameraType = extraData.getInt(VideoConstants.CAMERA_TYPE);
        callInfoFrom.mute = extraData.getInt(VideoConstants.MUTE);
        callInfoFrom.confirmMode = extraData.getInt(VideoConstants.CONFIRM_MODE);
        callInfoFrom.setFormatUrl(extraData.getString(VideoConstants.FORMAT));

        if (callInfoFrom.videoType == VideoConstants.AUDIO_CALL) {
            mAudioManager.inComingCall(callInfoFrom);
        } else {
            mVideoManager.inComingCall(callInfoFrom);
        }

        this.callInfoFrom = new CallInfoUi(callInfoFrom);
        return true;
    }

    /**
     * 接听拨打过来的电话.
     */
    public void accept(){
        if (callInfoFrom != null){
            if (callInfoFrom.getVideoType() == VideoConstants.AUDIO_CALL){
                mAudioManager.accept(callInfoFrom);
            }else{
                mVideoManager.accept(callInfoFrom);
            }
            sCallSid = callInfoFrom.sid;
        }
    }

    public boolean mute(){

        isMute = !isMute;
        mVideoManager.setMute(1, isMute);
        return isMute;
    }

    /**
     * 拒绝拨打来的电话.
     */
    public void refused(){
        if (callInfoFrom != null){
            int ret = -1;
            if (callInfoFrom.getVideoType() == VideoConstants.AUDIO_CALL){
                ret = mAudioManager.refused(callInfoFrom.sid);
            }else{
                ret = mVideoManager.refused(callInfoFrom.sid);
            }

            if (ret != VideoConstants.VIDEO_CALL_PROCESS_SUCCESS) {
                mCameraManager.release();
            }
        }
    }

    /**
     * 终止通话.
     */
    public void terminate(){

        if (mVideoManager.checkSidIsValid(sCallSid)) {
            if(isVoipCall){
                releaseAudio(true);
            }else {
                releaseVideo(true);
            }
        }
        isVoipCall = false;
    }

    /**
     * 切换前后置摄像头
     * @return true，切换成功
     */
    public boolean switchCamera(){

        if(mICameraType == -1){
            //未开始视频业务，不能切换
            return false;
        }
        mICameraType ^= 1;
        //停止当前摄像头的采样和预览
        mProxyRecorder.stopSamplingVideo();
        mCameraManager.stopPreview();
        boolean ret= openCameraPreviewAndRecord(mCallinfo, false);
        if(ret){
            mProxyRecorder.restartSamplingVideo();
        }
        return ret;
    }

    public boolean isOnCall(){
        return sCallSid != -1;
    }

    public String getRemoteNum() {
        return remoteNum;
    }

    /**
     * 初始化SDK中的media和video相关对象
     */
    private void initMediaAndVideo() {
        mVideoManager = new VideoManager(RuntimeEnv.appContext, mVideoCallback);
        mAudioManager = new VideoManagerForAudioCall(RuntimeEnv.appContext, mAudioCallback);

        MediaServiceProxy mediaServiceProxy = new MediaServiceProxy(activity.getApplicationContext(), null);
        mCameraManager = mediaServiceProxy.getProxyCamera();
        mProxyRecorder = mediaServiceProxy.getCallEngine().getProxyRecorder();
    }

    /**
     * 打开音频播放器和录制器，打开后会自动上传至云平台
     */
    private void openAudioPlayerAndRecorder(){
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_AUDIO).startPlayer();
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_AUDIO).startRecorder();
    }

    /**
     * 释放Audio资源
     *
     * @param isHangup true 本端主动挂断 ,false 对端挂断
     */
    private void releaseAudio(boolean isHangup, int reason) {

        if (isHangup) {
            mAudioManager.hangup(sCallSid);
        }

        if (reason != -1){
            mAudioManager.releaseSession(sCallSid, reason);
        }

        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_AUDIO).stop();
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_AUDIO).release();
        if (sCallSid != -1) {
            sCallSid = -1;
        }
    }

    /**
     * 释放Audio资源
     *
     * @param isHangup true 本端主动挂断 ,false 对端挂断
     */
    private void releaseAudio(boolean isHangup) {
        releaseAudio(isHangup, -1);
    }

    /**
     * 远端视频流数据播放控件Surface的回调对象
     */
    private SurfaceHolder.Callback mRecorderCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            //如果当前有有效会话，则调用media重新开始渲染视频数据
            if (sCallSid != -1) {
                MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).getProxyMediaEngine().getProxyPlayer().resetDisplaySurface(holder.getSurface());
                MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).getProxyMediaEngine().getProxyPlayer().startRendering();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            //调用media结束视频数据渲染
            MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).getProxyMediaEngine().getProxyPlayer().stopRendering();
        }
    };

    /**
     * 本地视频播放控件Surface的回调对象
     */
    private SurfaceHolder.Callback mPlayerCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (sCallSid != -1) {
                mProxyRecorder.resetPreviewSurface(holder.getSurface());
                mProxyRecorder.recordInFront();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mProxyRecorder.recordOnBackground();
        }
    };

    /**
     * 播放对端视频
     */
    private void playRemoteVideo() {
        //设置远程视频播放的surface，并开始播放远程视频
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).setPlayerSurfaceView(remoteSurface);
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).startPlayer();
    }

    /**
     * 释放Video资源
     *
     * @param isHangup true 本端主动挂断 ,false 对端挂断
     */
    private void releaseVideo(boolean isHangup) {
        releaseVideo(isHangup, -1);
    }

    /**
     * 释放Video资源
     *
     * @param isHangup true 本端主动挂断 ,false 对端挂断
     */
    private void releaseVideo(boolean isHangup, int reason) {

        if (isHangup) {
            mVideoManager.hangup(sCallSid);
        }

        if (reason != -1){
            mVideoManager.releaseSession(sCallSid, reason);
        }
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).stop();
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).release();
        mCameraManager.release();
        if (sCallSid != -1) {
            sCallSid = -1;
        }
        mICameraType = -1;
    }

    /**
     * 打开相机预览，并录制本地视频（录制时会自动上传到云服务器）
     *
     * @param callinfo
     * @return true，业务正常，false业务执行失败
     */
    private boolean openCameraPreviewAndRecord(CallInfoUi callinfo) {
        return openCameraPreviewAndRecord(callinfo, true);
    }

    public Size getPreviewSize(String fmt) {

        if (null == mSupportFmt) {
            return null;
        }

        Size mPreviewSize = null;
        for (Format p : mSupportFmt) {
            if (p.id.equals(fmt)) {
                mPreviewSize = new Size(p.width, p.height);
            }
        }
        return mPreviewSize;
    }

    /**
     * 打开相机预览，并录制本地视频（录制时会自动上传到云服务器）
     *
     * @param callinfo
     * @param isStartRecorder 是否打开录制器
     * @return true，业务正常，false业务执行失败
     */
    private boolean openCameraPreviewAndRecord(CallInfoUi callinfo, boolean isStartRecorder) {
        if(mICameraType == -1){
            //默认使用协商的摄像头
            mICameraType = callinfo.cameraType;
        }
        mCallinfo = callinfo;
        //释放相机并重新打开
        mCameraManager.release();
        int cameraOpenRet = mCameraManager.open(mICameraType);
        if (cameraOpenRet != MediaConstants.CAMERA_OPEN_SUCCESS) {
            mCameraManager.release();
            return false;
        }
        //获取当前设备相机支持的分辨率
        mSupportFmt = mCameraManager.getSupportFormat();
        //设置预览surface
        mCameraManager.setPreviewDisplay(mLocalFaceHolder.getSurface());
        //确认该设备是否支持对端请求的分辨率
        Size previewSize = getPreviewSize(callinfo.format);
        if (null == previewSize) {
            //不支持则退出，或ui给一个提示
            return false;
        }

        //设置相机的预览界面分辨率, previewSize.width, previewSize.height为主叫端要求的且被叫端支持的分辨率
        mCameraManager.setPreviewSize(previewSize.width, previewSize.height);
        //实际调用的android API，相机的安装角度为90度时，预览界面需要跟随旋转
        mCameraManager.setDisplayOrientation(90);
        //设置相机的录制分辨率480x640
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).setRcdVideoSize(previewSize.height, previewSize.width);

        //设置录制方向，MediaConstants.VIDEO_RECORD_DIRECTION_HOMEKEY_DOWN表示home键在录制的视频的正方向下方
        mProxyRecorder.setVideoRecordDirection(MediaConstants.VIDEO_RECORD_DIRECTION_HOMEKEY_DOWN);
        mProxyRecorder.setVideoDefaultSamplingState(true);

        //开始本地预览视频
        mCameraManager.startPreview();
        //设置录制的surface，并开始录制视频
        MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).setRecorderSurfaceView(localSurface);
        if(isStartRecorder){
            MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).startRecorder();
        }
        return true;
    }

    IVideoCallback mAudioCallback = new SdkBVideoCallback("mAudioCallback") {

        /**
         * 本端为主叫时，对端接听后，回调此函数
         * @param callinfo
         */
        @Override
        public void onCallAccepted(CallInfoUi callinfo) {
            openAudioPlayerAndRecorder();
            if (callBack != null){
                callBack.call();
            }
        }

        /**
         * 本端为被叫时，本端接听后，回调此函数
         * @param callinfo
         */
        @Override
        public void onCallConfirmed(CallInfoUi callinfo) {
            openAudioPlayerAndRecorder();
            if (callBack != null){
                callBack.call();
            }
        }

        @Override
        public void onCallClose(int sid, int reason, int closeType) {

            if (reason != VideoConstants.VIDEO_CALL_PROCESS_SUCCESS) {
            }
            releaseAudio(false,reason);
            Toast.makeText(activity, "对方拒绝接听", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    };
    /**
     * 视频业务回调对象
     */
    private IVideoCallback mVideoCallback = new SdkBVideoCallback("mVideoCallback") {

        /**
         * 本端为主叫时，对端接听后，回调此函数
         * @param callinfo
         */
        @Override
        public void onCallAccepted(CallInfoUi callinfo) {

            switch (callinfo.videoType) {
                case VideoConstants.VIDEO_CALL:
                    if (!openCameraPreviewAndRecord(callinfo)) {
                        releaseVideo(true);
                        return;
                    }
                    playRemoteVideo();
                    break;
                case VideoConstants.VIDEO_UPLOAD:
                case VideoConstants.VIDEO_GROUP_CALL:
                    if (!openCameraPreviewAndRecord(callinfo)) {
                        releaseVideo(true);
                        return;
                    }
                    break;
                case VideoConstants.VIDEO_MONITOR:
                    playRemoteVideo();
                    break;
            }
            if (callBack != null){
                callBack.call();
            }
        }

        /**
         * 本端为被叫时，本端接听后，回调此函数
         * @param callinfo
         */
        @Override
        public void onCallConfirmed(CallInfoUi callinfo) {

            String[] fmts = callinfo.format.split("/");
            if (fmts != null && fmts[0] != null) {
                callinfo.format = fmts[0];
            }
            switch (callinfo.videoType) {
                case VideoConstants.VIDEO_CALL:
                    if (!openCameraPreviewAndRecord(callinfo)) {
                        releaseVideo(true);
                        return;
                    }
                    playRemoteVideo();
                    break;

                case VideoConstants.VIDEO_GROUP_CALL:
                    playRemoteVideo();
                    break;
                case VideoConstants.VIDEO_MONITOR:
                    playRemoteVideo();
                    break;
                case VideoConstants.VIDEO_UPLOAD:
                    if (!openCameraPreviewAndRecord(callinfo)) {
                        releaseVideo(true);
                        return;
                    }
                    break;
            }
            if (callBack != null){
                callBack.call();
            }
        }

        /**
         * 释放Video资源
         *
         * @param isHangup true 本端主动挂断 ,false 对端挂断
         */
        private void releaseVideo(boolean isHangup) {
            releaseVideo(isHangup, -1);
        }

        /**
         * 释放Video资源
         *
         * @param isHangup true 本端主动挂断 ,false 对端挂断
         */
        private void releaseVideo(boolean isHangup, int reason) {

            if (isHangup) {
                mVideoManager.hangup(sCallSid);
            }

            if (reason != -1){
                mVideoManager.releaseSession(sCallSid, reason);
            }

            MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).stop();
            MediaManager.getMediaManager(VideoConstants.ENGINE_TYPE_VIDEO).release();
            mCameraManager.release();

            sCallSid = -1;
            mICameraType = -1;
        }

        @Override
        public void onCallClose(int sid, int reason, int closeType) {
            if (reason != VideoConstants.VIDEO_CALL_PROCESS_SUCCESS) {
                Log.i("Call", "------Close with error: " + Integer.toString(reason));
            }
            Toast.makeText(activity, "通话结束", Toast.LENGTH_SHORT).show();
            releaseVideo(false,reason);
            activity.finish();
        }
    };

}
