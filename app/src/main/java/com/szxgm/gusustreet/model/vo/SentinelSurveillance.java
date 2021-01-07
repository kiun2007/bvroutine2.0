package com.szxgm.gusustreet.model.vo;

public class SentinelSurveillance {

    private int id;
    private String createdOn;
    private int createdBy;
    private int updatedBy;
    private String updatedOn;
    private int versionNo;
    private boolean isDeleted;
    private String address;
    private Double longitude;
    private Double latitude;
    private int pathId;
    private int sentinelLevel;
    private String allPath;
    private String reportPath;
    private int attachId;
    private String attachment;
    private int dataStatus;
    private String dataStatusDesc;
    private int unitId;
    private String unit;
    private boolean result;
    private Object reportStartDate;
    private Object reportEndDate;
    private String description;
    private String reportMan;
    private String contact;
    private Object reportMonth;
    private Object sentinelSuggestionDto;
    private AttachmentInfo attachmentInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

    public int getSentinelLevel() {
        return sentinelLevel;
    }

    public void setSentinelLevel(int sentinelLevel) {
        this.sentinelLevel = sentinelLevel;
    }

    public String getAllPath() {
        return allPath;
    }

    public void setAllPath(String allPath) {
        this.allPath = allPath;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public int getAttachId() {
        return attachId;
    }

    public void setAttachId(int attachId) {
        this.attachId = attachId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getDataStatusDesc() {
        return dataStatusDesc;
    }

    public void setDataStatusDesc(String dataStatusDesc) {
        this.dataStatusDesc = dataStatusDesc;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Object reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Object getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Object reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReportMan() {
        return reportMan;
    }

    public void setReportMan(String reportMan) {
        this.reportMan = reportMan;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Object getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(Object reportMonth) {
        this.reportMonth = reportMonth;
    }

    public Object getSentinelSuggestionDto() {
        return sentinelSuggestionDto;
    }

    public void setSentinelSuggestionDto(Object sentinelSuggestionDto) {
        this.sentinelSuggestionDto = sentinelSuggestionDto;
    }

    public AttachmentInfo getAttachmentInfo() {
        return attachmentInfo;
    }

    public void setAttachmentInfo(AttachmentInfo attachmentInfo) {
        this.attachmentInfo = attachmentInfo;
    }

}
