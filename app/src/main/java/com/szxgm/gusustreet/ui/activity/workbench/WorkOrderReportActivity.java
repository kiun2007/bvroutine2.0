package com.szxgm.gusustreet.ui.activity.workbench;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityWorkorderReportBinding;
import com.szxgm.gusustreet.model.base.StaticSpinnerDataWrap;
import com.szxgm.gusustreet.model.dto.mobile.AppealType;
import com.szxgm.gusustreet.model.dto.river.RiverOrder;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.net.services.RiverService;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;

import java.util.List;

import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: WorkOrderReport
 * 作 者: 刘春杰
 * 创建日期: 2020-05-31 13:44
 * 说明: 事件上报
 */
public class WorkOrderReportActivity extends CommitBaseActivity<ActivityWorkorderReportBinding> {

    public static final Class clz = WorkOrderReportActivity.class;

    @IntentValue
    String orderSource;

    @IntentValue
    String riverId;

    @IntentValue
    String riverName;

    @IntentValue
    String patrolId;

    @Override
    public int getViewId() {
        return R.layout.activity_workorder_report;
    }

    @Override
    public void initView() {

        if (orderSource != null){
            binding.getOrder().setOrderSource(orderSource);
        }

        binding.setFixedRiver(riverId != null);
        if (riverId != null){
            binding.getOrder().setRiverId(riverId);
        }

        if (riverName != null){
            binding.getOrder().setRiverName(riverName);
        }
        rbp.addRequest(()->rbp.callServiceData(MobileService.class, s -> s.getAppealType()), this::onAppealType);
    }

    private void onAppealType(List<AppealType> list){
        binding.setAppealTypeSpinnerData(new StaticSpinnerDataWrap<AppealType>(list).listener(this::onAppealTypeSelected));
    }

    private void onAppealTypeSelected(StaticSpinnerDataWrap<AppealType> spinnerDataWrap){
        binding.getOrder().setAppealType(spinnerDataWrap.getSelectedItem().getCode());
    }

    @Override
    public void onComplete(Object data) {

        if (patrolId != null){
            rbp.addRequest(this::commitRiver, (v)->{
                complete();
            });
            return;
        }
        complete();
    }

    private void complete(){
        Toast.makeText(getApplicationContext(), "工单提交成功", Toast.LENGTH_LONG).show();
        finish();
    }

    private String commitRiver() throws Exception{

        RiverOrder riverOrder = new RiverOrder();
        riverOrder.setIncidentRiver(riverId);
        riverOrder.setPatrolId(patrolId);
        riverOrder.setRiverName(riverName);
        riverOrder.setOrderType(binding.getOrder().getRiverOrderType());
        return rbp.callServiceData(RiverService.class, s -> s.commitProblem(riverOrder));
    }

    public static Intent create(Context context, String orderSource){
        return WorkOrderReportActivityHandler.openActivityIntent(context, orderSource, null, null, null);
    }
}