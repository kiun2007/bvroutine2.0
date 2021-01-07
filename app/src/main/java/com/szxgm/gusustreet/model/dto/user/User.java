//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.szxgm.gusustreet.model.dto.user;

import android.util.Base64;

import java.util.List;
import kiun.com.bvroutine.interfaces.JSON;
import kiun.com.bvroutine.interfaces.binding.NetTextBean;

public class User implements JSON, NetTextBean {
    public static final String TAG = "User";
    private String avatar;
    private String deptId;
    private String deptName;
    private String email;
    private String imPwd;
    private String imUser;
    private String loginName;
    private String phonenumber;
    private List<Roles> roles;
    private String salt;
    private String shortNumber;
    private String status;
    private String userId;
    private String userName;

    public User() {
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getImPwd() {
        return this.getImPwd(true);
    }

    public String getImPwd(boolean var1) {
        return this.shortNumber;
    }

    public String getImUser() {
        return this.getImUser(true);
    }

    public String getImUser(boolean var1) {
        return this.shortNumber;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public String getNetText() {
        return this.userName;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public List<Roles> getRoles() {
        return this.roles;
    }

    public String getSalt() {
        return this.salt;
    }

    public String getShortNumber() {
        return this.shortNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setAvatar(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.avatar = var1;
    }

    public void setDeptId(String var1) {
        this.deptId = var1;
    }

    public void setDeptName(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.deptName = var1;
    }

    public void setEmail(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.email = var1;
    }

    public void setImPwd(String var1) {
        this.imPwd = var1;
    }

    public void setImUser(String var1) {
        this.imUser = var1;
    }

    public void setLoginName(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.loginName = var1;
    }

    public void setPhonenumber(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.phonenumber = var1;
    }

    public void setRoles(List<Roles> var1) {
        this.roles = var1;
    }

    public void setSalt(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.salt = var1;
    }

    public void setShortNumber(String var1) {
        this.shortNumber = var1;
    }

    public void setStatus(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.status = var1;
    }

    public void setUserId(String var1) {
        this.userId = var1;
    }

    public void setUserName(String var1) {
        if (var1 == null) {
            var1 = null;
        } else {
            var1 = var1.trim();
        }

        this.userName = var1;
    }

    public String createToken(String pwd){

        String tokenStr = loginName + ":" + pwd;
        return Base64.encodeToString(tokenStr.getBytes(), Base64.NO_WRAP);
    }
}
