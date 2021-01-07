package com.szxgm.gusustreet.model.dto.mobile;

import java.util.Date;

/**
 * 工单流转跟踪.
 */
public class OrderFlowTail {

    private String durationTime;
    private String handleOfficeNameQueue;
    private String assigneeName;
    private String handleName;
    private String activityName;
    private String handleOfficeName;
    private String startTime;
    private String comment;
    private Date endTime;

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getHandleOfficeNameQueue() {
        return handleOfficeNameQueue;
    }

    public void setHandleOfficeNameQueue(String handleOfficeNameQueue) {
        this.handleOfficeNameQueue = handleOfficeNameQueue;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getHandleOfficeName() {
        return handleOfficeName;
    }

    public void setHandleOfficeName(String handleOfficeName) {
        this.handleOfficeName = handleOfficeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
