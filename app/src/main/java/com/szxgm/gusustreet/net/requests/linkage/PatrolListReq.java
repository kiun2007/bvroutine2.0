//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.net.requests.linkage;

import kiun.com.bvroutine.data.PagerBean;

public class PatrolListReq extends PagerBean<Object, PatrolListReq> {
    private String overTimeFlag = "2";
    private String patrolPerson = "";
    private String patrolType = "2";
    private String wgbm = "320508002012501";

    public PatrolListReq() {
    }

    public String getOverTimeFlag() {
        return this.overTimeFlag;
    }

    public String getPatrolPerson() {
        return this.patrolPerson;
    }

    public String getPatrolType() {
        return this.patrolType;
    }

    public String getWgbm() {
        return this.wgbm;
    }

    public void setOverTimeFlag(String var1) {
        this.overTimeFlag = var1;
    }

    public void setPatrolPerson(String var1) {
        this.patrolPerson = var1;
    }

    public void setPatrolType(String var1) {
        this.patrolType = var1;
    }

    public void setWgbm(String var1) {
        this.wgbm = var1;
    }
}
