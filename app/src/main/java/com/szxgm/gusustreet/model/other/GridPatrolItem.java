package com.szxgm.gusustreet.model.other;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡查单项数据.
 */
public class GridPatrolItem implements Serializable {

    /**
     * 任务编号.
     */
    private String id;
    private String createdDate;
    private int delFlag;
    private String creatorId;
    /**
     * 任务名称.
     */
    private String patrolName;
    private String patrolPerson;

    /**
     * 巡查日期.
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date patrolDate;

    /**
     * 巡查时段.
     */
    private int patrolPeriod;

    /**
     * 巡查时间.
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date patrolTime;
    private String patrolContent;
    private String patrolFile;

    /**
     * 巡查状态 1未开始 2开始中 3完成 4暂停
     */
    private int status;
    private String overTimeFlag;
    private int patrolType;
    private String patrolNo;
    private String personName;
    private String wgbm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getPatrolName() {
        return patrolName;
    }

    public void setPatrolName(String patrolName) {
        this.patrolName = patrolName;
    }

    public String getPatrolPerson() {
        return patrolPerson;
    }

    public void setPatrolPerson(String patrolPerson) {
        this.patrolPerson = patrolPerson;
    }

    public Date getPatrolDate() {
        return patrolDate;
    }

    public void setPatrolDate(Date patrolDate) {
        this.patrolDate = patrolDate;
    }

    public int getPatrolPeriod() {
        return patrolPeriod;
    }

    public void setPatrolPeriod(int patrolPeriod) {
        this.patrolPeriod = patrolPeriod;
    }

    public Date getPatrolTime() {
        return patrolTime;
    }

    public void setPatrolTime(Date patrolTime) {
        this.patrolTime = patrolTime;
    }

    public String getPatrolContent() {
        return patrolContent;
    }

    public void setPatrolContent(String patrolContent) {
        this.patrolContent = patrolContent;
    }

    public String getPatrolFile() {
        return patrolFile;
    }

    public void setPatrolFile(String patrolFile) {
        this.patrolFile = patrolFile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOverTimeFlag() {
        return overTimeFlag;
    }

    public void setOverTimeFlag(String overTimeFlag) {
        this.overTimeFlag = overTimeFlag;
    }

    public int getPatrolType() {
        return patrolType;
    }

    public void setPatrolType(int patrolType) {
        this.patrolType = patrolType;
    }

    public String getPatrolNo() {
        return patrolNo;
    }

    public void setPatrolNo(String patrolNo) {
        this.patrolNo = patrolNo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getWgbm() {
        return wgbm;
    }

    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
    }
}
