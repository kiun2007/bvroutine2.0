package com.szxgm.gusustreet.model.dto.attendance;

import java.util.Date;

/**
 * 上班打卡.
 */
public class ArriveClock {

    private String dkPbid;

    private Date dkBegin;

    private Double dkBeginlong;

    private Double dkBeginlat;

    private String dkBeginaddress;

    private String dkLate;

    private String dkPbname;

    public boolean isLate(){
        return "1".equals(dkLate);
    }

    public String getDkPbid() {
        return dkPbid;
    }

    public void setDkPbid(String dkPbid) {
        this.dkPbid = dkPbid;
    }

    public Date getDkBegin() {
        return dkBegin;
    }

    public void setDkBegin(Date dkBegin) {
        this.dkBegin = dkBegin;
    }

    public String getDkBeginaddress() {
        return dkBeginaddress;
    }

    public void setDkBeginaddress(String dkBeginaddress) {
        this.dkBeginaddress = dkBeginaddress;
    }

    public String getDkLate() {
        return dkLate;
    }

    public void setDkLate(String dkLate) {
        this.dkLate = dkLate;
    }

    public String getDkPbname() {
        return dkPbname;
    }

    public void setDkPbname(String dkPbname) {
        this.dkPbname = dkPbname;
    }

    public Double getDkBeginlong() {
        return dkBeginlong;
    }

    public void setDkBeginlong(Double dkBeginlong) {
        this.dkBeginlong = dkBeginlong;
    }

    public Double getDkBeginlat() {
        return dkBeginlat;
    }

    public void setDkBeginlat(Double dkBeginlat) {
        this.dkBeginlat = dkBeginlat;
    }
}
