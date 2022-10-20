package com.szxgm.gusustreet;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import com.szxgm.gusustreet.base.presenter.ECommLogin;
import com.szxgm.gusustreet.base.presenter.imp.ECommLoginPresenter;;
import com.szxgm.gusustreet.net.SimulationGPS;
import com.szxgm.gusustreet.net.SimulationInterceptor;
import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.base.binding.BindConvertBridge;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.LocationUtils;

public class MainApplication extends ActivityApplication {

    ECommLogin eCommLogin;

    @Override
    public void onCreate() {
        super.onCreate();

        if (SimulationGPS.isFeatures()){
            LocationUtils.setLocationGetCall(SimulationGPS::getLocation);
        }

        ServiceGenerator.putBuild(new SimulationInterceptor(), BuildConfig.Prefix, "Fake");
        ServiceGenerator.putBuild(BuildConfig.Prefix);
        ServiceGenerator.putBuild(null, BuildConfig.Other,"other" , OtherAppLogin.class);
        ServiceGenerator.putBuild("http://2.45.0.38:8080/gsld/", "linkage");
        ServiceGenerator.putBuild("http://192.168.1.55:9999/auth/", "test");

        BindConvertBridge.loadPackage("com.szxgm.gusustreet.views.bind");
        eCommLogin = new ECommLoginPresenter(this);
        ListHandler.configNormal().empty("未找到相应信息").error("发生网络故障").loading("正在为您查找数据");

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
    }

    public boolean start(){
        return eCommLogin.start();
    }

    public void loginDevice(){
        eCommLogin.loginDevice();
    }

    public boolean isReady(){
        return eCommLogin.isReady();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public ECommLogin getPresenter() {
        return eCommLogin;
    }
}
