package com.szxgm.gusustreet.model.dto.mobile;

/**
 * 协调任务.
 */
public class CombinedTask {

    private String createTime;
    private String id;
    private String delFlag;
    private String orderId;
    private String handleOffice;
    private String handleOfficeName;
    private String handleContent;
    private String handlePerson;
    private String handlePersonName;
    private String workStatement;
    private String finishTime;
    private String finishState;
    private String userId;
    private String type;
    private String leader;
    private CombinedOrder orderInfo;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHandleOffice() {
        return handleOffice;
    }

    public void setHandleOffice(String handleOffice) {
        this.handleOffice = handleOffice;
    }

    public String getHandleOfficeName() {
        return handleOfficeName;
    }

    public void setHandleOfficeName(String handleOfficeName) {
        this.handleOfficeName = handleOfficeName;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public String getHandlePerson() {
        return handlePerson;
    }

    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }

    public String getHandlePersonName() {
        return handlePersonName;
    }

    public void setHandlePersonName(String handlePersonName) {
        this.handlePersonName = handlePersonName;
    }

    public String getWorkStatement() {
        return workStatement;
    }

    public void setWorkStatement(String workStatement) {
        this.workStatement = workStatement;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishState() {
        return finishState;
    }

    public void setFinishState(String finishState) {
        this.finishState = finishState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public CombinedOrder getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(CombinedOrder orderInfo) {
        this.orderInfo = orderInfo;
    }
}
