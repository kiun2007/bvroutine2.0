package com.szxgm.gusustreet.base;

import android.content.ComponentName;
import android.content.Intent;

public class AppComponent {

    public static final AppComponent PackageA = new AppComponent("com.jsict.cloud.gsmanagement", "com.jsict.cloud.gsmanagement.MainActivity");

    public static final AppComponent PackageB = new AppComponent("com.epoint.cgt.gs", "com.epoint.cgt.actys.CGT_LoginActivity");

    public static final AppComponent PackageC = new AppComponent("com.epoint.cgt.gshw", "com.epoint.cgt.actys.CGT_LoginActivity");

    public static final AppComponent PackageD = new AppComponent("com.techservice.wisdom.inner", "com.techservice.wisdom.app.ui.login.LoginActivity");

    private String packageName;
    private String clzName;

    Intent intent;

    private AppComponent(String packageName, String clzName) {
        this.packageName = packageName;
        this.clzName = clzName;
        intent = new Intent(Intent.ACTION_MAIN);

        ComponentName componentName = new ComponentName(packageName, clzName);
        intent.setComponent(componentName);
    }

    public AppComponent(String packageName){
        this.packageName = packageName;

    }

    public AppComponent put(String key, String value){
        intent.putExtra(key, value);
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClzName() {
        return clzName;
    }

    public Intent getIntent() {
        return intent;
    }
}
