package com.szxgm.gusustreet.model.dto.statistics;

public class WorkTimesRow {

    private String name;
    private String times;

    private Object extra;

    public WorkTimesRow(String name, String times) {
        this.name = name;
        this.times = times;
    }

    public WorkTimesRow(String name, String times, Object extra) {
        this(name, times);
        this.extra = extra;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
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
