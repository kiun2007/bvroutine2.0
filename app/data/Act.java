/**
 * Copyright (c) 2017-2020 xgmsz.com
 */
package com.ruoyi.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TimeUtils;
import com.ruoyi.xtcz.domain.XtczOrderDelay;
import com.ruoyi.xtcz.domain.XtczOrderInfo;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流Entity
 * @author xgmsz
 * @version 2013-11-03
 */
public class Act implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//历史任务参数
	private String histaskId; 		// 历史任务编号
	private String histaskName; 	// 历史任务名称
	private String histaskDefKey; 	// 历史务定义Key（任务环节标识）

	private String hisprocInsId; 	// 历史流程实例ID
	private String hisprocDefId; 	// 历史流程定义ID


	private String taskId; 		// 任务编号
	private String taskName; 	// 任务名称
	private String taskDefKey; 	// 任务定义Key（任务环节标识）

	private String procInsId; 	// 流程实例ID
	private String procDefId; 	// 流程定义ID
	private String procDefKey; 	// 流程定义Key（流程定义标识）

	private String businessTable;	// 业务绑定Table
	private String businessId;		// 业务绑定ID
	
	private String title; 		// 任务标题

	private String status; 		// 任务状态（todo/claim/finish）

//	private String procExecUrl; 	// 流程执行（办理）RUL
	private String comment; 	// 任务意见
	private String flag; 		// 意见状态
	
	private Task task; 			// 任务对象
//	private ProcessDefinition procDef; 	// 流程定义对象
	private ProcessInstance procIns;	// 流程实例对象
	private HistoricTaskInstance histTask; // 历史任务
	private HistoricActivityInstance histIns;	//历史活动任务

	private String handleName; // 处置操作名称
	private String handleOfficeName; // 处置部门名称
	private String handleOfficeNameQueue; // 处置部门名称队列

	private String assignee; // 任务执行人编号
	private String assigneeName; // 任务执行人名称
	
	private String assigneeNext; // 任务下一个执行人编号
	private String assigneeNameNext; // 任务下一个执行人名称

	private Variable vars; 		// 流程变量
//	private Variable taskVars; 	// 流程任务变量
	
	private Date beginDate;	// 开始查询日期
	private Date endDate;	// 结束查询日期

	private List<Act> list; // 任务列表
	
	private String mark; // 流程视图标记（1:表示入口为流程监控，其他参数:审核入口）


	private XtczOrderInfo xtczOrderInfo; // 工单信息
	private XtczOrderDelay xtczOrderDelay; // 延期申请信息


	public Act() {
		super();
		this.mark = "0";
	}

    public XtczOrderInfo getXtczOrderInfo() {
        return xtczOrderInfo;
    }

    public void setXtczOrderInfo(XtczOrderInfo xtczOrderInfo) {
        this.xtczOrderInfo = xtczOrderInfo;
    }

    public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getTaskId() {
		if (taskId == null && task != null){
			taskId = task.getId();
		}
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		if (taskName == null && task != null){
			taskName = task.getName();
		}
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDefKey() {
		if (taskDefKey == null && task != null ){
			taskDefKey = task.getTaskDefinitionKey();
		}
		return taskDefKey;
	}

	public String getProcDefId() {
		if (procDefId == null && task != null){
			procDefId = task.getProcessDefinitionId();
		}
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcInsId() {
		if (procInsId == null && task != null){
			procInsId = task.getProcessInstanceId();
		}
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	/**
	 * 获取流程定义KEY
	 * @return
	 */
	public String getProcDefKey() {
		if (StringUtils.isBlank(procDefKey) && StringUtils.isNotBlank(procDefId)){
			procDefKey = StringUtils.split(procDefId, ":")[0];
		}
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskCreateDate() {
		if (task != null){
			return task.getCreateTime();
		}
		return null;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEndDate() {
		if (histTask != null){
			return histTask.getEndTime();
		}
		return null;
	}
	
	@JsonIgnore
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

//	@JsonIgnore
//	public ProcessDefinition getProcDef() {
//		return procDef;
//	}
//
//	public void setProcDef(ProcessDefinition procDef) {
//		this.procDef = procDef;
//	}
//
//	public String getProcDefName() {
//		return procDef.getName();
//	}

	@JsonIgnore
	public ProcessInstance getProcIns() {
		return procIns;
	}

	public void setProcIns(ProcessInstance procIns) {
		this.procIns = procIns;
		if (procIns != null && procIns.getBusinessKey() != null){
			String[] ss = procIns.getBusinessKey().split(":");
			setBusinessTable(ss[0]);
			setBusinessId(ss[1]);
		}
	}

//	public String getProcExecUrl() {
//		return procExecUrl;
//	}
//
//	public void setProcExecUrl(String procExecUrl) {
//		this.procExecUrl = procExecUrl;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public HistoricTaskInstance getHistTask() {
		return histTask;
	}

	public void setHistTask(HistoricTaskInstance histTask) {
		this.histTask = histTask;
	}

	@JsonIgnore
	public HistoricActivityInstance getHistIns() {
		return histIns;
	}

	public void setHistIns(HistoricActivityInstance histIns) {
		this.histIns = histIns;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}



	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssignee() {
		if (assignee == null && task != null){
			assignee = task.getAssignee();
		}
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public List<Act> getList() {
		return list;
	}

	public void setList(List<Act> list) {
		this.list = list;
	}

	public Variable getVars() {
		return vars;
	}

	public void setVars(Variable vars) {
		this.vars = vars;
	}
	
	/**
	 * 通过Map设置流程变量值
	 * @param map
	 */
	public void setVars(Map<String, Object> map) {
		this.vars = new Variable(map);
	}

//	public Variable getTaskVars() {
//		return taskVars;
//	}
//
//	public void setTaskVars(Variable taskVars) {
//		this.taskVars = taskVars;
//	}
//
//	/**
//	 * 通过Map设置流程任务变量值
//	 * @param map
//	 */
//	public void setTaskVars(Map<String, Object> map) {
//		this.taskVars = new Variable(map);
//	}


	
	/**
	 * 获取过去的任务历时
	 * @return
	 */
	public String getDurationTime(){
		if (histIns!=null && histIns.getDurationInMillis() != null){
			return TimeUtils.toTimeString(histIns.getDurationInMillis());
		}
		return "";
	}
	
	/**
	 * 是否是一个待办任务
	 * @return
	 */
	public boolean isTodoTask(){
		return "todo".equals(status) || "claim".equals(status);
	}
	
	/**
	 * 是否是已完成任务
	 * @return
	 */
	public boolean isFinishTask(){
		return "finish".equals(status) || StringUtils.isBlank(taskId);
	}


	public String getAssigneeNext() {
		return assigneeNext;
	}

	public void setAssigneeNext(String assigneeNext) {
		this.assigneeNext = assigneeNext;
	}

	public String getAssigneeNameNext() {
		return assigneeNameNext;
	}

	public void setAssigneeNameNext(String assigneeNameNext) {
		this.assigneeNameNext = assigneeNameNext;
	}

	public String getHistaskId() {
		if (histaskId == null && histTask != null){
			histaskId = histTask.getId();
		}
		return histaskId;
	}

	public void setHistaskId(String histaskId) {
		this.histaskId = histaskId;
	}

	public String getHistaskName() {
		if (histaskName == null && histTask != null){
			histaskName = histTask.getName();
		}
		return histaskName;
	}

	public void setHistaskName(String histaskName) {
		this.histaskName = histaskName;
	}

	public String getHistaskDefKey() {
		if (histaskDefKey == null && histTask != null){
			histaskDefKey = histTask.getTaskDefinitionKey();
		}
		return histaskDefKey;
	}

	public void setHistaskDefKey(String histaskDefKey) {
		this.histaskDefKey = histaskDefKey;
	}

	public String getHisprocInsId() {
		if (hisprocInsId == null && histTask != null){
			hisprocInsId = histTask.getProcessInstanceId();
		}
		return hisprocInsId;
	}

	public void setHisprocInsId(String hisprocInsId) {
		this.hisprocInsId = hisprocInsId;
	}

	public String getHisprocDefId() {
		if (hisprocDefId == null && histTask != null){
			hisprocDefId = histTask.getProcessDefinitionId();
		}
		return hisprocDefId;
	}

	public void setHisprocDefId(String hisprocDefId) {
		this.hisprocDefId = hisprocDefId;
	}

	public XtczOrderDelay getXtczOrderDelay() {
		return xtczOrderDelay;
	}

	public void setXtczOrderDelay(XtczOrderDelay xtczOrderDelay) {
		this.xtczOrderDelay = xtczOrderDelay;
	}

	public String getHandleOfficeName() {
		return handleOfficeName;
	}

	public void setHandleOfficeName(String handleOfficeName) {
		this.handleOfficeName = handleOfficeName;
	}

	public String getHandleOfficeNameQueue() {
		return handleOfficeNameQueue;
	}

	public void setHandleOfficeNameQueue(String handleOfficeNameQueue) {
		this.handleOfficeNameQueue = handleOfficeNameQueue;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
}


