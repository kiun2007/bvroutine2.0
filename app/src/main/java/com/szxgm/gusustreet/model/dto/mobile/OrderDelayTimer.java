package com.szxgm.gusustreet.model.dto.mobile;

public class OrderDelayTimer {

    /**
     * id
     */
    private String id;

    /**
     * 申请名称.
     */
    private String appealName;

    /**
     * 时长
     */
    private String hourNumber;

    /**
     * 类型
     */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppealName() {
        return appealName;
    }

    public void setAppealName(String appealName) {
        this.appealName = appealName;
    }

    public String getHourNumber() {
        return hourNumber;
    }

    public void setHourNumber(String hourNumber) {
        this.hourNumber = hourNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
