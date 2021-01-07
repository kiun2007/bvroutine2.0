package com.szxgm.gusustreet.ui.activity.other;


import android.view.View;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivitySpecialPatrolDetailsBinding;
import com.szxgm.gusustreet.model.other.GridPatrolItem;

import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 文 件 名: SpecialPatrolDetails
 * 作 者: 刘春杰
 * 创建日期: 2020/11/2 17:56
 * 说明: 专项巡查详情
 */
public class SpecialPatrolDetailsActivity extends RequestBVActivity<ActivitySpecialPatrolDetailsBinding> {

    public static final Class clz = SpecialPatrolDetailsActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_special_patrol_details;
    }

    @Override
    public void initView() {
         GridPatrolItem patrolItem = (GridPatrolItem) getIntent().getSerializableExtra("item");
         binding.setData(patrolItem);
    }

    public void onShowViewPoint(View view){
        SpecialPatrolMapActivityHandler.openActivityNormal(this, binding.getData().getId());
    }
}