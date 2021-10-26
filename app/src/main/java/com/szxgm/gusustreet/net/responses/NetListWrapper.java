package com.szxgm.gusustreet.net.responses;

import java.util.List;

import kiun.com.bvroutine.interfaces.wrap.DepthWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;

@DepthWrap
public class NetListWrapper<T> implements ListWrap<T> {

    private DataList<T> data;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return "0".equals(code);
    }

    @Override
    public boolean isLogout() {
        return "1".equals(code);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataList getData() {
        return data;
    }

    public void setData(DataList data) {
        this.data = data;
    }

    @Override
    public List<T> wrapList() {
        if (data == null){
            return null;
        }
        return data.rows;
    }

    @Override
    public int pages() {
        return 0;
    }

    @Override
    public int total() {
        return data.total;
    }

    public static class DataList<D>{

        private int total;

        private List<D> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setRows(List<D> rows) {
            this.rows = rows;
        }

        public List<D> getRows() {
            return rows;
        }
    }
}
