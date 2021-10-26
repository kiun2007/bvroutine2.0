package com.szxgm.gusustreet.model.dto.attendance;

import com.szxgm.gusustreet.model.base.GeneralItem;

public class ArriveTime implements GeneralItem {

    private String bcTimes;

    private String bcBegin;

    private String bcEnd;

    private String bcRest;

    private String bcChoose;

    private String bcWeek;

    public String getBcTimes() {
        return bcTimes;
    }

    public void setBcTimes(String bcTimes) {
        this.bcTimes = bcTimes == null ? null : bcTimes.trim();
    }

    public String getBcBegin() {
        return bcBegin;
    }

    public void setBcBegin(String bcBegin) {
        this.bcBegin = bcBegin == null ? null : bcBegin.trim();
    }

    public String getBcEnd() {
        return bcEnd;
    }

    public void setBcEnd(String bcEnd) {
        this.bcEnd = bcEnd == null ? null : bcEnd.trim();
    }

    public String getBcRest() {
        return bcRest;
    }

    public void setBcRest(String bcRest) {
        this.bcRest = bcRest == null ? null : bcRest.trim();
    }

    public String getBcChoose() {
        return bcChoose;
    }

    public void setBcChoose(String bcChoose) {
        this.bcChoose = bcChoose == null ? null : bcChoose.trim();
    }

    public String getBcWeek() {
        return bcWeek;
    }

    public void setBcWeek(String bcWeek) {
        this.bcWeek = bcWeek == null ? null : bcWeek.trim();
    }

    @Override
    public String getTitle() {
        return bcTimes;
    }

    @Override
    public String getId() {
        return bcTimes;
    }
}
