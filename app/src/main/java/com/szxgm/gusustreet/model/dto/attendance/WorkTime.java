package com.szxgm.gusustreet.model.dto.attendance;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.model.base.GeneralItem;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.MCString;

public class WorkTime implements GeneralItem {

    private BigDecimal userId;

    private String pbId;

    private Date pbStartdate;

    private Date pbEnddate;

    private String pbTimes;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date pbTime;

    private String pbBegin;

    private String pbEnd;

    private String pbDept;

    private String bcWeek;

    private Date createTime;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getPbId() {
        return pbId;
    }

    public void setPbId(String pbId) {
        this.pbId = pbId == null ? null : pbId.trim();
    }

    public Date getPbStartdate() {
        return pbStartdate;
    }

    public void setPbStartdate(Date pbStartdate) {
        this.pbStartdate = pbStartdate;
    }

    public Date getPbEnddate() {
        return pbEnddate;
    }

    public void setPbEnddate(Date pbEnddate) {
        this.pbEnddate = pbEnddate;
    }

    public String getPbTimes() {
        return pbTimes;
    }

    public void setPbTimes(String pbTimes) {
        this.pbTimes = pbTimes == null ? null : pbTimes.trim();
    }

    public String getPbBegin() {
        return pbBegin;
    }

    public void setPbBegin(String pbBegin) {
        this.pbBegin = pbBegin == null ? null : pbBegin.trim();
    }

    public String getPbEnd() {
        return pbEnd;
    }

    public void setPbEnd(String pbEnd) {
        this.pbEnd = pbEnd == null ? null : pbEnd.trim();
    }

    public String getPbDept() {
        return pbDept;
    }

    public void setPbDept(String pbDept) {
        this.pbDept = pbDept == null ? null : pbDept.trim();
    }

    public String getBcWeek() {
        return bcWeek;
    }

    public void setBcWeek(String bcWeek) {
        this.bcWeek = bcWeek == null ? null : bcWeek.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getTitle() {
        return pbTimes;
    }

    public String getId() {
        return pbId;
    }

    public Date getPbTime() {
        return pbTime;
    }

    public void setPbTime(Date pbTime) {
        this.pbTime = pbTime;
    }

    public String getWorkTimeFormat(){
        return MCString.formatDate("yyyy-MM-dd", pbTime);
    }

    public Date getBegin(){
        Date date = MCString.dateByFormat(String.format("%s %s", getWorkTimeFormat(), pbBegin),  "yyyy-MM-dd HH:mm:ss");
        return date;
    }

    public Date getEnd(){

        Date date = MCString.dateByFormat(String.format("%s %s", getWorkTimeFormat(), pbEnd),  "yyyy-MM-dd HH:mm:ss");
        if (date.before(getBegin())){
            date = DateUtil.addDay(date, 1);
        }
        return date;
    }

    public long getWorkTime(){
        return getEnd().getTime() - getBegin().getTime();
    }

    public float getHours(Date start, Date over){

        Date begin = getBegin();
        Date end = getEnd();

        if (start.after(end) || over.before(begin)){
            return 0;
        }

        if (begin.before(start)){
            begin = start;
        }

        if (end.after(over)){
            end = over;
        }
        return (float) (end.getTime() - begin.getTime()) / (1000 * 60 * 60);
    }

    /**
     * 检测当前时间是否超过班次下班时间,下班后不允许打上班卡.
     * @return
     */
    public boolean isInWork(){
        Date date = new Date();
        Date end = getEnd();
        return date.before(end);
    }
}
