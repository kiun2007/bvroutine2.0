package com.szxgm.gusustreet.ui.activity.other;


import android.location.Location;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivitySpecialPatrolMapBinding;
import com.szxgm.gusustreet.net.services.LinkageService;
import com.szxgm.gusustreet.ui.activity.AMap3DActivity;

import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: SpecialPatrolDetails
 * 作 者: 刘春杰
 * 创建日期: 2020/11/2 17:56
 * 说明: 专项巡查详情
 */
public class SpecialPatrolMapActivity extends AMap3DActivity<ActivitySpecialPatrolMapBinding> {

    public static final Class clz = SpecialPatrolMapActivity.class;

    @IntentValue
    String id;

    @Override
    public int getViewId() {
        return R.layout.activity_special_patrol_map;
    }

    @Override
    public void mapInit(Location location) {
    }

    @Override
    public int mapViewId() {
        return R.id.mainMapView;
    }

    @Override
    public void initView() {
        rbp.addRequest(()-> rbp.callServiceList(LinkageService.class, s -> s.viewPoint(id), null), binding::setPoints);
    }
}