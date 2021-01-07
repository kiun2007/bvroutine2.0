package com.szxgm.gusustreet;

import android.Manifest;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.szxgm.gusustreet.base.presenter.VersionUpdate;
import com.szxgm.gusustreet.base.presenter.imp.VersionUpdatePresenter;
import com.szxgm.gusustreet.databinding.ActivityRootMainBinding;
import com.szxgm.gusustreet.model.base.DictStatic;
import com.szxgm.gusustreet.model.dto.Dict;
import com.szxgm.gusustreet.net.services.SysService;
import com.szxgm.gusustreet.service.TrailService;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.RetrofitUtil;

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

        rbp.addRequest(this::initData, v->{});

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

    private Void initData() throws Exception {
        DictStatic.ApplyType = RetrofitUtil.callServiceData(SysService.class, s -> s.getDict(Dict.create("shift_pb_sbyy")));
        DictStatic.LeaveType = RetrofitUtil.callServiceData(SysService.class, s -> s.getDict(Dict.create("shift_qj_qjlx")));

        DictStatic.SeverityType = DictStatic.mapFromKey("severity");
        DictStatic.EventType = DictStatic.mapFromKey("event");
        return null;
    }
}
