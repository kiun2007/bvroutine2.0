package com.szxgm.gusustreet.base.presenter;

import kiun.com.bvroutine.interfaces.callers.CallBack;

public interface ECommVideo {

    void call(String remoteUser, int type);

    void call(String remoteUser);

    void call(String remoteUser, boolean monitor);

    void setCallBack(CallBack callBack);

    boolean tryPcsIncomingCall();

    void accept();

    void refused();

    void terminate();

    boolean switchCamera();

    String getRemoteNum();

    boolean isOnCall();

    boolean mute();
}
