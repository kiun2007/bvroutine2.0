package com.szxgm.gusustreet.model.query;

import kiun.com.bvroutine.data.PagerBean;

public class OrderReq extends PagerBean {

    /**
     * 工单类型.
     */
    private String orderListType;

    private String orderCode;

    private String startTime;

    private String endTime;

    /**
     * 用户ID.
     */
    private String userId;

    public String getOrderListType() {
        return orderListType;
    }

    public void setOrderListType(String orderListType) {
        this.orderListType = orderListType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
