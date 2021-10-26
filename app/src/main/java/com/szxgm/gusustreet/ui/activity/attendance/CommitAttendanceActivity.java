package com.szxgm.gusustreet.ui.activity.attendance;

import android.content.Context;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAttendanceCommitBinding;
import com.szxgm.gusustreet.model.dto.Apply;
import com.szxgm.gusustreet.model.dto.attendance.LeaveClock;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;

import kiun.com.bvroutine_apt.ActivityOpen;

/**
 * 文 件 名: CommitAttendance
 * 作 者: 刘春杰
 * 创建日期: 2020/5/19 14:28
 */
public class CommitAttendanceActivity extends CommitBaseActivity<ActivityAttendanceCommitBinding> {

    public static Class clz = CommitAttendanceActivity.class;
    LeaveClock clock;

    /**
     * 根据打卡记录跳转
     * @param clock 打卡记录
     */
    @ActivityOpen
    public void openByClock(LeaveClock clock){
        this.clock = clock;
    }

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_commit;
    }

    @Override
    public void initView() {

        Apply apply = new Apply();
        if (clock != null){
            apply.setDkid(clock.getDkId());
            apply.setSbReason(clock.getApplyType());
        }
        setVariable(BR.apply, apply);
        binding.setClock(clock);
    }

    @Override
    public Context getContext() {
        return this;
    }
}