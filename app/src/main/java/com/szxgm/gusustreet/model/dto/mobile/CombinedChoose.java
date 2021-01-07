package com.szxgm.gusustreet.model.dto.mobile;

public class CombinedChoose {

    private String handleOfficeName;
    private String handleOffice;
    private String leader;

    public CombinedChoose(String handleOfficeName, String handleOffice, String leader) {
        this.handleOfficeName = handleOfficeName;
        this.handleOffice = handleOffice;
        this.leader = leader;
    }

    public String getHandleOfficeName() {
        return handleOfficeName;
    }

    public void setHandleOfficeName(String handleOfficeName) {
        this.handleOfficeName = handleOfficeName;
    }

    public String getHandleOffice() {
        return handleOffice;
    }

    public void setHandleOffice(String handleOffice) {
        this.handleOffice = handleOffice;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return handleOfficeName + ("1".equals(leader) ? "(牵头部门)" : "");
    }
}
