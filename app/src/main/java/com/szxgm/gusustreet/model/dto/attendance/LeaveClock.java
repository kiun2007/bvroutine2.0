package com.szxgm.gusustreet.model.dto.attendance;

import android.text.TextUtils;

import com.szxgm.gusustreet.model.base.LastLocation;
import com.szxgm.gusustreet.model.dto.attendance.ArriveClock;

import java.util.Date;

/**
 * 下班打卡，合并上班信息.
 */
public class LeaveClock extends ArriveClock {

    private String dkId;

    private Date dkEnd;

    private Double dkEndlong;

    private Double dkEndlat;

    private String dkEndaddress;

    private String dkEarly;

    public boolean isEarly(){
        return !TextUtils.isEmpty(dkEarly) && dkEarly.equals("0");
    }

    public String getDkId() {
        return dkId;
    }

    public void setDkId(String dkId) {
        this.dkId = dkId;
    }

    public Date getDkEnd() {
        return dkEnd;
    }

    public void setDkEnd(Date dkEnd) {
        this.dkEnd = dkEnd;
    }

    public String getDkEndaddress() {
        return dkEndaddress;
    }

    public void setDkEndaddress(String dkEndaddress) {
        this.dkEndaddress = dkEndaddress;
    }

    public boolean isComplete(){

        if (getDkBegin() != null && dkEnd != null){
            return true;
        }

        return false;
    }

    public String getDkEarly() {
        return dkEarly;
    }

    public void setDkEarly(String dkEarly) {
        this.dkEarly = dkEarly;
    }

    public Double getDkEndlong() {
        return dkEndlong;
    }

    public void setDkEndlong(Double dkEndlong) {
        this.dkEndlong = dkEndlong;
    }

    public Double getDkEndlat() {
        return dkEndlat;
    }

    public void setDkEndlat(Double dkEndlat) {
        this.dkEndlat = dkEndlat;
    }

    /**
     * 上班打卡.
     * @param lastLocation 系统最后的定位位置信息.
     * @return
     * @throws Exception 定位失败时无法打卡报错.
     */
    public LeaveClock arrive(LastLocation lastLocation) {

        if (lastLocation != null){
            lastLocation = lastLocation.convert84();
            setDkBeginaddress(lastLocation.getAddress());
            setDkBeginlat(lastLocation.getLng()); //
            setDkBeginlong(lastLocation.getLat()); //
            setDkBegin(new Date());
        }
        return this;
    }

    /**
     * 下班打卡
     * @param lastLocation 系统最后的定位位置信息.
     * @return
     * @throws Exception 定位失败时无法打卡报错.
     */
    public LeaveClock leave(LastLocation lastLocation){

        if (lastLocation != null){
            lastLocation = lastLocation.convert84();
            setDkEndaddress(lastLocation.getAddress());
            setDkEndlat(lastLocation.getLng());
            setDkEndlong(lastLocation.getLat());
            setDkEnd(new Date());
        }
        return this;
    }
}
