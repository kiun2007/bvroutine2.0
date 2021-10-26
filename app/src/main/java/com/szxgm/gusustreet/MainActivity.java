package com.szxgm.gusustreet;

import android.Manifest;
import android.content.Intent;
import android.os.Build;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.szxgm.gusustreet.base.presenter.VersionUpdate;
import com.szxgm.gusustreet.base.presenter.imp.VersionUpdatePresenter;
import com.szxgm.gusustreet.databinding.ActivityRootMainBinding;
import com.szxgm.gusustreet.service.TrailService;

import kiun.com.bvroutine.base.RequestBVActivity;

public class MainActivity extends RequestBVActivity<ActivityRootMainBinding> {

    @Override
    public int getViewId() {
        return R.layout.activity_root_main;
    }

    @Override
    public void initView() {

        binding.navView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startPermission(()->{
                startService(new Intent(this, TrailService.class));
            }, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }else{
            startService(new Intent(this, TrailService.class));
        }

        VersionUpdate versionUpdate = new VersionUpdatePresenter(this);
        versionUpdate.compound();
    }
}
