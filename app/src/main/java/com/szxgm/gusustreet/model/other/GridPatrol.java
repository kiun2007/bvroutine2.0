package com.szxgm.gusustreet.model.other;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.GeneralItemTextListener;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import kiun.com.bvroutine.views.text.GeneralItemText;

/**
 * 网格巡逻任务.
 */
public class GridPatrol extends EventBean implements GeneralItemTextListener {

    //网格ID.
    @Verifys(@Verify(value = NotNull.class, desc = "请选择网格"))
    private String wgbm;

    //巡查名称.
    @Verifys(@Verify(value = NotNull.class, desc = "请输入巡查名称"))
    private String patrolName;

    //执行对象.
    private String personName;

    //巡查人员用户.
    @Verifys(@Verify(value = NotNull.class, desc = "请选择巡查人员"))
    private String patrolPerson;

    //巡查时段 1 上午 2下午 3全时段.
    private String patrolPeriod;

    //巡查日期.
    @Verifys(@Verify(value = NotNull.class, desc = "请选择巡日期"))
    @JSONField(format = "yyyy-MM-dd")
    private Date patrolDate;

    //任务描述
    @Verifys(@Verify(value = NotNull.class, desc = "请输入任务描述"))
    private String patrolContent;
    
    public String getWgbm() {
        return wgbm;
    }

    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
        onChanged();
    }

    public String getPatrolName() {
        return patrolName;
    }

    public void setPatrolName(String patrolName) {
        this.patrolName = patrolName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPatrolPerson() {
        return patrolPerson;
    }

    public void setPatrolPerson(String patrolPerson) {
        this.patrolPerson = patrolPerson;
    }

    public String getPatrolPeriod() {
        return patrolPeriod;
    }

    public void setPatrolPeriod(String patrolPeriod) {
        this.patrolPeriod = patrolPeriod;
    }

    public Date getPatrolDate() {
        return patrolDate;
    }

    public void setPatrolDate(Date patrolDate) {
        this.patrolDate = patrolDate;
    }

    public String getPatrolContent() {
        return patrolContent;
    }

    public void setPatrolContent(String patrolContent) {
        this.patrolContent = patrolContent;
    }

    @Override
    public void onChanged(GeneralItemText view, String id, String title, Object extra) {
        personName = title;
        patrolPerson = id;
    }
}
