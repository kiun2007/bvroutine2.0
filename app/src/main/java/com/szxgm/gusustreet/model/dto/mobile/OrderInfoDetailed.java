package com.szxgm.gusustreet.model.dto.mobile;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class OrderInfoDetailed implements Serializable {

    private String createBy;
    private String createTime;
    private String title;
    private String appealType;
    private String orderType;
    private String seriousDegree;
    private String partyGrid;
    private String partyGridName;
    private String description;
    private Double gisx;
    private Double gisy;
    private String position;
    private String reportPerson;
    private String reportOffice;
    private String orderId;
    private String orderCode;
    private String orderSource;
    private String reportDate;
    private String shouldEndDate;
    private String orderState;
    private String delFlag;

    /**
     * 疑难工单标志
     */
    private String difficult;
    private String seriousDegreeName;
    private String procInsId;
    private String appealTypeName;
    private String ascription;
    private String isAccept;
    private String difficultSheet;
    private String imageData;

    public OrderInfoDetailed(){
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppealType() {
        return appealType;
    }

    public void setAppealType(String appealType) {
        this.appealType = appealType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSeriousDegree() {
        return seriousDegree;
    }

    public void setSeriousDegree(String seriousDegree) {
        this.seriousDegree = seriousDegree;
    }

    public String getPartyGrid() {
        return partyGrid;
    }

    public void setPartyGrid(String partyGrid) {
        this.partyGrid = partyGrid;
    }

    public String getPartyGridName() {
        return partyGridName;
    }

    public void setPartyGridName(String partyGridName) {
        this.partyGridName = partyGridName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getReportOffice() {
        return reportOffice;
    }

    public void setReportOffice(String reportOffice) {
        this.reportOffice = reportOffice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getSeriousDegreeName() {
        return seriousDegreeName;
    }

    public void setSeriousDegreeName(String seriousDegreeName) {
        this.seriousDegreeName = seriousDegreeName;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getAppealTypeName() {
        return appealTypeName;
    }

    public void setAppealTypeName(String appealTypeName) {
        this.appealTypeName = appealTypeName;
    }

    public String getAscription() {
        return ascription;
    }

    public void setAscription(String ascription) {
        this.ascription = ascription;
    }

    public String getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept;
    }

    public Double getGisx() {
        return gisx;
    }

    public void setGisx(Double gisx) {
        this.gisx = gisx;
    }

    public Double getGisy() {
        return gisy;
    }

    public void setGisy(Double gisy) {
        this.gisy = gisy;
    }

    public String getShouldEndDate() {
        return shouldEndDate;
    }

    public void setShouldEndDate(String shouldEndDate) {
        this.shouldEndDate = shouldEndDate;
    }

    public String getDifficultSheet() {
        return difficultSheet;
    }

    public void setDifficultSheet(String difficultSheet) {
        this.difficultSheet = difficultSheet;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    @JSONField(serialize = false)
    public List<String> getImageList(){
        if (imageData != null){
            return Arrays.asList(imageData.split("\\|"));
        }
        return null;
    }
}
