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
 * 说明: 我的申请详情
 */
public class MineApplyDetailActivity extends RequestBVActivity<ActivityAttendanceApplyDetailBinding> {

    public static final Class clz = MineApplyDetailActivity.class;

    @IntentValue
    public MineApply mineApply;

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_apply_detail;
    }

    @Override
    public void initView() {
        binding.setMineApply(false);
        binding.setItem(mineApply);
    }

    private void onComplete(String v){
        Toast.makeText(this, "审核完成", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}