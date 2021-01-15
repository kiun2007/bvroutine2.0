package com.szxgm.gusustreet.ui.activity.attendance;

import android.content.Context;
import android.widget.Toast;

import com.amap.api.maps2d.AMapUtils;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAttendanceOvertimeBinding;
import com.szxgm.gusustreet.model.dto.Overtime;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;

import java.util.Date;

/**
 * 文 件 名: Overtime
 * 作 者: 刘春杰
 * 创建日期: 2020/5/19 13:13
 */
public class OvertimeActivity extends CommitBaseActivity<ActivityAttendanceOvertimeBinding> {

    public static final Class clz = OvertimeActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_attendance_overtime;
    }

    @Override
    public void initView() {
        setVariable(BR.overtime, new Overtime().listener(this::overTimeChanged));
    }

    protected void overTimeChanged(Overtime overtime){

        Date begin = overtime.getJbBegin();
        Date end = overtime.getJbEnd();

        if (begin != null && end != null){
            timeStatistics(begin, end, count->{
                float totalTime = (float)(end.getTime() - begin.getTime())/(1000*60*60);
                int totalValue = (int) Math.round(totalTime - count.floatValue());
                if (totalValue == 0){
                    Toast.makeText(getContext(), "时间太短,请重新选择时间", Toast.LENGTH_LONG).show();
                    overtime.clear();
                    return;
                }
                overtime.setJbTotal(String.valueOf(totalValue));
                overtime.onChanged();
            });
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}