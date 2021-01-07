package com.szxgm.gusustreet.model.dto.river;

import java.math.BigDecimal;
import java.util.Date;

public class RiverDetail {

    private String id;

    private String code;

    private String rivername;

    private String place;

    private String area;

    private String rivermaste;

    private String phone;

    private String position;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    private String sysAreaId;

    private String riverlevel;

    private String image;

    private String lnglat;

    private String disposePerson;

    private String transferOffice;

    private BigDecimal orderr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRivername() {
        return rivername;
    }

    public void setRivername(String rivername) {
        this.rivername = rivername == null ? null : rivername.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getRivermaste() {
        return rivermaste;
    }

    public void setRivermaste(String rivermaste) {
        this.rivermaste = rivermaste == null ? null : rivermaste.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getSysAreaId() {
        return sysAreaId;
    }

    public void setSysAreaId(String sysAreaId) {
        this.sysAreaId = sysAreaId == null ? null : sysAreaId.trim();
    }

    public String getRiverlevel() {
        return riverlevel;
    }

    public void setRiverlevel(String riverlevel) {
        this.riverlevel = riverlevel == null ? null : riverlevel.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat == null ? null : lnglat.trim();
    }

    public String getDisposePerson() {
        return disposePerson;
    }

    public void setDisposePerson(String disposePerson) {
        this.disposePerson = disposePerson == null ? null : disposePerson.trim();
    }

    public String getTransferOffice() {
        return transferOffice;
    }

    public void setTransferOffice(String transferOffice) {
        this.transferOffice = transferOffice == null ? null : transferOffice.trim();
    }

    public BigDecimal getOrderr() {
        return orderr;
    }

    public void setOrderr(BigDecimal orderr) {
        this.orderr = orderr;
    }
}