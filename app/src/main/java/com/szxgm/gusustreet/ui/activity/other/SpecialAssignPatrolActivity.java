package com.szxgm.gusustreet.ui.activity.other;

import android.widget.Toast;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityGridAssignPatrolBinding;
import com.szxgm.gusustreet.model.other.GridPatrol;

import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 文 件 名: SpecialAssignPatrolActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/10/22 22:55
 * 说明: 指派巡查任务
 */
public class SpecialAssignPatrolActivity extends RequestBVActivity<ActivityGridAssignPatrolBinding> {

    public static final Class clz = SpecialAssignPatrolActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_grid_assign_patrol;
    }

    @Override
    public void initView() {
        binding.setData(new GridPatrol());
    }

    public void onComplete(String data){
        Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
        finish();
    }
}