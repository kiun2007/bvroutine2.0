package com.szxgm.gusustreet.model.dto.mobile;

import java.util.Date;

public class OrderInfo {

    /**
     * 拒绝退回截止时间, 超过此时间.
     */
    private Date orderRefuseTime;

    private String officeId;
    private String orderId;
    private String startType;
    private String orderCode;
    private Object orderEndTime;
    private String difficultType;
    private String title;
    private String orderTitle;
    private String orderReportTime;
    private String seriousDegree;

    /**
     * 是否超出退回截止时间
     * @return
     */
    public boolean isTimeOut(){
        return orderRefuseTime != null && orderRefuseTime.before(new Date());
    }

    public Date getOrderRefuseTime() {
        return orderRefuseTime;
    }

    public void setOrderRefuseTime(Date orderRefuseTime) {
        this.orderRefuseTime = orderRefuseTime;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Object getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Object orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getDifficultType() {
        return difficultType;
    }

    public void setDifficultType(String difficultType) {
        this.difficultType = difficultType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderReportTime() {
        return orderReportTime;
    }

    public void setOrderReportTime(String orderReportTime) {
        this.orderReportTime = orderReportTime;
    }

    public String getSeriousDegree() {
        return seriousDegree;
    }

    public void setSeriousDegree(String seriousDegree) {
        this.seriousDegree = seriousDegree;
    }
}
