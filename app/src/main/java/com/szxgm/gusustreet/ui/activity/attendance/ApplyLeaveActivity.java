package com.szxgm.gusustreet.ui.activity.attendance;

import android.content.Context;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityApplyLeaveBinding;
import com.szxgm.gusustreet.model.dto.LeaveApply;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;
import java.util.Date;

/**
 * 请假申请.
 */
public class ApplyLeaveActivity extends CommitBaseActivity<ActivityApplyLeaveBinding> {

    public static Class clz = ApplyLeaveActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_apply_leave;
    }

    @Override
    public void initView() {
        setVariable(BR.leaveApply, new LeaveApply().listener(this::leaveChanged));
    }

    protected void leaveChanged(LeaveApply leaveApply){

        Date begin = leaveApply.getQjBegin();
        Date end = leaveApply.getQjEnd();

        if (begin != null && end != null){
            timeStatistics(begin, end, count->{
                leaveApply.setQjTotaltime(Math.round(count.floatValue()));
                leaveApply.onChanged();
            });
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}