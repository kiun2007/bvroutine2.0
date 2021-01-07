package com.szxgm.gusustreet.ui.activity.monitor;


import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityMonitorPlayBinding;
import com.szxgm.gusustreet.model.dto.CameraInfo;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: MonitorPlay
 * 作 者: 刘春杰
 * 创建日期: 2020/9/10 16:04
 * 说明: 播放监控
 */
public class MonitorPlayActivity extends RequestBVActivity<ActivityMonitorPlayBinding> {

    public static final Class clz = MonitorPlayActivity.class;

    @IntentValue
    CameraInfo cameraInfo;

    @Override
    public int getViewId() {
        return R.layout.activity_monitor_play;
    }

    @Override
    public void initView() {
        binding.setCameraInfo(cameraInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mediaPlayer.onDestroy();
    }
}