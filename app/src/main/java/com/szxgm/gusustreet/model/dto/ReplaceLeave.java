package com.szxgm.gusustreet.model.dto;

import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;


public class ReplaceLeave extends QueryBean {

    /**
     * 调班班次
     */
    @Verifys(@Verify(value = NotNull.class, desc = "请选择"))
    private String tbqId;

    /**
     * 被调班次
     */
    @Verifys(@Verify(value = NotNull.class, desc = "请选择"))
    private String btbId;

    /**
     * 人员ID
     */
    @Verifys(@Verify(value = NotNull.class, desc = "请选择"))
    private String tbLaterId;

    /**
     * 理由
     */
    @Verify(value = NotNull.class, desc = "输入理由")
    private String tbReason;


    public String getTbqId() {
        return tbqId;
    }

    public void setTbqId(String tbqId) {
        this.tbqId = tbqId;
    }

    public String getBtbId() {
        return btbId;
    }

    public void setBtbId(String btbId) {
        this.btbId = btbId;
        onChanged();
    }

    public String getTbReason() {
        return tbReason;
    }

    public void setTbReason(String tbReason) {
        this.tbReason = tbReason;
    }

    public String getTbLaterId() {
        return tbLaterId;
    }

    public void setTbLaterId(String tbLaterId) {
        this.tbLaterId = tbLaterId;
    }
}
