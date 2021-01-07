package com.szxgm.gusustreet.ui.activity;

import androidx.databinding.ViewDataBinding;
import android.widget.Toast;

import com.szxgm.gusustreet.net.services.AttendanceService;

import java.math.BigDecimal;
import java.util.Date;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.MCString;

public abstract class CommitBaseActivity<T extends ViewDataBinding> extends RequestBVActivity<T> {

    public void onComplete(Object data){
        Toast.makeText(getApplicationContext(), "申请成功", Toast.LENGTH_LONG).show();
        finish();
    }

    protected void timeStatistics(Date begin, Date end, SetCaller<BigDecimal> countCaller){

        String startTime = MCString.formatDate("yyyy-MM-dd HH:mm", begin);
        String endTime = MCString.formatDate("yyyy-MM-dd HH:mm", end);

        getRequestPresenter().addRequest(()->rbp.callServiceData(AttendanceService.class, s -> s.getLeaveTime(startTime, endTime)), countCaller);
    }
}
