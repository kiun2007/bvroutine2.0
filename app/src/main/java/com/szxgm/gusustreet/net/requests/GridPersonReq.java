package com.szxgm.gusustreet.net.requests;

import android.content.DialogInterface;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.base.SearchQuery;

import kiun.com.bvroutine.data.PagerBean;

/**
 * 网格人员请求.
 */
public class GridPersonReq extends PagerBean<Object, GridPersonReq> implements SearchQuery {

    int page;
    int size = 15;

    /**
     * 网格编码
     */
    String wgbm;

    int isWgz = 1;

    /**
     * 人员姓名.
     */
    String xm;

    @Override
    public void setPageNum(int pageNum) {
        super.setPageNum(pageNum);
        page = pageNum;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getWgbm() {
        return wgbm;
    }

    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
    }

    public int getIsWgz() {
        return isWgz;
    }

    public void setIsWgz(int isWgz) {
        this.isWgz = isWgz;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    @Override
    public void setSearchValue(String value) {
        this.xm = value;
    }

    @Override
    public String getSearchValue() {
        return xm;
    }

    @Override
    public String searchHint() {
        return "输入姓名搜索人员";
    }

    @Override
    public int filterLayout() {
        return R.layout.dialog_filter_grid;
    }

    @Override
    public void onChanged() {
        super.onChanged(false);
    }

    public void onChanged(DialogInterface dialogInterface){
        onChanged();
        dialogInterface.dismiss();
    }
}
