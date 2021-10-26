package com.szxgm.gusustreet.ui.fragment.attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.FragmentAttendanceStatisticsBinding;
import com.szxgm.gusustreet.model.dto.attendance.AttendanceRoot;
import com.szxgm.gusustreet.model.dto.statistics.ClockStatistics;
import com.szxgm.gusustreet.model.dto.statistics.DateRow;
import com.szxgm.gusustreet.model.dto.attendance.LeaveClock;
import com.szxgm.gusustreet.model.dto.statistics.TimeTotal;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.model.dto.attendance.WorkTime;
import com.szxgm.gusustreet.model.dto.statistics.WorkTimesRow;
import com.szxgm.gusustreet.net.requests.ClockHistoryReq;
import com.szxgm.gusustreet.net.requests.TimeReq;
import com.szxgm.gusustreet.net.requests.WorkTimeReq;
import com.szxgm.gusustreet.net.services.AttendanceService;
import com.szxgm.gusustreet.ui.activity.attendance.CommitAttendanceActivityHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.TreeSmartView;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.presenters.StepTreePresenter;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.MapUtil;
import kiun.com.bvroutine.utils.ObjectUtil;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 考勤统计页面
 */
public class StatisticsFragment extends RequestBVFragment<FragmentAttendanceStatisticsBinding> implements TreeSmartView {

    @Override
    public int getViewId() {
        return R.layout.fragment_attendance_statistics;
    }

    List<WorkTime> workTimes;

    List<LeaveClock> clocks;

    List<TimeTotal> leaveTimeTotal;

    List<TimeTotal> overTimeTotal;

    ListViewPresenter<TreeNode, ?, TreeStepView> listViewPresenter = null;

    List rootList = new LinkedList<>();
    List<List> rowDataList = new ArrayList<>();
    Map<String, WorkTime> workTimeMap;

    int mainColor = 0xFF243148, redColor = 0xFFF84948, grayColor = 0xFFCACACA;
    Date monthStart, monthEnd;
    ClockStatistics clockStatistics;

    ListHandler listHandler = new ListHandler(BR.handler,R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, Object data) {
            if (data instanceof WorkTimesRow){
                WorkTimesRow row = (WorkTimesRow) data;
                if (row.getExtra() instanceof LeaveClock){
                    LeaveClock clock = (LeaveClock) row.getExtra();
                    if (clock.getTimes() == null){
                        Toast.makeText(context, "未查找到排班信息，无法补卡", Toast.LENGTH_LONG).show();
                        return;
                    }
                    CommitAttendanceActivityHandler.openByClock(context, clock);
                }
            }
        }
    };

    @Override
    public void initView() {

        clockStatistics = new ClockStatistics(SharedUtil.getValue(User.TAG, new User())).listener(this::onDateChanged);
        monthStart = DateUtil.getMonthStart(null);
        monthEnd = DateUtil.getMonthEnd(null);
        listViewPresenter = new StepTreePresenter(mViewBinding.mainTree, mViewBinding.mainRefresh, BR.treeHandler);
        listViewPresenter.initRequest(null, this);
        listViewPresenter.start(listHandler, R.layout.item_root, BR.item, getRequestPresenter());
    }

    private void onDateChanged(ClockStatistics clockStatistics){
        monthStart = clockStatistics.getMonthStart();
        monthEnd = clockStatistics.getMonthEnd();
        listViewPresenter.reload();
    }

    //添加头部.
    private void addHead(){
        rootList.add(clockStatistics);
    }

    //平均工时
    @SuppressLint("DefaultLocale")
    private void addWorkTimeByDay(int index){

        float totalTime = 0;
        Map<String, Float> allWorkDate = new HashMap<>();
        for (LeaveClock clock : clocks){
            if (clock.isComplete()){
                float total = DateUtil.getHours(clock.getDkBegin(), clock.getDkEnd());
                totalTime += total;

                String date = MCString.formatDate("yyyy-MM-dd(EEE)", clock.getDkBegin());
                if (allWorkDate.get(date) != null){
                    //同一天叠加后统计.
                    total += allWorkDate.get(date);
                }
                allWorkDate.put(date, total);
            }
        }

        List<DateRow> dateRows = new LinkedList();

        for (String key : allWorkDate.keySet()){
            dateRows.add(new DateRow(key, String.format("%.1f小时", allWorkDate.get(key))));
        }

        rowDataList.add(dateRows);
        if (allWorkDate.size() > 0){
            totalTime /= allWorkDate.size();
        }
        rootList.add(new AttendanceRoot("平均工时", String.format("%.1f小时", totalTime), mainColor,index,allWorkDate.size() > 0));
    }

    private void addArrives(int index){
        Map<String, String> clockMap = new HashMap<>();
        for (LeaveClock clock : clocks){
            clockMap.put(MCString.formatDate("yyyy-MM-dd(EEE)",clock.getDkBegin()),"");
        }

        rowDataList.add(MapUtil.toList(clockMap, (key,val)->new DateRow(key, "1天")));
        rootList.add(new AttendanceRoot("出勤天数", String.format("%d天", clockMap.size()), mainColor, index,clockMap.size() > 0));
    }

    //出勤班次
    private void addArrivesTimes(int index){

        Map<String, Integer> timesCount = new HashMap<>();
        int total = 0;
        for (LeaveClock clock : clocks){
            WorkTime workTime = workTimeMap.get(clock.getDkPbid());

            if (workTime == null){
                continue;
            }

            String key = String.format("%s(%s-%s)", workTime.getPbTimes(), workTime.getPbBegin(), workTime.getPbEnd());
            Integer count = timesCount.get(key) == null?1:timesCount.get(key)+1;
            timesCount.put(key, count);
            total ++;
        }

        rowDataList.add(MapUtil.toList(timesCount,(key, val)-> new WorkTimesRow(key, String.format("%d次", val))));
        rootList.add(new AttendanceRoot("出勤班次", String.format("%d次", total), mainColor, index,total > 0));
    }

    private void addSleepTimes(int index){

        List<String> days = DateUtil.getDays(monthStart, monthEnd, "yyyy-MM-dd(EEE)");
        for (LeaveClock clock : clocks){
            days.remove(MCString.formatDate("yyyy-MM-dd(EEE)", clock.getDkBegin()));
        }

        rowDataList.add(ListUtil.toList(days, item->new DateRow(item, "1天")));
        rootList.add(new AttendanceRoot("休息天数", String.format("%d天", days.size()), mainColor,index,days.size() > 0));
    }

    @SuppressLint("DefaultLocale")
    private void addClock(int index, GetCaller<LeaveClock, Boolean> caller, String title, boolean isLate){

        int count = 0;
        long total = 0;

        Map<String, Long> timesCount = new HashMap<>();
        Map<String, LeaveClock> clockMap = new HashMap<>();

        for (LeaveClock clock : clocks){
            if (caller.call(clock)){
                WorkTime workTime = workTimeMap.get(clock.getDkPbid());
                if (workTime == null || clock.getDkEnd() == null) continue;

                long time = isLate ? clock.getDkBegin().getTime() - workTime.getBegin().getTime() : workTime.getEnd().getTime() - clock.getDkEnd().getTime();
                total += time;
                count ++;

                String key = MCString.formatDate("yyyy-MM-dd(HH:mm)", isLate ? workTime.getBegin() : workTime.getEnd());
                clockMap.put(key, clock);
                clock.setTimes(workTime.getTimes());
                clock.applyType(isLate ? "1" : "2");

                time += timesCount.get(key) == null ? 0 : timesCount.get(key);
                timesCount.put(key, time);
            }
        }
        rowDataList.add(MapUtil.toList(timesCount,(key, val)->
                new WorkTimesRow(key, String.format("%s%d分钟", title, (val/(60*1000))), clockMap.get(key))));
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d次", count));
        if (count != 0){
            sb.append(String.format(",共%d分钟", total/(60*1000)));
        }
        rootList.add(new AttendanceRoot(title, sb.toString(), count == 0 ? grayColor : redColor, index,count != 0));
    }

    private String getTimeName(String timesId){
        WorkTime workTime = workTimeMap.get(timesId);
        if (workTime != null){
            return workTime.getTimes();
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    private void addMissClock(int index){
        List<LeaveClock> missClocks = ListUtil.filter(clocks, item -> !item.isComplete());

        rowDataList.add(ListUtil.toList(missClocks,
                item-> new WorkTimesRow(MCString.formatDate("yyyy-MM-dd(EEE)", item.getDkBegin()),
                        String.format("%s下班缺卡", item.getDkPbname()),
                        item.applyType("2").setTimes(getTimeName(item.getDkPbid())))
        ));
        rootList.add(new AttendanceRoot("缺卡", String.format("%d次", missClocks.size()), missClocks.size() == 0 ? grayColor : redColor, index,missClocks.size() > 0));
    }

    private void addNeglect(int index){

        List<WorkTimesRow> rows = new LinkedList<>();
        for (WorkTime workTime : workTimes){
            List<LeaveClock> withClocks = ListUtil.filter(clocks, item -> item.getDkPbid().equals(workTime.getPbId()));
            if (withClocks.isEmpty()){
                rows.add(new WorkTimesRow(MCString.formatDate("yyyy-MM-dd(EEE)", workTime.getBegin()), String.format("%s旷工", workTime.getPbTimes())));
            }
        }
        rowDataList.add(rows);
        rootList.add(new AttendanceRoot("旷工", String.format("旷工%d次", rows.size()), rows.size() > 0 ? redColor : grayColor, index,rows.size() > 0));
    }

    private void addTimeTotal(String title, int index, List<TimeTotal> totals){

        AtomicInteger count = new AtomicInteger();
        List<DateRow> rows = ListUtil.toList(totals, item-> {
            count.addAndGet(item.getTotal());
            String start = MCString.formatDate("M月d日 HH:mm",item.getBegin());
            String end = MCString.formatDate("M月d日 HH:mm", item.getEnd());
            return new DateRow(String.format("%s至%s", start, end), String.format("%d小时", item.getTotal()));
        });

        rowDataList.add(rows);
        rootList.add(new AttendanceRoot(title, String.format("%d小时", count.get()), count.get() == 0 ? grayColor : mainColor, index,count.get() != 0));
    }

    public void allComplete(boolean complete){
        rootList.clear();
        rowDataList.clear();

        addHead();
        addWorkTimeByDay(0);
        addArrives(1);
        addArrivesTimes(2);
        addSleepTimes(3);
        addClock(4, LeaveClock::isLate, "迟到", true);
        addClock(5, LeaveClock::isEarly, "早退", false);
        addMissClock(6);
        addNeglect(7);
        addTimeTotal("请假", 8, leaveTimeTotal);
        addTimeTotal("加班", 9, overTimeTotal);
    }

    private boolean allGet() throws Exception{

        RequestBindingPresenter p = getRequestPresenter();
        String startDate = MCString.formatDate("yyyy-MM-dd", monthStart);
        String startEnd = MCString.formatDate("yyyy-MM-dd", monthEnd);

        ClockHistoryReq clockReq = new ClockHistoryReq();
        clockReq.setDkBegin(monthStart);
        clockReq.setDkEnd(monthEnd);

        TimeReq req = new TimeReq();
        req.setBegin(startDate);
        req.setEnd(startEnd);

        this.workTimes = p.callServiceData(AttendanceService.class,s->s.pbList(new WorkTimeReq(monthStart, monthEnd)));
        this.clocks = p.callServiceData(AttendanceService.class,s->s.clockHistory(clockReq));
        this.leaveTimeTotal = p.callServiceData(AttendanceService.class,s->s.qjTotalList(req));
        this.overTimeTotal = p.callServiceData(AttendanceService.class,s->s.jbTotalList(req));

        workTimeMap = new HashMap<>();
        for (WorkTime workTime : workTimes){
            workTimeMap.put(workTime.getPbId(), workTime);
        }

        this.clocks = ListUtil.filter(this.clocks, item -> workTimeMap.containsKey(item.getDkPbid()));
        return true;
    }

    @Override
    public TreeViewNode children(TreeNode node) {
        if (node.getExtra() instanceof AttendanceRoot){
            AttendanceRoot root = (AttendanceRoot) node.getExtra();
            if (ListUtil.contains(root.getId(), 2,4,5,6,7)){
                return new TreeViewNode(R.layout.item_row_times, false);
            }
            return new TreeViewNode(R.layout.item_row_date, false);
        }
        return null;
    }

    @Override
    public void rootLayout(TreeNode node) {
        if (node.getExtra() instanceof ClockStatistics){
            node.setViewLayoutId(R.layout.item_row_head);
        }
    }

    @Override
    public void onCheckChanged(StepTreePresenter p) {
    }

    @Override
    public void onLoadMore(StepTreePresenter p) {
    }

    @Override
    public List requestPager(RequestBindingPresenter p, TreeNode treeNode) throws Exception {

        if (treeNode == null){
            allComplete(allGet());
            return rootList;
        }

        if (treeNode.getExtra() instanceof AttendanceRoot){
            AttendanceRoot attendanceRoot = (AttendanceRoot) treeNode.getExtra();
            List list = rowDataList.get(attendanceRoot.getId());
            rowDataList.set(attendanceRoot.getId(), new LinkedList());
            return list;
        }
        return null;
    }

    @Override
    public void loadComplete(ListViewPresenter<TreeNode, TreeNode, ?> p) {
    }
}
