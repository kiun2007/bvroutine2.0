package com.szxgm.gusustreet.model.dto.mobile;

import kiun.com.bvroutine.data.viewmodel.SpinnerItem;

/**
 * 诉求类型.
 */
public class AppealType implements SpinnerItem {

    private String appealName;

    private String code;

    public String getAppealName() {
        return appealName;
    }

    public void setAppealName(String appealName) {
        this.appealName = appealName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String label() {
        return appealName;
    }
}
