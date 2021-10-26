package com.szxgm.gusustreet.model.dto.mobile;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.model.base.GeneralItem;

import kiun.com.bvroutine.base.EventBean;

public class Department extends EventBean implements GeneralItem {

    private String officeId;
    private String officeName;
    private String parentId;
    private String typeId;
    private String typeName;
    private String streetCode;
    private String gisx;
    private String gisy;

    /**
     * 是否选中.
     */
    @JSONField(serialize = false)
    private boolean selected = false;

    @Override
    public String getTitle() {
        return officeName;
    }

    @Override
    public String getId() {
        return officeId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getGisx() {
        return gisx;
    }

    public void setGisx(String gisx) {
        this.gisx = gisx;
    }

    public String getGisy() {
        return gisy;
    }

    public void setGisy(String gisy) {
        this.gisy = gisy;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        onChanged();
    }
}
