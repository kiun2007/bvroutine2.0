package com.szxgm.gusustreet.model.query;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.LengthLimit;
import kiun.com.bvroutine.data.verify.NeLimit;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

@Verifys({
        @Verify(value = NotNull.class, desc = "密码不能为空"),
        @Verify(value = LengthLimit.class,extra = "[6,14]", desc = "密码长度应该为6-14位")
})
public class Password extends EventBean {

    /**
     * 旧密码.
     */
    private String oldPassword;

    /**
     * 修改密码
     */
    private String newPassword;

    /**
     * 再次输入密码.
     */
    @Verifys({
            @Verify(value = NeLimit.class, extra = "that.newPassword", desc = "两次密码输入不一致")
    })
    private String againPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getAgainPassword() {
        return againPassword;
    }

    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }
}