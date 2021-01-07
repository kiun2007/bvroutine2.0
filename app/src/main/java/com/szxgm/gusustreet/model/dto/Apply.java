package com.szxgm.gusustreet.model.dto;


import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.QueryBean;

public class Apply extends QueryBean {

    /**
     * 打卡记录
     */
    private String dkid;

    /**
     * 申报原因
     */
    private String sbReason;

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
}
