package com.szxgm.gusustreet.net.requests;

import java.util.Date;

public class ClockHistoryReq {

    private Date dkBegin;

    private Date dkEnd;

    public Date getDkBegin() {
        return dkBegin;
    }

    public void setDkBegin(Date dkBegin) {
        this.dkBegin = dkBegin;
    }

    public Date getDkEnd() {
        return dkEnd;
    }

    public void setDkEnd(Date dkEnd) {
        this.dkEnd = dkEnd;
    }
}