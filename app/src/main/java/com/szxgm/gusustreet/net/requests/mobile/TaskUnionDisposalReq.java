//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.net.requests.mobile;

import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

public class TaskUnionDisposalReq extends QueryBean {
    private String content;
    private String id;
    @Verifys({@Verify(
            desc = "请输入处置说明",
            value = NotNull.class
    )})
    private String workStatement;

    public TaskUnionDisposalReq(String var1, String var2) {
        this.id = var1;
        this.content = var2;
    }

    public String getContent() {
        return this.content;
    }

    public String getId() {
        return this.id;
    }

    public String getWorkStatement() {
        return this.workStatement;
    }

    public void setContent(String var1) {
        this.content = var1;
    }

    public void setId(String var1) {
        this.id = var1;
    }

    public void setWorkStatement(String var1) {
        this.workStatement = var1;
    }
}
