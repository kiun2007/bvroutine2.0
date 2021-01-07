package com.szxgm.gusustreet.net.requests;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.utils.MCString;

public class WorkTimeReq extends EventBean {

    private String pbUser;

    @JSONField(format = "yyyy-MM-dd")
    private Date pbStartDate = new Date();

    @JSONField(format = "yyyy-MM-dd")
    private Date pbEndDate = new Date();

    public Date getPbStartDate() {
        return pbStartDate;
    }

    public void setPbStartDate(Date pbStartDate) {
        this.pbEndDate = pbStartDate;
        this.pbStartDate = pbStartDate;
        onChanged(false);
    }

    public Date getPbEndDate() {
        return pbEndDate;
    }

    public void setPbEndDate(Date pbEndDate) {
        this.pbEndDate = pbEndDate;
        this.pbStartDate = pbEndDate;
        onChanged(false);
    }

    public String getPbUser() {
        return pbUser;
    }

    public void setPbUser(String pbUser) {
        this.pbUser = pbUser;
    }
}
