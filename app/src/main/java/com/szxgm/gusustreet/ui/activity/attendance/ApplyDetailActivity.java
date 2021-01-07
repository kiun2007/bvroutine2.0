package com.szxgm.gusustreet.ui.activity.attendance;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAttendanceApplyDetailBinding;
import com.szxgm.gusustreet.model.dto.MineApply;
import com.szxgm.gusustreet.net.requests.ApplyReq;
import com.szxgm.gusustreet.net.services.AttendanceService;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: ApplyDetail
 * 作 者: 刘春杰
 * 创建日期: 2020/6/3 16:38
 * 说明: 别人的申请详情
 */
public class ApplyDetailActivity extends RequestBVActivity<ActivityAttendanceApplyDetailBinding> {

    public static final Class clz = ApplyDetailActivity.class;

    @IntentValue
    public MineApply mineApply;

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_apply_detail;
    }

    @Override
    public void initView() {

        binding.setMineApply(true);
        binding.setItem(mineApply);
        RequestBindingPresenter p = getRequestPresenter();

        p.addRequest(()->p.callServiceData(AttendanceService.class, s->s.qjXqList(mineApply.getId())), data->{
            int a = 0;
        });
    }

    private void onComplete(String v){
        Toast.makeText(this, "审核完成", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    private void requestApply(String status){
        ApplyReq req = new ApplyReq();
        RequestBindingPresenter p = getRequestPresenter();

        if (mineApply.getType().equals("1")){
            req.setQjId(mineApply.getId());
            req.setQjStatus(status);
            p.addRequest(()->p.callServiceData(AttendanceService.class, s -> s.qjshSaveList(req)), this::onComplete);
        }
        if (mineApply.getType().equals("2")){
            req.setJbId(mineApply.getId());
            req.setJbStatus(status);
            p.addRequest(()->p.callServiceData(AttendanceService.class, s -> s.jbshSaveList(req)), this::onComplete);
        }
        if (mineApply.getType().equals("3")){
            req.setTbId(mineApply.getId());
            req.setTbStatus(status);
            p.addRequest(()->p.callServiceData(AttendanceService.class, s -> s.tbshSaveList(req)), this::onComplete);
        }
        if (mineApply.getType().equals("4")){
            req.setSbId(mineApply.getId());
            req.setSbStatus(status);
            p.addRequest(()->p.callServiceData(AttendanceService.class, s -> s.sbshSaveList(req)), this::onComplete);
        }
    }

    public void onPass(View view)  {
        new AlertDialog.Builder(this).setTitle("提示").setMessage("是否通过审批")
                .setPositiveButton("确定", (dialog, which) -> {
                    requestApply("2");
                }).setNegativeButton("取消", null).show();
    }

    public void onRefuse(View view){
        new AlertDialog.Builder(this).setTitle("提示").setMessage("是否驳回审批")
                .setPositiveButton("确定", (dialog, which) -> {
                    requestApply("3");
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}