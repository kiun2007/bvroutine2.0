package com.szxgm.gusustreet.ui.fragment.attendance;

import androidx.recyclerview.widget.SortedList;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.FragmentAttendanceClockBinding;
import com.szxgm.gusustreet.model.dto.attendance.ClockOfView;
import com.szxgm.gusustreet.model.dto.attendance.LeaveClock;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.model.dto.attendance.WorkTime;
import com.szxgm.gusustreet.net.requests.ClockHistoryReq;
import com.szxgm.gusustreet.net.requests.WorkTimeReq;
import com.szxgm.gusustreet.net.services.AttendanceService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 考勤打卡页面
 */
public class ClockInFragment extends RequestBVFragment<FragmentAttendanceClockBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_attendance_clock;
    }

    @Override
    public void initView() {
        mViewBinding.setUser(SharedUtil.getValue(User.TAG, new User()));
        getRequestPresenter().addRequest(this::getClockViews, mViewBinding::setClocks);
        setVariable(BR.req, new WorkTimeReq(true).listener(v -> {
            reqBinding.addRequest(this::getClockViews, mViewBinding::setClocks);
        }));
    }

    public boolean onComplete(String clockId){
        reqBinding.addRequest(this::getClockViews, mViewBinding::setClocks);
        return true;
    }

    private List<ClockOfView> getClockViews() throws Exception{

        boolean isHis = mViewBinding.getReq().getPbStartDate().getDay() != new Date().getDay();
        List<WorkTime> workTimes = reqBinding.callServiceData(AttendanceService.class, s -> s.pbList(mViewBinding.getReq()));


        Collections.sort(workTimes, (o1, o2) -> (int) (o1.getBegin().getTime() - o2.getBegin().getTime()));

        //没有排班给出提示.
        if (ListUtil.isEmpty(workTimes)){
            return null;
        }

        ClockHistoryReq req = new ClockHistoryReq();
        req.setDkBegin(DateUtil.getDayStart(mViewBinding.getReq().getPbStartDate()));
        req.setDkEnd(DateUtil.getDayEnd(mViewBinding.getReq().getPbStartDate()));
        List<LeaveClock> clocks = reqBinding.callServiceData(AttendanceService.class, s -> s.clockHistory(req));

        List<ClockOfView> clockOfViewList = new LinkedList<>();

        for (WorkTime workTime : workTimes){

            List<LeaveClock> clockList = ListUtil.filter(clocks, item -> workTime.getId().equals(item.getDkPbid()));
            ClockOfView clockOfView = new ClockOfView(ListUtil.first(clockList), workTime);

            clockOfView.setHis(isHis);
            clockOfViewList.add(clockOfView);

            if (!isHis && clockOfView.isClocking()){
                clockOfView.setCurrent(true);
                break;
            }
        }
        return clockOfViewList;
    }
}