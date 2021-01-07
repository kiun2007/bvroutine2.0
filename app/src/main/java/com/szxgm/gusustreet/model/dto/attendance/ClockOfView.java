package com.szxgm.gusustreet.model.dto.attendance;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.base.LastLocation;

import java.util.Date;

public class ClockOfView {

    /**
     * 打卡记录.
     */
    private LeaveClock clock;

    /**
     * 班次信息.
     */
    private WorkTime workTime;

    /**
     * 是否为历史记录
     */
    private boolean isHis = false;

    /**
     * 是否正在执行的打卡记录
     */
    private boolean isCurrent = false;

    public ClockOfView(LeaveClock clock, WorkTime workTime) {
        this.clock = clock;
        this.workTime = workTime;
    }

    public LeaveClock getClock() {
        return clock;
    }

    public void setClock(LeaveClock clock) {
        this.clock = clock;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public LeaveClock arrive(LastLocation lastLocation){

        LeaveClock arriveClock = new LeaveClock();
        arriveClock.arrive(lastLocation);
        arriveClock.setDkPbid(workTime.getPbId());
        return arriveClock;
    }

    /**
     * 显示上班 文字或圆点.
     * @return true 为文字 false 打卡大圆点
     */
    public boolean isArriveShow(){
        return !(isCurrent && clock == null);
    }

    /**
     * 显示下班 文字或圆点.
     * @return true 为文字 false 打卡大圆点
     */
    public boolean isLeaveShow(){
        return !(isCurrent && clock != null);
    }
    
    /**
     * 是否上班缺卡.
     * @return
     */
    public boolean isLackArrive(){
        return clock == null && !workTime.isInWork();
    }

    /**
     * 是否下班缺卡
     * @return
     */
    public boolean isLackLeave(){
        if (!workTime.isInWork()) {
            //没有打卡记录或打卡未完成
            return clock == null || (isHis && !clock.isComplete());
        }
        return false;
    }

    public String[] getLeaveEarlyMessage(){
        if (new Date().before(workTime.getEnd())){
            return new String[]{"当前时间打卡为早退,是否继续打卡", "继续", "取消"};
        }
        return null;
    }

    public boolean isHis() {
        return isHis;
    }

    public void setHis(boolean his) {
        isHis = his;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    /**
     * 是否正在打卡.
     * 如果没有超过下班时间可以打上班卡，
     * 如果超过下班时间没有打卡则无法打上班卡
     * @return
     */
    public boolean isClocking(){

        //打卡完成直接跳过
        if (clock != null && clock.isComplete()){
            return false;
        }

        if (!workTime.isInWork()){
            return clock != null;
        }
        return true;
    }

    private int getDrawableRes(){
        if (isHis){
            return R.drawable.shape_dot_third_bg;
        }

        if (clock == null) {
            return R.drawable.shape_dot_first_bg;
        }else if (clock.getDkEnd() == null){
            return R.drawable.shape_dot_second_bg;
        }
        return R.drawable.shape_dot_third_bg;
    }

    public Drawable getLeftDrawable(Context context){
        return context.getDrawable(getDrawableRes());
    }
}
