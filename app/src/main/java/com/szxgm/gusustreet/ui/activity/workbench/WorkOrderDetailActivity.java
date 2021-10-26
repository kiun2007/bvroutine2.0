package com.szxgm.gusustreet.ui.activity.workbench;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityWorkorderDetailBinding;
import com.szxgm.gusustreet.model.dto.MobileOrder;
import com.szxgm.gusustreet.model.dto.mobile.OrderInfo;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.services.FileService;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.net.services.UserService;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.FieldEvent;
import kiun.com.bvroutine.interfaces.binding.Import;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: WorkOrderDetail
 * 作 者: 刘春杰
 * 创建日期: 2020-05-31 12:44
 * 说明: 工单详情
 * @see 
 */
public class WorkOrderDetailActivity extends RequestBVActivity<ActivityWorkorderDetailBinding> {

    public static final Class clz = WorkOrderDetailActivity.class;

    @IntentValue
    OrderInfoReq req;

    /**
     * 处置办法.
     */
    @IntentValue
    String taskDefKey;

    @Override
    public int getViewId() {
        return R.layout.activity_workorder_detail;
    }

    @Override
    public void initView() {
        if (taskDefKey != null){
            getBarItem().setTitle("工单处理");
        }
        setVariable(BR.pagerIndex, new FieldEvent<>(0));
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void onComplete(Object object){
        Toast.makeText(this, "处理完成", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK);
        finish();
    }
}