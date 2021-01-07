package com.szxgm.gusustreet.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.szxgm.gusustreet.MainActivity;
import com.szxgm.gusustreet.MainApplication;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.presenter.ECommLogin;
import com.szxgm.gusustreet.base.presenter.ECommVideo;
import com.szxgm.gusustreet.databinding.ActivityRootGuideBinding;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.AgileThread;

public class GuideActivity extends BVBaseActivity<ActivityRootGuideBinding> {

    @Override
    public int getViewId() {
        return R.layout.activity_root_guide;
    }

    @Override
    public void initView() {
        binding.setAllow(true);
        onAllow(null);
    }

    private void refuse(){
        binding.setAllow(false);
    }

    public void onAllow(View view){

        //必要权限.
        String[] allPermissions = new String[]{
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.WRITE_CALL_LOG,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CALL_PHONE};

        startPermission(allPermissions, this::isOk, this::refuse);
    }

    private void isOk(){
        binding.setAllow(true);
        boolean isDeviceLogin = MainApplication.<MainApplication>getApplication().start();

        if (isDeviceLogin){
            begin();
            return;
        }

        new AgileThread(this, (thread)->{
            while (!MainApplication.<MainApplication>getApplication().isReady()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            MainApplication.<MainApplication>getApplication().getPresenter().setCallBack(()->{
                begin();
            }).loginDevice();
        }).start();
    }

    private void begin(){

        binding.setLoading(false);
        if (!ServiceGenerator.isLogin()){
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
