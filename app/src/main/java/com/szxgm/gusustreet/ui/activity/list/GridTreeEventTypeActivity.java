package com.szxgm.gusustreet.ui.activity.list;

import android.content.Context;
import android.content.Intent;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityStepTreeGridBinding;
import com.szxgm.gusustreet.databinding.ActivityTreeEventTypeBinding;
import com.szxgm.gusustreet.model.dto.mobile.EventType;
import com.szxgm.gusustreet.model.other.GridDept;
import com.szxgm.gusustreet.views.GeneralItemText;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;

/**
 * 文 件 名: GridTreeActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/10/22 23:09
 * 说明: 事件树状选择
 */
public class GridTreeEventTypeActivity extends RequestBVActivity<ActivityTreeEventTypeBinding> {

    public static final Class clz = GridTreeEventTypeActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_tree_event_type;
    }

    @Override
    public void initView() {
        binding.setHandler(listHandler);
    }

    ListHandler<EventType> listHandler = new ListHandler<EventType>(BR.handler, R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, EventType data) {
            if (tag == 3){
                setResult(RESULT_OK,
                        new Intent().putExtra(GeneralItemText.ID, data.getTypeId())
                                .putExtra(GeneralItemText.TITLE, data.getTypeName()));
                finish();
            }
        }
    };
}