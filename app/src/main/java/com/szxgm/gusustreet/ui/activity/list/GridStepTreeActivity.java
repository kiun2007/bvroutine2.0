package com.szxgm.gusustreet.ui.activity.list;

import android.content.Context;
import android.content.Intent;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityStepTreeGridBinding;
import com.szxgm.gusustreet.model.other.GridDept;
import com.szxgm.gusustreet.views.GeneralItemText;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;

/**
 * 文 件 名: GridTreeActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/10/22 23:09
 * 说明: 网格树状选择
 */
public class GridStepTreeActivity extends RequestBVActivity<ActivityStepTreeGridBinding> {

    public static final Class clz = GridStepTreeActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_step_tree_grid;
    }

    @Override
    public void initView() {
        binding.setDeptId("402881f6681232c00168129af2100073");
        binding.setHandler(listHandler);
    }

    ListHandler<GridDept> listHandler = new ListHandler<GridDept>(BR.handler, R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, GridDept data) {
            if (tag == 2){
                setResult(RESULT_OK, new Intent().putExtra(GeneralItemText.ID, data.getWgbm()).putExtra(GeneralItemText.TITLE, data.getDeptName()));
                finish();
            }
        }
    };
}