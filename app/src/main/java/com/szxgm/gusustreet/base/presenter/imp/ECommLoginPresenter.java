package com.szxgm.gusustreet.base.presenter.imp;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.szxgm.gusustreet.base.presenter.ECommLogin;
import com.tdtech.providers.econtacts.EContactsProviderApplication;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.SharedUtil;
import lte.trunk.tapp.sdk.TAppFramework;
import lte.trunk.tapp.sdk.common.TAppConstants;
import lte.trunk.terminal.contacts.ContactsApplication;
import lte.trunk.tms.api.sm.SMManager;
import lte.trunk.tms.api.sm.SmConstants;

public class ECommLoginPresenter implements ECommLogin {

    private Application application;
    BroadcastReceiver broadcastReceiver;
    private boolean isDeviceLogin;
    private CallBack callBack;
    private SetCaller<Boolean> loginCaller;
    private boolean isUserLogin = false;

    public ECommLoginPresenter(@NonNull Application application) {

        this.application = application;
        isDeviceLogin = SharedUtil.getValue("isDeviceLogin", false);

        TAppFramework.initApplication(application, false);
        EContactsProviderApplication.getInstance().init(application);
        ContactsApplication.getInstance().init(application);

        IntentFilter filter = new IntentFilter(SmConstants.ACTION_TAPP_USER_LOGIN);
        filter.addAction(SmConstants.ACTION_TAPP_USER_LOGOUT);
        filter.addAction(SmConstants.ACTION_TAPP_LOGIN_ACTIVITY_UPDATE);
        filter.addAction(SmConstants.ACTION_DEVICE_LOGIN_RESULT);

        application.registerReceiver(broadcastReceiver = new LoginServiceReceiver(), filter,
                TAppConstants.PERMISSION_SEND_BROADCAST, null);
    }

    public void logout(){
        SMManager.getDefaultManager().logout(false);
    }

    public boolean start(){
        TAppFramework.start();
        return SharedUtil.getValue("isDeviceLogin", false);
    }

    public ECommLogin setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    public void userLoginListener(SetCaller<Boolean> caller) {
        loginCaller = caller;
        caller.call(isUserLogin);
    }

    public boolean isReady(){
        return SMManager.getDefaultManager().isReady();
    }

    public void loginDevice(){

        if (isDeviceLogin){
            return;
        }

        SMManager manager = SMManager.getDefaultManager();
//        manager.setAAServerIP("180.168.76.206:8013");
        manager.setAAServerIP("222.92.48.29:8013");
//        manager.setAAServerIP("2.40.9.243:8013");
        manager.setSystemNotification(0);
        application.sendBroadcast(new Intent("lte.trunk.action.ACTION_TRIGGER_DEV_LOGIN"), SmConstants.PERMISSION_USER_BROADCAST);
    }

    public void loginUser(String userName, String password){
        
        if (isDeviceLogin){
            SMManager manager = SMManager.getDefaultManager();
            manager.PttDevtappLogin(userName, password, true, true);
        }
    }

    private class LoginServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (SmConstants.ACTION_DEVICE_LOGIN_RESULT.equals(action)){

                int result = intent.getIntExtra(SmConstants.AAS_LOGIN_RESULT, -1);
                if (result == SmConstants.LOGIN_SUCCESS){

                    SharedUtil.saveValue("isDeviceLogin", true);
                    if (!isDeviceLogin){
                        if (callBack != null){
                            callBack.call();
                        }
                    }
                    isDeviceLogin = true;
                }else{
                    Toast.makeText(application, "设备登陆失败", Toast.LENGTH_LONG).show();
                }
            }else if (SmConstants.ACTION_TAPP_USER_LOGIN.equals(action)) {
                SMManager manager = SMManager.getDefaultManager();

                boolean isError = SmConstants.UserStatus_OnLine != manager.getLoginStatus();
                if (isError){
                    Toast.makeText(application, "用户登陆失败", Toast.LENGTH_LONG).show();
                }
                isUserLogin = !isError;

                if (loginCaller != null){
                    loginCaller.call(!isError);
                }
            }
        }
    }
}
