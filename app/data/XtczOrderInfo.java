package com.ruoyi.xtcz.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.activiti.entity.Act;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 工单表 xtcz_order_info
 *
 * @author ruoyi
 * @date 2020-04-08
 */
public class XtczOrderInfo extends BaseEntity implements Cloneable
{
	private static final long serialVersionUID = 1L;

	/** 事件标题 */
	@NotBlank(message = "事件标题不能为空")
	private String title;
	//诉求类型
	@NotBlank(message = "诉求类型不能为空")
	private String appealType;
	/** 事件类型 */
	@NotBlank(message = "事件类型不能为空")
	private String orderType;
	/** 严重程度 */
	@NotBlank(message = "严重程度不能为空")
	private String seriousDegree;
	/** 所属网格 */
	@NotBlank(message = "所属网格不能为空")
	private String partyGrid;
	/** 所属网格名称 */
//    @NotBlank(message = "所属网格名称不能为空")
	private String partyGridName;
	/** 问题描述 */
	@NotBlank(message = "问题描述不能为空")
	private String description;
	/** X坐标 */
	@NotBlank(message = "坐标不能为空")
	private String gisx;
	/** Y坐标 */
	@NotBlank(message = "坐标不能为空")
	private String gisy;
	/** 事发位置说明 */
	@NotBlank(message = "事发位置不能为空")
	private String position;
	/** 事件提交类型 */
	@NotBlank(message = "事件提交类型不能为空")
	private String startType;
	/** 上报人 */
	@NotBlank(message = "上报人员不能为空")
	private String reportPerson;
	/** 上报部门 */
	@NotBlank(message = "上报部门不能为空")
	private String reportOffice;

	/** 事件主键id */
	private String orderId;
	/** 事件编号 */
	private String orderCode;
	/** 事件来源 */
	private String orderSource;
	/** 所属街道 */
	private String street;
	/** 所属社区 */
	private String community;
	//上报人姓名
	private String reportPersonName;
	/** 上报时间 */
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date reportDate;
	/** 结案人 */
	private String closePerson;
	/** 结案人id */
	private String closePersonId;
	/** 结案日期 */
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date closeDate;
	/** 结案意见 */
	private String closeOpinion;
	/** 事件状态 */
	private String orderState;
	/** 删除标记（0未删除，2已删除） */
	private String delFlag;
	/**是否疑难工单（0否，2是）*/
	private String difficult;
	/**疑难说明*/
	private String difficultRemarks;
	/**紧急程度说明*/
	private String seriousDegreeName;
	/**工单状态说明*/
	private String orderStateName;
	/** 事件类型名称 */
	private String orderTypeName;
	/**流程实例ID*/
	private String procInsId;
	/**是否申请延期*/
	private String isDelay;
	/**对接ID*/
	private String duijieId;
	//外部工单所属街道
	private String outStreetName;
	//处置部门id
	private String officeId;
	//疑难事项上报单
	private String difficultSheet;

	//河道名称
	private String riverName;
	//河道等级
	private String riverLevel;
	//图片
	private List<String> images;
	private String imageData;

	//工单来源-联动
	private String infoSource;
	//受理方式-联动
	private String acceptWay;
	//事、部件-联动
	private String infoType;
	//市交办意见-联动
	private String sasSignOpinion;
	//诉求人性别-联动
	private String appealerGender;
	//诉求人年龄-联动
	private String appealerAge;
	//诉求电话-联动
	private String appealerPhone;
	//联系电话1-联动
	private String contactPhone1;
	//联系电话2-联动
	private String contactPhone2;
	//诉求类型名称-联动
	private String appealTypeName;
	//诉求目的-联动
	private String appealGoal;
	//处理时限-联动
	private String timeLimit;
	//派发时间-联动
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date sendDate;
	//截止时间-联动
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date shouldEndDate;
	//12345截止时间-联动
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date shouldEndDate12345;
	//拒绝反馈时间-联动
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date refuseDate;
	//回访类型-联动
	private String rtnVisitType;
	//归口类型-联动
	private String ascriptionType;
	//归口类型中文-联动
	private String ascription;
	//备注-联动
	private String memo;
	//回访意见-联动
	private String rtnVisitContent;
	//满意度-联动
	private String satisfactionLevel;
	//结案类型-联动
	private String closeType;
	//街道编码-联动
	private String deptCode;
	//区联动是否受理（0未受理，1已受理）
	private String isAccept;
	//是否自办结工单
	private String isSelfFinish;
	//交办意见
	private String comment;

	public String getAscription() {
		return ascription;
	}

	public void setAscription(String ascription) {
		this.ascription = ascription;
	}

	public String getReportPersonName() {
		return reportPersonName;
	}

	public void setReportPersonName(String reportPersonName) {
		this.reportPersonName = reportPersonName;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getDuijieId() {
		return duijieId;
	}

	public void setDuijieId(String duijieId) {
		this.duijieId = duijieId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getOrderId()
	{
		return orderId;
	}
	public void setOrderCode(String orderCode)
	{
		this.orderCode = orderCode;
	}

	public String getOrderCode()
	{
		return orderCode;
	}
	public void setOrderSource(String orderSource)
	{
		this.orderSource = orderSource;
	}

	public String getOrderSource()
	{
		return orderSource;
	}
	public void setSeriousDegree(String seriousDegree)
	{
		this.seriousDegree = seriousDegree;
	}

	public String getSeriousDegree()
	{
		return seriousDegree;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getStreet()
	{
		return street;
	}
	public void setCommunity(String community)
	{
		this.community = community;
	}

	public String getCommunity()
	{
		return community;
	}

	public String getPartyGrid() {
		return partyGrid;
	}

	public void setPartyGrid(String partyGrid) {
		this.partyGrid = partyGrid;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getPosition()
	{
		return position;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
	public void setReportPerson(String reportPerson)
	{
		this.reportPerson = reportPerson;
	}

	public String getReportPerson()
	{
		return reportPerson;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getReportDate()
	{
		return reportDate;
	}
	public void setGisx(String gisx)
	{
		this.gisx = gisx;
	}

	public String getGisx()
	{
		return gisx;
	}
	public void setGisy(String gisy)
	{
		this.gisy = gisy;
	}

	public String getGisy()
	{
		return gisy;
	}
	public void setClosePerson(String closePerson)
	{
		this.closePerson = closePerson;
	}

	public String getClosePerson()
	{
		return closePerson;
	}
	public void setClosePersonId(String closePersonId)
	{
		this.closePersonId = closePersonId;
	}

	public String getClosePersonId()
	{
		return closePersonId;
	}
	public void setCloseDate(Date closeDate)
	{
		this.closeDate = closeDate;
	}

	public Date getCloseDate()
	{
		return closeDate;
	}
	public void setCloseOpinion(String closeOpinion)
	{
		this.closeOpinion = closeOpinion;
	}

	public String getCloseOpinion()
	{
		return closeOpinion;
	}
	public void setOrderState(String orderState)
	{
		this.orderState = orderState;
	}

	public String getOrderState()
	{
		return orderState;
	}
	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public String getOrderType()
	{
		return orderType;
	}
	public void setDelFlag(String delFlag)
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag()
	{
		return delFlag;
	}

	public String getPartyGridName() {
		return partyGridName;
	}

	public void setPartyGridName(String partyGridName) {
		this.partyGridName = partyGridName;
	}

	public String getDifficult() {
		return difficult;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public String getDifficultRemarks() {
		return difficultRemarks;
	}

	public void setDifficultRemarks(String difficultRemarks) {
		this.difficultRemarks = difficultRemarks;
	}

	public String getSeriousDegreeName() {
		return seriousDegreeName;
	}

	public void setSeriousDegreeName(String seriousDegreeName) {
		this.seriousDegreeName = seriousDegreeName;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
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

	public String getOutStreetName() {
		return outStreetName;
	}

	public void setOutStreetName(String outStreetName) {
		this.outStreetName = outStreetName;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
		if (images != null && images.size() > 0){
			this.imageData = JSON.toJSONString(images);
		}
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
		if (StringUtils.isNotBlank(imageData)){
			this.images = JSON.parseArray(imageData,String.class);
		}
	}

	public String getRiverName() {
		return riverName;
	}

	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}

	public String getRiverLevel() {
		return riverLevel;
	}

	public void setRiverLevel(String riverLevel) {
		this.riverLevel = riverLevel;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getAcceptWay() {
		return acceptWay;
	}

	public void setAcceptWay(String acceptWay) {
		this.acceptWay = acceptWay;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getSasSignOpinion() {
		return sasSignOpinion;
	}

	public void setSasSignOpinion(String sasSignOpinion) {
		this.sasSignOpinion = sasSignOpinion;
	}

	public String getAppealerGender() {
		return appealerGender;
	}

	public void setAppealerGender(String appealerGender) {
		this.appealerGender = appealerGender;
	}

	public String getAppealerAge() {
		return appealerAge;
	}

	public void setAppealerAge(String appealerAge) {
		this.appealerAge = appealerAge;
	}

	public String getAppealerPhone() {
		return appealerPhone;
	}

	public void setAppealerPhone(String appealerPhone) {
		this.appealerPhone = appealerPhone;
	}

	public String getContactPhone1() {
		return contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}

	public String getContactPhone2() {
		return contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	public String getAppealType() {
		return appealType;
	}

	public void setAppealType(String appealType) {
		this.appealType = appealType;
	}

	public String getAppealGoal() {
		return appealGoal;
	}

	public void setAppealGoal(String appealGoal) {
		this.appealGoal = appealGoal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getShouldEndDate() {
		return shouldEndDate;
	}

	public void setShouldEndDate(Date shouldEndDate) {
		this.shouldEndDate = shouldEndDate;
	}

	public Date getShouldEndDate12345() {
		return shouldEndDate12345;
	}

	public void setShouldEndDate12345(Date shouldEndDate12345) {
		this.shouldEndDate12345 = shouldEndDate12345;
	}

	public Date getRefuseDate() {
		return refuseDate;
	}

	public void setRefuseDate(Date refuseDate) {
		this.refuseDate = refuseDate;
	}



	public String getAscriptionType() {
		return ascriptionType;
	}

	public void setAscriptionType(String ascriptionType) {
		this.ascriptionType = ascriptionType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRtnVisitType() {
		return rtnVisitType;
	}

	public void setRtnVisitType(String rtnVisitType) {
		this.rtnVisitType = rtnVisitType;
	}

	public String getRtnVisitContent() {
		return rtnVisitContent;
	}

	public void setRtnVisitContent(String rtnVisitContent) {
		this.rtnVisitContent = rtnVisitContent;
	}

	public String getSatisfactionLevel() {
		return satisfactionLevel;
	}

	public void setSatisfactionLevel(String satisfactionLevel) {
		this.satisfactionLevel = satisfactionLevel;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getStartType() {
		return startType;
	}

	public void setStartType(String startType) {
		this.startType = startType;
	}

	public String getDifficultSheet() {
		return difficultSheet;
	}

	public void setDifficultSheet(String difficultSheet) {
		this.difficultSheet = difficultSheet;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}

	public String getIsSelfFinish() {
		return isSelfFinish;
	}

	public void setIsSelfFinish(String isSelfFinish) {
		this.isSelfFinish = isSelfFinish;
	}

	public String getAppealTypeName() {
		return appealTypeName;
	}

	public void setAppealTypeName(String appealTypeName) {
		this.appealTypeName = appealTypeName;
	}

	public String getReportOffice() {
		return reportOffice;
	}

	public void setReportOffice(String reportOffice) {
		this.reportOffice = reportOffice;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("orderId", getOrderId())
				.append("orderCode", getOrderCode())
				.append("orderSource", getOrderSource())
				.append("seriousDegree", getSeriousDegree())
				.append("street", getStreet())
				.append("community", getCommunity())
				.append("partyGrid", getPartyGrid())
				.append("position", getPosition())
				.append("description", getDescription())
				.append("reportPerson", getReportPerson())
				.append("reportDate", getReportDate())
				.append("gisx", getGisx())
				.append("gisy", getGisy())
				.append("closePerson", getClosePerson())
				.append("closePersonId", getClosePersonId())
				.append("closeDate", getCloseDate())
				.append("closeOpinion", getCloseOpinion())
				.append("orderState", getOrderState())
				.append("orderType", getOrderType())
				.append("delFlag", getDelFlag())
				.toString();
	}

	@Override
	public XtczOrderInfo clone() {
		XtczOrderInfo info = null;
		try {
			info = (XtczOrderInfo) super.clone();
			if (getParams() != null) {
				info.setParams(new HashMap<>(getParams()));
			}
		} catch (CloneNotSupportedException ignored) {
			System.out.println(ignored.getMessage());
		}
		return info;
	}
}
