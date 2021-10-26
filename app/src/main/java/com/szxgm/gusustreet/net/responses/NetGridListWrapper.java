package com.szxgm.gusustreet.net.responses;

import android.content.Intent;

import java.util.List;

import kiun.com.bvroutine.interfaces.wrap.ListWrap;

public class NetGridListWrapper<T> implements ListWrap<T> {

    private String code;
    private String msg;
    private Integer count;
    private Integer totalSize;
    private List<T> data;
    private List<T> datas;

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
        return false;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public List<T> wrapList() {
        return data != null ? data : datas;
    }

    @Override
    public int pages() {
        return 0;
    }

    @Override
    public int total() {
        if (count != null){
            return count;
        }
        if (totalSize != null){
            return totalSize;
        }
        return 0;
    }
}
