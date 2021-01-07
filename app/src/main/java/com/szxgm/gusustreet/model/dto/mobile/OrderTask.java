package com.szxgm.gusustreet.model.dto.mobile;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.model.base.TaskStatus;

import java.util.Date;

/**
 * 工单任务.
 */
public class OrderTask {

    private String taskId;
    private String taskName;
    private String taskDefKey;
    private String procInsId;
    private String procDefId;
    private String procDefKey;
    private TaskStatus status;
    private VarsBean vars;
    private String mark;
    private String durationTime;
    private String seriousDegree;
    private String seriousDegreeName;

    private boolean todoTask;
    private boolean finishTask;

    private OrderInfoDetailed xtczOrderInfo;

    public OrderInfoDetailed getXtczOrderInfo() {
        return xtczOrderInfo;
    }

    public void setXtczOrderInfo(OrderInfoDetailed xtczOrderInfo) {
        this.xtczOrderInfo = xtczOrderInfo;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date taskCreateDate;

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

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public VarsBean getVars() {
        return vars;
    }

    public void setVars(VarsBean vars) {
        this.vars = vars;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public boolean isTodoTask() {
        return todoTask;
    }

    public void setTodoTask(boolean todoTask) {
        this.todoTask = todoTask;
    }

    public boolean isFinishTask() {
        return finishTask;
    }

    public void setFinishTask(boolean finishTask) {
        this.finishTask = finishTask;
    }

    public Date getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(Date taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }

    public String getSeriousDegree() {
        return seriousDegree;
    }

    public String getSeriousDegreeName() {
        return seriousDegreeName;
    }

    public void setSeriousDegreeName(String seriousDegreeName) {
        this.seriousDegreeName = seriousDegreeName;
    }

    public void setSeriousDegree(String seriousDegree) {
        this.seriousDegree = seriousDegree;
    }

    public static class VarsBean {
        private OrderInfo map;

        public OrderInfo getMap() {
            return map;
        }

        public void setMap(OrderInfo map) {
            this.map = map;
        }
    }
}
