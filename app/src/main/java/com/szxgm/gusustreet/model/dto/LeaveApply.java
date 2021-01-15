package com.szxgm.gusustreet.model.dto;

import com.alibaba.fastjson.annotation.JSONField;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.data.verify.RangeLimit;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

import java.util.Date;

public class LeaveApply extends QueryBean {

    private String qjId;

    @Verify(value = NotNull.class, desc = "请选择")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date qjBegin;

    @Verify(value = NotNull.class, desc = "请选择")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date qjEnd;

    @Verifys({
            @Verify(value = NotNull.class, desc = "请选中开始时间和结束时间"),
            @Verify(value = RangeLimit.class, desc = "时间范围内没有排班,无需请假", extra = "[0,100000,3]")
    })
    private Integer qjTotaltime;

    private String qjType;

    @Verify(value = NotNull.class, desc = "请输入请假事由")
    private String qjReason;

    private String qjAnnex;

    private Date createTime;

    private String updateBy;

    private String qjStatus = "1";

    private String remark;

    private Date updateTime;

    public String getQjId() {
        return qjId;
    }

    public void setQjId(String qjId) {
        this.qjId = qjId == null ? null : qjId.trim();
    }

    public Date getQjBegin() {
        return qjBegin;
    }

    public void setQjBegin(Date qjBegin) {

        if (qjEnd != null && qjBegin.after(qjEnd)){
            onError("开始时间不能在结束时间之后!");
            return;
        }
        this.qjBegin = qjBegin;
        onChanged(false);
    }

    public Date getQjEnd() {
        return qjEnd;
    }

    public void setQjEnd(Date qjEnd) {

        if (qjBegin != null && qjEnd.before(qjBegin)){
            onError("结束时间不能在开始时间之前!");
            return;
        }
        this.qjEnd = qjEnd;
        onChanged(false);
    }

    public Integer getQjTotaltime() {
        return qjTotaltime;
    }

    public void setQjTotaltime(Integer qjTotaltime) {
        this.qjTotaltime = qjTotaltime;
    }

    public String getQjType() {
        return qjType;
    }

    public void setQjType(String qjType) {
        this.qjType = qjType == null ? null : qjType.trim();
    }

    public String getQjReason() {
        return qjReason;
    }

    public void setQjReason(String qjReason) {
        this.qjReason = qjReason == null ? null : qjReason.trim();
    }

    public String getQjAnnex() {
        return qjAnnex;
    }

    public void setQjAnnex(String qjAnnex) {
        this.qjAnnex = qjAnnex == null ? null : qjAnnex.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getQjStatus() {
        return qjStatus;
    }

    public void setQjStatus(String qjStatus) {
        this.qjStatus = qjStatus == null ? null : qjStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(String id) {
        this.qjId = id;
    }

    public String getId() {
        return qjId;
    }

    @Override
    public void clear() {
        qjBegin = null;
        qjEnd = null;
        qjTotaltime = null;
        onChanged();
    }
}
