package com.szxgm.gusustreet.net.requests;

import com.szxgm.gusustreet.model.base.SearchQuery;
import kiun.com.bvroutine.data.PagerBean;

public class MonitorListReq extends PagerBean implements SearchQuery {

    private String searchName;

    @Override
    public void setSearchValue(String value) {
        searchName = value;
        onChanged(false);
    }

    @Override
    public String getSearchValue() {
        return searchName;
    }

    @Override
    public String searchHint() {
        return "搜索监控名称";
    }

    @Override
    public int filterLayout() {
        return -1;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}
