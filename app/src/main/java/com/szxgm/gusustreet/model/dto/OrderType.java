package com.szxgm.gusustreet.model.dto;

import kiun.com.bvroutine.interfaces.callers.GetCaller;

public class OrderType {

    private String id;

    private String orderType;

    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public static GetCaller<OrderType, String> labelGet(){
        return OrderType::getOrderType;
    }

    public static GetCaller<OrderType, String> valueGet(){
        return OrderType::getId;
    }
}
