package com.szxgm.gusustreet.model.dto.mobile;

/**
 * 各个部门处置的情况
 */
public class PersonDisposal {

    private String handleOffice;
    private String handleOfficeName;
    private String handleContent;
    private String handlePersonName;
    private String finishTime;
    private String leader;
    private String remark;
    private String workStatement;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHandleOffice() {
        return handleOffice;
    }

    public void setHandleOffice(String handleOffice) {
        this.handleOffice = handleOffice;
    }

    public String getHandleOfficeName() {
        return handleOfficeName;
    }

    public void setHandleOfficeName(String handleOfficeName) {
        this.handleOfficeName = handleOfficeName;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public String getHandlePersonName() {
        return handlePersonName;
    }

    public void setHandlePersonName(String handlePersonName) {
        this.handlePersonName = handlePersonName;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getWorkStatement() {
        return workStatement;
    }

    public void setWorkStatement(String workStatement) {
        this.workStatement = workStatement;
    }
}
