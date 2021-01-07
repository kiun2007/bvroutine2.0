package com.szxgm.gusustreet.model.dto.mobile;

import com.szxgm.gusustreet.model.base.GeneralItem;

import java.io.Serializable;

public class AreaRiverStreet implements Serializable, GeneralItem {

    /**
     * 街道人员名称
     */
    private String streetPersonName;

    /**
     * 街道人员ID
     */
    private String streetPerson;

    /**
     * 街道名称
     */
    private String streetIdName;

    /**
     * 街道ID
     */
    private String streetId;

    /**
     * 父类处置部门对象.
     */
    private AreaRiverOffice areaRiverOffice;

    public AreaRiverOffice getAreaRiverOffice() {
        return areaRiverOffice;
    }

    public void setAreaRiverOffice(AreaRiverOffice areaRiverOffice) {
        this.areaRiverOffice = areaRiverOffice;
    }

    public String getStreetPersonName() {
        return streetPersonName;
    }

    public void setStreetPersonName(String streetPersonName) {
        this.streetPersonName = streetPersonName;
    }

    public String getStreetPerson() {
        return streetPerson;
    }

    public void setStreetPerson(String streetPerson) {
        this.streetPerson = streetPerson;
    }

    public String getStreetIdName() {
        return streetIdName;
    }

    public void setStreetIdName(String streetIdName) {
        this.streetIdName = streetIdName;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    @Override
    public String getTitle() {
        return streetIdName;
    }

    @Override
    public String getId() {
        return streetId;
    }
}
