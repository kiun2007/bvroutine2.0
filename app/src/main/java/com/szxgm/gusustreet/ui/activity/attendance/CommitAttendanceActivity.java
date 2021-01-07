package com.szxgm.gusustreet.ui.activity.attendance;

import android.content.Context;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAttendanceCommitBinding;
import com.szxgm.gusustreet.model.dto.Apply;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;

/**
 * 文 件 名: CommitAttendance
 * 作 者: 刘春杰
 * 创建日期: 2020/5/19 14:28
 */
public class CommitAttendanceActivity extends CommitBaseActivity<ActivityAttendanceCommitBinding> {

    public static Class clz = CommitAttendanceActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_commit;
    }

    @Override
    public void initView() {
        setVariable(BR.apply, new Apply());
    }

    @Override
    public Context getContext() {
        return this;
    }
}