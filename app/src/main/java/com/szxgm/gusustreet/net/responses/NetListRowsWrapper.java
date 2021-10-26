package com.szxgm.gusustreet.net.responses;

import java.util.List;

import kiun.com.bvroutine.interfaces.wrap.DepthWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;

@DepthWrap
public class NetListRowsWrapper<T> implements ListWrap<T> {

    private int total;
    private int code;
    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public List<T> wrapList() {
        return rows;
    }

    @Override
    public int pages() {
        return 0;
    }

    @Override
    public int total() {
        return total;
    }

    @Override
    public String getMsg() {
        return String.valueOf(code);
    }

    @Override
    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public boolean isLogout() {
        return code == 1;
    }
}
