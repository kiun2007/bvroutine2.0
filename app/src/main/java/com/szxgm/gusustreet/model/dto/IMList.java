package com.szxgm.gusustreet.model.dto;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.szxgm.gusustreet.utils.IMUtil;

public class IMList extends BaseIndexPinyinBean {

    private String imUser;

    private String userId;

    private String deptId;

    private String deptName;

    private String userName;

    private String sex;

    private String avatar;

    public String getImUser() {
        return imUser;
    }

    public void setImUser(String imUser) {
        this.imUser = imUser == null ? null : imUser.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String getTarget() {
        return userName;
    }
}
