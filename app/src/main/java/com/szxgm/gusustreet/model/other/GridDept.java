package com.szxgm.gusustreet.model.other;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.net.services.LinkageService;
import java.util.List;
import kiun.com.bvroutine.interfaces.TreeItem;
import kiun.com.bvroutine.utils.RetrofitUtil;

public class GridDept implements TreeItem {

    private String id;
    private String createdDate;
    private String updatedDate;
    private int delFlag;
    private String creatorId;
    private String updaterId;
    private String deptCode;
    private String deptName;
    private int sort;
    private String parentDeptId;
    private int enable;
    private String wgbm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getWgbm() {
        return wgbm;
    }

    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
    }

    @Override
    public int itemLayoutId() {
        return R.layout.item_grid_tree;
    }

    @Override
    public boolean withChild(int parentLevel) {
        return parentLevel < 2;
    }

    @Override
    public List<?> itemList() throws Exception {
        return RetrofitUtil.callServiceData(LinkageService.class, s -> s.queryByParentDeptId(id));
    }
}
