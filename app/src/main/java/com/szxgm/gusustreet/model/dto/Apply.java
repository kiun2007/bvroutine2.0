package com.szxgm.gusustreet.model.dto;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;

public class Apply extends QueryBean {

    /**
     * 打卡记录
     */
    @Verify(value = NotNull.class, desc = "请选择打卡记录")
    private String dkid;

    /**
     * 申报原因
     */
    @Verify(value = NotNull.class, desc = "请选择申报原因")
    private String sbReason;

    /**
     * 申报打卡时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @Verify(value = NotNull.class, desc = "请选择实际打卡时间")
    private Date sbBegin;

    public String getDkid() {
        return dkid;
    }

    public void setDkid(String dkid) {
        this.dkid = dkid;
    }

    public String getSbReason() {
        return sbReason;
    }

    public void setSbReason(String sbReason) {
        this.sbReason = sbReason;
        onChanged();
    }

    public Date getSbBegin() {
        return sbBegin;
    }

    public void setSbBegin(Date sbBegin) {
        this.sbBegin = sbBegin;
    }
}
