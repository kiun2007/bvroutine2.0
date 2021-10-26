package com.szxgm.gusustreet.model.dto.mobile;

import android.content.Context;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.net.services.MobileService;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ContextHandlerVariableSet;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.utils.RetrofitUtil;

@AutoImport(ContextHandlerVariableSet.class)
public class OrderDelay extends QueryBean {

    private String orderId;
    private String appealType;
    private Integer startType = 0;
    private String duration;
    private String type;

    @Verify(value = NotNull.class, desc = "请输入申请理由")
    private String reason;

    @JSONField(serialize = false)
    private RequestBVActivity activity;

    public OrderDelay(Context context) {
        if (context instanceof RequestBVActivity){
            activity = (RequestBVActivity) context;
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderDelay orderId(String orderId){
        this.orderId = orderId;
        return this;
    }

    public String getAppealType() {
        return appealType;
    }

    public void setAppealType(String appealType) {
        this.appealType = appealType;
        startGetTimer();
    }

    private void startGetTimer(){
        if (activity != null){
            activity.getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceData(MobileService.class, s -> s.getHourNumber(appealType, type)), this::onAppealTypeChanged);
        }
    }

    private void onAppealTypeChanged(OrderDelayTimer orderDelayTimer){
        duration = orderDelayTimer.getHourNumber();
        onChanged();
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        startGetTimer();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
