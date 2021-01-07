//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.net.requests.mobile;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.TaskDisposalType;
import com.szxgm.gusustreet.model.base.TaskStatus;
import com.szxgm.gusustreet.model.dto.mobile.AreaRiverOffice;
import com.szxgm.gusustreet.model.dto.mobile.AreaRiverStreet;
import com.szxgm.gusustreet.model.query.OrderInfoReq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.GeneralItemTextListener;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;
import kiun.com.bvroutine.views.text.GeneralItemText;

public class TaskDisposalReq extends EventBean implements GeneralItemTextListener {

    @JSONField(
            serialize = false
    )
    private String appealType;
    private String businessId;
    @Verifys({@Verify(
            desc = "请输入处置说明",
            value = NotNull.class
    )})
    private String comment;
    private String difficultSheet;
    private String flag = "1";
    @Verifys({@Verify(
            desc = "选择处置部门",
            pass = "that.passOffice()",
            value = NotNull.class
    )})
    private String officeId;
    @Verifys({@Verify(
            desc = "选择协调处置部门",
            pass = "that.passOfficeRefines()",
            value = NotNull.class
    )})
    private String officeRefines;
    private String procInsId;
    private TaskStatus status;
    private TaskDisposalType taskDefKey;
    private String taskId;
    private Integer timeLimit;
    private String reportOrderSource = "0";
    //是否为疑难工单
    private boolean difficult;
    //是否超出退回时间
    private boolean isTimeOut;

    //-----以下是河长制相关字段
    //处置河段ID
    private String riverId;
    @Verifys({@Verify(
            desc = "选择处置河流",
            pass = "that.passRiver()",
            value = NotNull.class
    )})
    //河段名称
    private String riverName;
    //河长制处置部门ID
    private String riverOfficeId;
    @Verifys({@Verify(
            desc = "选择指派部门",
            pass = "that.passRiver()",
            value = NotNull.class
    )})
    //河长制处置部门
    private String riverOfficeName;
    //河长制处置部门人员
    private String riverOfficePerson;
    //河长制事件类型
    private String riverOrderType;
    //是否选取街道处置.
    private String isUnderclass = "0";
    //处置街道ID
    private String riverStreetId;
    //处置街道人员
    private String riverStreetPerson;

    public TaskDisposalReq(OrderInfoReq var1) {
        this.taskId = var1.getTaskId();
        this.taskDefKey = TaskDisposalType.valueOf(var1.getTaskDefKey());
        this.procInsId = var1.getProcInsId();
        this.status = TaskStatus.valueOf(var1.getStatus());
        this.businessId = var1.getBusinessId();
    }

    public TaskDisposalReq flag(String var1) {
        this.flag = var1;
        return this;
    }

    public String getAppealType() {
        return this.appealType;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public String getComment() {
        return this.comment;
    }

    public String getDifficultSheet() {
        return this.difficultSheet;
    }

    public String getFlag() {
        return this.flag;
    }

    public String getOfficeId() {
        return this.officeId;
    }

    public String getOfficeRefines() {
        return this.officeRefines;
    }

    public String getProcInsId() {
        return this.procInsId;
    }

    public String getRiverId() {
        return this.riverId;
    }

    public String getRiverName() {
        return this.riverName;
    }

    public String getRiverOfficeId() {
        return this.riverOfficeId;
    }

    public String getRiverOfficeName() {
        return this.riverOfficeName;
    }

    public String getRiverOrderType() {
        return this.riverOrderType;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public TaskDisposalType getTaskDefKey() {
        return this.taskDefKey;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public Integer getTimeLimit() {
        return this.timeLimit;
    }

    public void onChanged(GeneralItemText view, String id, String title, Object extra) {
        if (view.getId() == R.id.generalTextRiver) {
            this.riverId = id;
            this.riverName = title;
            onChanged();
        } else if (view.getId() == R.id.generalTextOffice){
            this.riverOfficeId = id;
            this.riverOfficeName = title;
            if (extra instanceof AreaRiverOffice){
                AreaRiverOffice office = (AreaRiverOffice) extra;
                isUnderclass = office.getDisposeOffice().getIsUnderclass();
                riverOfficePerson = office.getDisposeOffice().getOfficePerson();

                if ("1".equals(isUnderclass)){
                    riverStreetId = office.getStreets().get(0).getStreetId();
                    riverStreetPerson = office.getStreets().get(0).getStreetPerson();
                }
            }
        } else if (view.getId() == R.id.generalTextEventType) {
            this.officeId = id;
        }
    }

    public boolean passOffice() {
        if (taskDefKey == TaskDisposalType.subCenter || taskDefKey == TaskDisposalType.streetLeader) {
            return !"1".equals(flag);
        }
        return true;
    }

    public boolean passOfficeRefines() {
        return taskDefKey != TaskDisposalType.stationLeader;
    }

    public boolean passRiver() {
        return taskDefKey != TaskDisposalType.subCenter || !"3".equals(this.flag) || !"30".equals(reportOrderSource);
    }

    public void setAppealType(String var1) {
        this.appealType = var1;
    }

    public void setBusinessId(String var1) {
        this.businessId = var1;
    }

    public void setComment(String var1) {
        this.comment = var1;
    }

    public void setDifficultSheet(String var1) {
        this.difficultSheet = var1;
    }

    public void setFlag(String flag) {

        if (this.taskDefKey == TaskDisposalType.subCenter){
            if ("3A".equals(flag)){
                //上报区联动
                reportOrderSource = "20";
            }else if ("3B".equals(flag)){
                reportOrderSource = "30";
            }
            Pattern pattern = Pattern.compile("([0-9]).+?");
            Matcher matcher = pattern.matcher(flag);

            if(matcher.find()){
                flag = matcher.group(1);
            }
        }
        this.flag = flag;
        this.onChanged();
    }

    public void setOfficeId(String var1) {
        this.officeId = var1;
    }

    public void setOfficeRefines(String var1) {
        this.officeRefines = var1;
    }

    public void setProcInsId(String var1) {
        this.procInsId = var1;
    }

    public void setRiverId(String var1) {
        this.riverId = var1;
    }

    public void setRiverName(String var1) {
        this.riverName = var1;
    }

    public void setRiverOfficeId(String var1) {
        this.riverOfficeId = var1;
    }

    public void setRiverOfficeName(String var1) {
        this.riverOfficeName = var1;
    }

    public void setRiverOrderType(String var1) {
        this.riverOrderType = var1;
        onChanged();
    }

    @JSONField(serialize = false)
    public boolean isSelectComplete(){
        return riverId != null && riverOrderType != null;
    }

    public void setStatus(TaskStatus var1) {
        this.status = var1;
    }

    public void setTaskDefKey(TaskDisposalType var1) {
        this.taskDefKey = var1;
    }

    public void setTaskId(String var1) {
        this.taskId = var1;
    }

    public void setTimeLimit(Integer var1) {
        this.timeLimit = var1;
    }

    public String getReportOrderSource() {
        return reportOrderSource;
    }

    public void setReportOrderSource(String reportOrderSource) {
        this.reportOrderSource = reportOrderSource;
    }

    public String getIsUnderclass() {
        return isUnderclass;
    }

    public void setIsUnderclass(String isUnderclass) {
        this.isUnderclass = isUnderclass;
    }

    public String getRiverOfficePerson() {
        return riverOfficePerson;
    }

    public void setRiverOfficePerson(String riverOfficePerson) {
        this.riverOfficePerson = riverOfficePerson;
    }

    public String getRiverStreetId() {
        return riverStreetId;
    }

    public void setRiverStreetId(String riverStreetId) {
        this.riverStreetId = riverStreetId;
    }

    public String getRiverStreetPerson() {
        return riverStreetPerson;
    }

    public void setRiverStreetPerson(String riverStreetPerson) {
        this.riverStreetPerson = riverStreetPerson;
    }

    public boolean isDifficult() {
        return difficult;
    }

    public void setDifficult(boolean difficult) {
        this.difficult = difficult;
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }

    public void setTimeOut(boolean timeOut) {
        isTimeOut = timeOut;
    }
}
