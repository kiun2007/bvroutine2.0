package com.szxgm.gusustreet.model.dto.mobile;

/**
 * 协调任务详情.
 */
public class CombinedTaskInfo {

    private String createTime;
    private String id;
    private String delFlag;
    private String orderId;
    private String handleOffice;
    private String handleOfficeName;
    private String handleContent;
    private String type;
    private String leader;
    private OrderInfoDetailed orderInfo;

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

    public OrderInfoDetailed getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoDetailed orderInfo) {
        this.orderInfo = orderInfo;
    }
}
