package com.szxgm.gusustreet.model.dto.mobile;

import com.szxgm.gusustreet.model.base.GeneralItem;

import java.io.Serializable;
import java.util.List;

/**
 * 区河长处置部门.
 */
public class AreaRiverOffice implements GeneralItem, Serializable {

    private DisposeOfficeDTO disposeOffice;

    private List<AreaRiverStreet> streets;

    @Override
    public String getTitle() {
        return disposeOffice != null ? disposeOffice.officeName : "";
    }

    @Override
    public String getId() {
        return disposeOffice != null ? disposeOffice.officeId : "";
    }

    public static class DisposeOfficeDTO implements Serializable{

        private String officeId;

        private String officeName;

        private String areaId;

        private String areaName;

        private String isUnderclass;

        private String orderTypeId;

        private String orderTypeName;

        private String officePersonName;

        private String officePerson;

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

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getIsUnderclass() {
            return isUnderclass;
        }

        public void setIsUnderclass(String isUnderclass) {
            this.isUnderclass = isUnderclass;
        }

        public String getOrderTypeId() {
            return orderTypeId;
        }

        public void setOrderTypeId(String orderTypeId) {
            this.orderTypeId = orderTypeId;
        }

        public String getOrderTypeName() {
            return orderTypeName;
        }

        public void setOrderTypeName(String orderTypeName) {
            this.orderTypeName = orderTypeName;
        }

        public String getOfficePersonName() {
            return officePersonName;
        }

        public void setOfficePersonName(String officePersonName) {
            this.officePersonName = officePersonName;
        }

        public String getOfficePerson() {
            return officePerson;
        }

        public void setOfficePerson(String officePerson) {
            this.officePerson = officePerson;
        }
    }

    public DisposeOfficeDTO getDisposeOffice() {
        return disposeOffice;
    }

    public void setDisposeOffice(DisposeOfficeDTO disposeOffice) {
        this.disposeOffice = disposeOffice;
    }

    public List<AreaRiverStreet> getStreets() {
        return streets;
    }

    public void setStreets(List<AreaRiverStreet> streets) {
        this.streets = streets;
        for (AreaRiverStreet street : streets){
            street.setAreaRiverOffice(this);
        }
    }
}
