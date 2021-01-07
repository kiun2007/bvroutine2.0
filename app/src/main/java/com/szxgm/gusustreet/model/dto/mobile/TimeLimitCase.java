package com.szxgm.gusustreet.model.dto.mobile;

import kiun.com.bvroutine.data.viewmodel.SpinnerItem;
import kiun.com.bvroutine.data.viewmodel.SpinnerItemVal;

public class TimeLimitCase implements SpinnerItemVal {

    private Integer hourNumber;

    private String limitTimeName;

    public Integer getHourNumber() {
        return hourNumber;
    }

    public void setHourNumber(Integer hourNumber) {
        this.hourNumber = hourNumber;
    }

    public String getLimitTimeName() {
        return limitTimeName;
    }

    public void setLimitTimeName(String limitTimeName) {
        this.limitTimeName = limitTimeName;
    }

    @Override
    public String label() {
        return limitTimeName;
    }

    @Override
    public Object value() {
        return hourNumber;
    }
}
