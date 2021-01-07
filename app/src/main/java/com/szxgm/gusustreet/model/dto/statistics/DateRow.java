package com.szxgm.gusustreet.model.dto.statistics;

public class DateRow {

    private String date;

    private String value;

    public DateRow(String date, String value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
