package com.szxgm.gusustreet.model.dto.statistics;

import com.szxgm.gusustreet.model.dto.user.User;

import java.util.Date;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.DateUtil;
import kiun.com.bvroutine.utils.MCString;

public class ClockStatistics extends EventBean {

    private User user;

    private String month = null;

    private Date monthStart;
    private Date monthEnd;

    public ClockStatistics(User user) {
        this.user = user;
        monthStart = DateUtil.getMonthStart(null);
        monthEnd = DateUtil.getMonthEnd(null);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
        Date date = MCString.dateByFormat(month, "yyyy-MM");
        monthStart = DateUtil.getMonthStart(date);
        monthEnd = DateUtil.getMonthEnd(date);
        onChanged(false);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(Date monthStart) {
        this.monthStart = monthStart;
    }

    public Date getMonthEnd() {
        return monthEnd;
    }

    public void setMonthEnd(Date monthEnd) {
        this.monthEnd = monthEnd;
    }
}
