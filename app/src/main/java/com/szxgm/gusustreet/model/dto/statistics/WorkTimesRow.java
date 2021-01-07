package com.szxgm.gusustreet.model.dto.statistics;

public class WorkTimesRow {

    private String name;
    private String times;

    public WorkTimesRow(String name, String times) {
        this.name = name;
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
