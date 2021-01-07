//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.net.requests.linkage;

import kiun.com.bvroutine.data.PagerBean;

public class VisitListReq extends PagerBean<Object, VisitListReq> {
    private String finishTimeEnd = "";
    private String finishTimeStart = "";
    private int missionType = 1;
    private int overTimeFlag = 0;
    private int status = 1;
    private String togetherVisitFlag = "";
    private String visitName = "";
    private String visitObject = "";

    //TODO 网格编码需要动态输入.
    private String wgbm = "320508002012501";

    public VisitListReq() {
    }

    public String getFinishTimeEnd() {
        return this.finishTimeEnd;
    }

    public String getFinishTimeStart() {
        return this.finishTimeStart;
    }

    public int getMissionType() {
        return this.missionType;
    }

    public int getOverTimeFlag() {
        return this.overTimeFlag;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTogetherVisitFlag() {
        return this.togetherVisitFlag;
    }

    public String getVisitName() {
        return this.visitName;
    }

    public String getVisitObject() {
        return this.visitObject;
    }

    public String getWgbm() {
        return this.wgbm;
    }

    public void setFinishTimeEnd(String var1) {
        this.finishTimeEnd = var1;
    }

    public void setFinishTimeStart(String var1) {
        this.finishTimeStart = var1;
    }

    public void setMissionType(int var1) {
        this.missionType = var1;
    }

    public void setOverTimeFlag(int var1) {
        this.overTimeFlag = var1;
    }

    public void setStatus(int var1) {
        this.status = var1;
    }

    public void setTogetherVisitFlag(String var1) {
        this.togetherVisitFlag = var1;
    }

    public void setVisitName(String var1) {
        this.visitName = var1;
    }

    public void setVisitObject(String var1) {
        this.visitObject = var1;
    }

    public void setWgbm(String var1) {
        this.wgbm = var1;
    }
}
