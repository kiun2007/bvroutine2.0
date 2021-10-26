package com.szxgm.gusustreet.model.dto.river;

import com.szxgm.gusustreet.model.base.AddressChooseBean;
import com.szxgm.gusustreet.model.dto.OrderType;

import java.util.Date;

import kiun.com.bvroutine.interfaces.verify.Verifys;

public class RiverOrder extends AddressChooseBean {

    /**
     * 工单表主键
     */
    private String id;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除标记（0未删除，1已删除）
     */
    private String delFalg;

    /**
     * 严重等级
     */
    private String urgencyLevel;

    /**
     * 事件描述
     */
    private String describe;

    /**
     * 事发河道
     */
    private String incidentRiver;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 事件来源
     */
    private String eventsSource;

    /**
     * 工单状态
     */
    private String orderState;

    /**
     * 河长工作联系单
     */
    private String contactSheet;

    /**
     * 标题
     */
    private String title;

    /**
     * 事件类型
     */
    private String orderType;

    /**
     * 经度
     */
    private String xcoor;

    /**
     * 纬度
     */
    private String ycoor;

    /**
     * 事件图片
     */
    private String orderImg;

    /**
     * 批示人
     */
    private String approvalPerson;

    /**
     * 投诉id
     */
    private String complainId;

    /**
     * 详细地址
     */
    private String reportAddress;

    /**
     * 列表图片
     */
    private String image;

    /**
     * 事件复审确认说明
     */
    private String auditRemark;

    /**
     * 巡查ID(不为空即河长上报的问题)
     */
    private String patrolId;

    private String riverName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getDelFalg() {
        return delFalg;
    }

    public void setDelFalg(String delFalg) {
        this.delFalg = delFalg == null ? null : delFalg.trim();
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel == null ? null : urgencyLevel.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getIncidentRiver() {
        return incidentRiver;
    }

    public void setIncidentRiver(String incidentRiver) {
        this.incidentRiver = incidentRiver == null ? null : incidentRiver.trim();
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getEventsSource() {
        return eventsSource;
    }

    public void setEventsSource(String eventsSource) {
        this.eventsSource = eventsSource == null ? null : eventsSource.trim();
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState == null ? null : orderState.trim();
    }

    public String getContactSheet() {
        return contactSheet;
    }

    public void setContactSheet(String contactSheet) {
        this.contactSheet = contactSheet == null ? null : contactSheet.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Object getOrderType() {
        return orderType;
    }

    public void setOrderType(Object orderType) {
        if (orderType instanceof String){
            this.orderType = (String) orderType;
        }else if (orderType instanceof OrderType){
            this.orderType =  ((OrderType) orderType).getId();
            this.title = ((OrderType) orderType).getOrderType();
        }
        onChanged();
    }

    public String getXcoor() {
        return xcoor;
    }

    public void setXcoor(String xcoor) {
        this.xcoor = xcoor == null ? null : xcoor.trim();
    }

    public String getYcoor() {
        return ycoor;
    }

    public void setYcoor(String ycoor) {
        this.ycoor = ycoor == null ? null : ycoor.trim();
    }

    public String getOrderImg() {
        return orderImg;
    }

    public void setOrderImg(String orderImg) {
        this.orderImg = orderImg == null ? null : orderImg.trim();
    }

    public String getApprovalPerson() {
        return approvalPerson;
    }

    public void setApprovalPerson(String approvalPerson) {
        this.approvalPerson = approvalPerson == null ? null : approvalPerson.trim();
    }

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId == null ? null : complainId.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    public String getPatrolId() {
        return patrolId;
    }

    public void setPatrolId(String patrolId) {
        this.patrolId = patrolId == null ? null : patrolId.trim();
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getReportAddress() {
        return reportAddress;
    }

    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    @Override
    protected void onAddressChange(String title, double latitude, double longitude, String adCode, String adName) {

    }
}
