package com.szxgm.gusustreet.model.dto;

import com.szxgm.gusustreet.model.base.GeneralItem;

public class Person implements GeneralItem {

    private String userId;
    private String loginName;
    private String userName;
    private String sex;
    private DeptBean dept;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public DeptBean getDept() {
        return dept;
    }

    public void setDept(DeptBean dept) {
        this.dept = dept;
    }

    @Override
    public String getTitle() {
        return userName;
    }

    @Override
    public String getId() {
        return userId;
    }

    public static class DeptBean{
        private String deptId;
        private String deptName;

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }
    }
}
