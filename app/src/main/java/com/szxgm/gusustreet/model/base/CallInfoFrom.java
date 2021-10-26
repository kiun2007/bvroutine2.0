package com.szxgm.gusustreet.model.base;

import android.os.Bundle;

import kiun.com.bvroutine.data.FieldMapping;
import kiun.com.bvroutine.data.FieldRead;
import kiun.com.bvroutine.data.getter.BundleGetter;
import lte.trunk.tapp.sdk.video.CallInfoUi;
import lte.trunk.tapp.sdk.video.VideoConstants;

public class CallInfoFrom extends CallInfoUi {

    @Override
    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.SESSION_ID, getter = BundleGetter.class))
    public void setSid(int sid) {
        super.setSid(sid);
    }

    @Override
    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.CALL_NUM, getter = BundleGetter.class))
    public void setRemoteNum(String remoteNum) {
        super.setRemoteNum(remoteNum);
    }

    @Override
    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.VIDEO_TYPE, getter = BundleGetter.class))
    public void setVideoType(int videoType) {
        super.setVideoType(videoType);
    }

    @Override
    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.CAMERA_TYPE, getter = BundleGetter.class))
    public void setCameraType(int cameraType) {
        super.setCameraType(cameraType);
    }

    @Override
    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.MUTE, getter = BundleGetter.class))
    public void setMute(int mute) {
        super.setMute(mute);
    }

    @Override
    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.CONFIRM_MODE, getter = BundleGetter.class))
    public void setConfirmMode(int confirmMode) {
        super.setConfirmMode(confirmMode);
    }

    @FieldMapping(@FieldRead(type = Bundle.class, field = VideoConstants.FORMAT, getter = BundleGetter.class))
    public void setFormatUrl(String formatUrl){
        String[] fmts = formatUrl.split("/");
        if (fmts != null && fmts.length > 0 && fmts[0] != null) {
            setFormat(fmts[0]);
        }
    }
}
