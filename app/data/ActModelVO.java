package com.ruoyi.activiti.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.xtcz.domain.XtczHandleOfficeRefine;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author zlq
 * @Date 2019/12/17 4:32 下午
 **/
public class ActModelVO extends PageDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    //模型id
    private String id;

    //模型名称
    @NotNull
    private String name;

    //模型标识
    @NotNull
    private String key;

    //模型描述
    private String description;

    //模型分类
    private String category;

    private String businessId;		// 业务绑定ID

    private String title; 		// 任务标题

    private String userId; 		// 用户id
    private String userName; 		// 用户姓名

    private Map<String,Object> params; 		//  流程参数

    private String taskId; 		// 任务ID
    private String taskName; 		// 任务名称
    private String taskDefKey; 		// 任务环节标识
    private String procInsId; 		// 流程实例ID
    private String procDefId; 		// 流程定义ID
    private String status; 		// 签收状态

    protected Act act; 		// 流程任务对象

    private String nowOfficeId;//当前处理部门id
    private String officeId;//处理部门id
    private String isDifficult;//是否疑难工单
    private String difficultSheet;//疑难事项上报单
    private String reportOrderSource;//上报事件平台
    //协同处置部门
    private String officeRefines;
    //待办列表类型
    private String orderListType;

    public String getIsDifficult() {
        return isDifficult;
    }

    public void setIsDifficult(String isDifficult) {
        this.isDifficult = isDifficult;
    }

    public String getDifficultSheet() {
        return difficultSheet;
    }

    public void setDifficultSheet(String difficultSheet) {
        this.difficultSheet = difficultSheet;
    }

    @JsonIgnore
    public Act getAct() {
        if (act == null){
            act = new Act();
        }
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public String getOrderListType() {
        return orderListType;
    }

    public void setOrderListType(String orderListType) {
        this.orderListType = orderListType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getReportOrderSource() {
        return reportOrderSource;
    }

    public void setReportOrderSource(String reportOrderSource) {
        this.reportOrderSource = reportOrderSource;
    }

    public String getOfficeRefines() {
        return officeRefines;
    }

    public void setOfficeRefines(String officeRefines) {
        this.officeRefines = officeRefines;
    }

    public String getNowOfficeId() {
        return nowOfficeId;
    }

    public void setNowOfficeId(String nowOfficeId) {
        this.nowOfficeId = nowOfficeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("key", getKey())
                .append("description", getDescription())
                .append("category", getCategory())
                .toString();
    }
}
