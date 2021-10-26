package com.szxgm.gusustreet.ui.activity.attendance;

import android.content.Context;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAttendanceReplaceLeaveBinding;
import com.szxgm.gusustreet.model.dto.ReplaceLeave;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;

/**
 * 文 件 名: ReplaceLeave
 * 作 者: 刘春杰
 * 创建日期: 2020/5/19 15:12
 * 说明: 调班页面
 */
public class ReplaceLeaveActivity extends CommitBaseActivity<ActivityAttendanceReplaceLeaveBinding> {

    public static final Class clz = ReplaceLeaveActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_replace_leave;
    }

    @Override
    public void initView() {
        setVariable(BR.replaceLeave, new ReplaceLeave());
    }

    @Override
    public Context getContext() {
        return this;
    }
}