package com.szxgm.gusustreet.base.presenter;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public interface ECommLogin {

    //应用权限
    String TAPP = "lte.trunk.permission-group.TAPP";

    //广播接受权限.
    String TAPP_RECEIVE_BROADCAST = "lte.trunk.permission.RECEIVE_TAPP_BROADCAST";

    //广播发送权限.
    String TAPP_SEND_BROADCAST = "lte.trunk.permission.SEND_TAPP_BROADCAST";

    /**
     * 设备登陆
     */
    void loginDevice();

    /**
     * 登出.
     */
    void logout();

    /**
     * 框架是否准备好.
     * @return
     */
    boolean isReady();

    /**
     * 用户登陆.
     * @param userName 用户名
     * @param password 密码
     */
    void loginUser(String userName, String password);

    /**
     * 开始服务.
     * @return
     */
    boolean start();

    ECommLogin setCallBack(CallBack callBack);

    void userLoginListener(SetCaller<Boolean> caller);
}
