package com.szxgm.gusustreet.ui.activity.other;

import android.widget.Toast;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityGridAssignPatrolBinding;
import com.szxgm.gusustreet.databinding.ActivityGridAssignVisitBinding;
import com.szxgm.gusustreet.model.other.GridVisit;

import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 文 件 名: SpecialAssignVisitActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/10/22 22:55
 * 说明: 指派走访任务
 */
public class SpecialAssignVisitActivity extends RequestBVActivity<ActivityGridAssignVisitBinding> {

    public static final Class clz = SpecialAssignVisitActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_grid_assign_visit;
    }

    @Override
    public void initView() {
        binding.setData(new GridVisit());
    }

    public void onComplete(String data){
        Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
        finish();
    }
}