package com.szxgm.gusustreet.ui.activity.other;

import android.content.Context;
import android.content.Intent;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivitySpecialTaskBinding;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.FieldEvent;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.views.popup.PopupWindowManager;

/**
 * 文 件 名: SpecialTask
 * 作 者: 刘春杰
 * 创建日期: 2020/10/20 22:46
 * 说明: 巡查走访专项任务
 */
public class SpecialTaskActivity extends RequestBVActivity<ActivitySpecialTaskBinding> {

    public static final Class clz = SpecialTaskActivity.class;

    PopupWindowManager popupWindowManager;

    @Override
    public int getViewId() {
        return R.layout.activity_special_task;
    }

    @Override
    public void initView() {
        setVariable(BR.pagerIndex, new FieldEvent<>(0));

        getBarItem().getHandler().setRightCallBack((view)->{
            if (popupWindowManager == null){
                popupWindowManager = PopupWindowManager.create(view, R.layout.popup_menu_special_types, eventHandler);
            }
            popupWindowManager.show();
        });
    }

    private void onAddComplete(Intent intent){

    }

    private BaseHandler eventHandler = new BaseHandler(){
        @Override
        public void onClick(Context context, int tag, Object data) {
            popupWindowManager.dismiss();

            Intent intent = null;
            if (tag == 0){
                intent = new Intent(context, SpecialAssignPatrolActivity.clz);
            }else if (tag == 1){
                intent = new Intent(context, SpecialAssignVisitActivity.clz);
            }

            if (intent != null){
                startForResult(intent, SpecialTaskActivity.this::onAddComplete);
            }
        }
    };
}