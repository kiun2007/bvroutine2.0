//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.net.requests.mobile;

import kiun.com.bvroutine.data.PagerBean;

public class DepartmentReq extends PagerBean {
    private String officeName;
    private String typeId;

    public DepartmentReq(String var1) {
        this.typeId = var1;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setOfficeName(String var1) {
        this.officeName = var1;
    }

    public void setTypeId(String var1) {
        this.typeId = var1;
    }
}
