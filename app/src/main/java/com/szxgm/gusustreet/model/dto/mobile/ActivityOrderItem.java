package com.szxgm.gusustreet.model.dto.mobile;

import java.util.Date;

public class ActivityOrderItem {

    /**
     * 事件标题 。
     */
    private String title;

    /**
     * 对接其他平台ID.
     */
    private String duijieId;

    /**
     * 工单ID
     */
    private String orderId;

    /**
     * 工单编号
     */
    private String orderCode;

    /**
     * 流程ID
     */
    private String procInsId;

    /**
     * 拒绝反馈时间, 超过此时间不允许退回工单.
     */
    private Date refuseDate;

    /**
     * 上报时间
     */
    private Date reportDate;

    /**
     * 工单截止时间.
     */
    private Date shouldEndDate;

    /**
     * 工单截止时间12345
     */
    private Date shouldEndDate12345;

    /**
     * 任务ID,非常重要.
     */
    private String taskId;

    /**
     * 环节名称.
     */
    private String taskName;

    /**
     * 处置环节
     */
    private String taskDefKey;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuijieId() {
        return duijieId;
    }

    public void setDuijieId(String duijieId) {
        this.duijieId = duijieId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public Date getRefuseDate() {
        return refuseDate;
    }

    public void setRefuseDate(Date refuseDate) {
        this.refuseDate = refuseDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getShouldEndDate() {
        return shouldEndDate;
    }

    public void setShouldEndDate(Date shouldEndDate) {
        this.shouldEndDate = shouldEndDate;
    }

    public Date getShouldEndDate12345() {
        return shouldEndDate12345;
    }

    public void setShouldEndDate12345(Date shouldEndDate12345) {
        this.shouldEndDate12345 = shouldEndDate12345;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }
}
