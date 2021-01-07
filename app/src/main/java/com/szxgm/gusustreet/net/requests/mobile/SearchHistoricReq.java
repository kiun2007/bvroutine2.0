//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.net.requests.mobile;

import com.alibaba.fastjson.annotation.JSONField;
import kiun.com.bvroutine.data.PagerBean;

public class SearchHistoricReq extends PagerBean<Object, SearchHistoricReq> {
    @JSONField(
            name = "params[beginTime]"
    )
    private String beginTime;
    @JSONField(
            name = "params[endTime]"
    )
    private String endTime;
    private String isAsc = "asc";
    private String orderCode;

    public SearchHistoricReq() {
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getIsAsc() {
        return this.isAsc;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setBeginTime(String var1) {
        this.beginTime = var1;
    }

    public void setEndTime(String var1) {
        this.endTime = var1;
    }

    public void setIsAsc(String var1) {
        this.isAsc = var1;
    }

    public void setOrderCode(String var1) {
        this.orderCode = var1;
    }
}
