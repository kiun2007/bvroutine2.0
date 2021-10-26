package com.szxgm.gusustreet.base;

import android.os.Bundle;

import lte.trunk.tapp.sdk.video.CallInfoUi;
import lte.trunk.tapp.sdk.video.IVideoCallback;

/**
 *  接口IVideoCallback中有一些不需要实现的方法，在BVideoCallback中统一处理一下
 */
public abstract class SdkBVideoCallback implements IVideoCallback {

    private String TAG = "SdkBVideoCallback";
    public SdkBVideoCallback() {
    }

    public SdkBVideoCallback(String TAG) {
        this.TAG = TAG;
    }

    @Override
    public void onCallRinging(CallInfoUi callInfoUi) {

    }

    @Override
    public void onCallRemoteNumChanged(CallInfoUi callInfoUi) {

    }

    @Override
    public void onAudioHijacked() {

    }

    @Override
    public void onAudioGranted() {

    }

    @Override
    public void onCallStateChanged(int i) {

    }

    @Override
    public void onSpeakerStateChanged(boolean b) {

    }

    @Override
    public void onVideoProcessDeath() {

    }

    @Override
    public void onCallInComing(CallInfoUi callInfoUi) {

    }

    @Override
    public void onCameraListUpdated(int i) {

    }

    @Override
    public void onNewCameraListUpdated(int i) {

    }

    @Override
    public void onregisterCameralist(int i) {

    }

    @Override
    public void onCameralistAbilityChange() {

    }

    @Override
    public void onQueryCamerasListResult(int i) {

    }

    @Override
    public void onVideoUploadProgressing(String s, int i) {

    }

    @Override
    public void onVideoUploadResult(String s, boolean b) {

    }

    @Override
    public void onCallLinkStateChange(int i, int i1, int i2) {

    }

    @Override
    public void onCallAmbaMsg(Bundle bundle) {

    }

    @Override
    public void onQueryCloudNameSuccess(String s, String s1, int i) {

    }

    @Override
    public void onRingtokenAllowed() {

    }

    @Override
    public void onCallRefused(int i) {

    }

    /**
     *发起紧急呼叫提升请求后收到200ok，然后开始真正提升为紧急呼叫
     *
     */
    public void requestUpGradeToEmergencyResult(int sid , boolean result){}

    /**
     *被MDC通知当前语音点呼提升为紧急呼叫，然后开始真正提升为紧急呼叫
     */
    public void upGradeToEmergency(int sid){}
    public void onCallEncryptModeChanged(Bundle bundle){}
    public void updateHalfDuplexCallState(Bundle bundle){}
    public void encryptToNormalCall(int sid){}
}
