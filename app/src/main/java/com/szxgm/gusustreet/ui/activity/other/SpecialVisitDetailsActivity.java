package com.szxgm.gusustreet.ui.activity.other;


import android.view.View;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivitySpecialPatrolDetailsBinding;
import com.szxgm.gusustreet.databinding.ActivitySpecialVisitDetailsBinding;
import com.szxgm.gusustreet.model.other.GridPatrolItem;
import com.szxgm.gusustreet.model.other.GridVisitItem;

import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 文 件 名: SpecialPatrolDetails
 * 作 者: 刘春杰
 * 创建日期: 2020/11/2 17:56
 * 说明: 专项走访详情
 */
public class SpecialVisitDetailsActivity extends RequestBVActivity<ActivitySpecialVisitDetailsBinding> {

    public static final Class clz = SpecialVisitDetailsActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_special_visit_details;
    }

    @Override
    public void initView() {
         GridVisitItem visitItem = (GridVisitItem) getIntent().getSerializableExtra("item");
         binding.setData(visitItem);
    }

    public void onShowViewPoint(View view){
        SpecialPatrolMapActivityHandler.openActivityNormal(this, binding.getData().getId());
    }
}