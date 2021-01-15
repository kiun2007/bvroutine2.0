package com.szxgm.gusustreet.model.dto;

import com.alibaba.fastjson.annotation.JSONField;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.data.verify.RangeLimit;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

import java.util.Date;

public class Overtime extends EventBean {

    private String jbId;

    private String jbDept;

    @Verify(value = NotNull.class, desc = "请选择")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date jbBegin;

    @Verify(value = NotNull.class, desc = "请选择")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date jbEnd;

    @Verifys({
            @Verify(value = NotNull.class, desc = "请选择开始时间和结束时间"),
            @Verify(value = RangeLimit.class, desc = "必须大于0", extra = "[0,100000,3]")
    })
    private String jbTotal;

    @Verify(value = NotNull.class, desc = "请输入加班事由")
    private String jbReason;

    private String jbAnnex;

    private Date createTime;

    private String updateBy;

    private String jbStatus;

    private String remark;

    private Date updateTime;

    public String getJbId() {
        return jbId;
    }

    public void setJbId(String jbId) {
        this.jbId = jbId == null ? null : jbId.trim();
    }

    public String getJbDept() {
        return jbDept;
    }

    public void setJbDept(String jbDept) {
        this.jbDept = jbDept == null ? null : jbDept.trim();
    }

    public Date getJbBegin() {
        return jbBegin;
    }

    public void setJbBegin(Date jbBegin) {
        this.jbBegin = jbBegin;
        onChanged(false);
    }

    public Date getJbEnd() {
        return jbEnd;
    }

    public void setJbEnd(Date jbEnd) {
        this.jbEnd = jbEnd;
        onChanged(false);
    }

    public String getJbTotal() {
        return jbTotal;
    }

    public void setJbTotal(String jbTotal) {
        this.jbTotal = jbTotal == null ? null : jbTotal.trim();
    }

    public String getJbReason() {
        return jbReason;
    }

    public void setJbReason(String jbReason) {
        this.jbReason = jbReason == null ? null : jbReason.trim();
    }

    public String getJbAnnex() {
        return jbAnnex;
    }

    public void setJbAnnex(String jbAnnex) {
        this.jbAnnex = jbAnnex == null ? null : jbAnnex.trim();
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

    public String getJbStatus() {
        return jbStatus;
    }

    public void setJbStatus(String jbStatus) {
        this.jbStatus = jbStatus == null ? null : jbStatus.trim();
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

    public void clear(){
        jbBegin = null;
        jbEnd = null;
        jbTotal = null;
        onChanged();
    }
}
