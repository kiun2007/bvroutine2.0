package com.szxgm.gusustreet.ui.activity.list;

import android.content.Context;
import android.content.Intent;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityTreeGridBinding;
import com.szxgm.gusustreet.model.dto.grid.Grid;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.views.GeneralItemText;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;

import static kiun.com.bvroutine.views.text.GeneralItemText.ID;
import static kiun.com.bvroutine.views.text.GeneralItemText.TITLE;

/**
 * 文 件 名: GridTreeActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/10/22 23:09
 * 说明: 网格树状选择
 */
public class GridTreeActivity extends RequestBVActivity<ActivityTreeGridBinding> {

    public static final Class clz = GridTreeActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_tree_grid;
    }

    @Override
    public void initView() {
        binding.setHandler(listHandler);
    }

    ListHandler<Grid> listHandler = new ListHandler<Grid>(BR.handler, R.layout.list_error_normal){

        @Override
        public void onClick(Context context, int tag, Grid data) {
            if (tag == 3){
                setResult(RESULT_OK, new Intent().putExtra(ID, data.getGridId()).putExtra(TITLE, data.getGridName()));
                finish();
            }
        }
    };
}