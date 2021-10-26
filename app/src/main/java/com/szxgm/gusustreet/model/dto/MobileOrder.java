package com.szxgm.gusustreet.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.AddressChooseBeanVariableSet;
import com.szxgm.gusustreet.model.base.AddressChooseBean;

import java.io.Serializable;
import java.util.Date;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.data.verify.LengthLimit;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.GeneralItemTextListener;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import kiun.com.bvroutine.utils.ParcelableUtil;
import kiun.com.bvroutine.views.text.GeneralItemText;

@AutoImport(AddressChooseBeanVariableSet.class)
public class MobileOrder extends AddressChooseBean implements Serializable, GeneralItemTextListener {

    public MobileOrder(){
    }

    /**
     * 事件主键id
     */
    private String orderId;

    /**
     * 事件编号
     */
    private String orderCode;


    /**
     * 0 非自办结 9自办结
     */
    private String startType = "0";

    /**
     * 标题.
     */
    @Verify(value = NotNull.class, desc = "请输入事件标题")
    private String title;

    /**
     * 所属网格
     */
    @Verify(value = NotNull.class,desc = "请选择所属网格")
    private String partyGrid;

    /**
     * 事发位置说明
     */
    @Verify(value = NotNull.class,desc = "请选择事发位置")
    private String position;

    /**
     * 问题描述
     */
    @Verifys({
            @Verify(value = NotNull.class,desc = "请描述您所遇到的问题"),
            @Verify(value = LengthLimit.class, extra = "[10,100]", desc = "请详细描述您所遇到的问题10字以上")
    })
    private String description;

    /**
     * 事件类型
     */
    @Verify(value = NotNull.class,desc = "请选择事件类型")
    private String orderType;

    /**
     * 事件来源, 0 街道 30 河长制
     */
    private String orderSource = "0";

    /**
     * 严重程度
     */
    private String seriousDegree;

    /**
     * 网格名称.
     */
    private String partyGridName;

    /**
     * 纬度
     */
    private Double gisx;

    /**
     * 经度.
     */
    private Double gisy;

    /**
     * 结案意见
     */
    private String closeOpinion;

    /**
     * 结案附件
     */
    private String closeAnnex;

    /**
     * 事件状态
     */
    private String orderState;

    /**
     * 诉求类型.
     */
    private String appealType;

    /**
     * 删除标记（0未删除，2已删除）
     */
    private String delFlag;

    /**
     * 是否疑难工单（0否，2是）
     */
    private String difficult;

    /**
     * 流程实例ID
     */
    private String procInsId;

    /**
     * 是否申请延期（0否，1是）
     */
    private String isDelay;

    /**
     * 上报时间
     */
    private Date reportDate;

    /**
     * 上报人
     */
    private String reportPerson;

    /**
     * 街道级河长制工单中使用, 河道区域ID
     */
    private String riverId;

    /**
     * 河道名称.
     */
    private String riverName;

    /**
     * 河道事项ID
     */
    private String riverOrderType;

    /**
     * 河道事项名称
     */
    private String riverOrderTypeName;

    @JSONField(serialize = false)
    private OrderType riverOrderTypeValue;

    /**
     * 工单图片
     */
    @Verify(value = NotNull.class, desc = "至少上传一张照片")
    private String imageData;

    private boolean commitMode = false;

    public OrderType getRiverOrderTypeValue() {
        return riverOrderTypeValue;
    }

    public void setRiverOrderTypeValue(OrderType value) {
        this.riverOrderTypeValue = value;
        riverOrderTypeName = value.getOrderType();
        riverOrderType = value.getId();
        onChanged();
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
        onChanged();
    }

    public MobileOrder startType(String startType){
        this.startType = startType;
        return this;
    }

    @Override
    protected void onAddressChange(String title, double latitude, double longitude, String adCode, String adName) {
        position = title;

        gisx = longitude;
        gisy = latitude;
    }

    @Override
    public void onChanged(GeneralItemText view, String id, String title, Object extra) {
        if (view.getId() == R.id.generalTextGrid){
            partyGrid = id;
            partyGridName = title;
        }else if (view.getId() == R.id.generalTextEventType){
            orderType = id;
        }
//        else if (view.getId() == R.id.generalTextRiver){
//            riverId = id;
//            riverName = title;
//        }
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPartyGrid() {
        return partyGrid;
    }

    public void setPartyGrid(String partyGrid) {
        this.partyGrid = partyGrid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getSeriousDegree() {
        return seriousDegree;
    }

    public void setSeriousDegree(String seriousDegree) {
        this.seriousDegree = seriousDegree;
    }

    public String getPartyGridName() {
        return partyGridName;
    }

    public void setPartyGridName(String partyGridName) {
        this.partyGridName = partyGridName;
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

    public String getCloseOpinion() {
        return closeOpinion;
    }

    public void setCloseOpinion(String closeOpinion) {
        this.closeOpinion = closeOpinion;
    }

    public String getCloseAnnex() {
        return closeAnnex;
    }

    public void setCloseAnnex(String closeAnnex) {
        this.closeAnnex = closeAnnex;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getAppealType() {
        return appealType;
    }

    public void setAppealType(String appealType) {
        this.appealType = appealType;
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

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(String isDelay) {
        this.isDelay = isDelay;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getRiverId() {
        return riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId;
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getRiverOrderType() {
        return riverOrderType;
    }

    public void setRiverOrderType(String riverOrderType) {
        this.riverOrderType = riverOrderType;
    }

    public String getRiverOrderTypeName() {
        return riverOrderTypeName;
    }

    public void setRiverOrderTypeName(String riverOrderTypeName) {
        this.riverOrderTypeName = riverOrderTypeName;
    }

    public boolean isCommitMode() {
        return commitMode;
    }

    public void setCommitMode(boolean commitMode) {
        this.commitMode = commitMode;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
        onChanged();
    }
}
