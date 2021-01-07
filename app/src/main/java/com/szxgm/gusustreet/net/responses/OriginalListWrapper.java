package com.szxgm.gusustreet.net.responses;

import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class OriginalListWrapper<T> extends ArrayList<T> implements DataWrap<List<T>> {

    @Override
    public List<T> getData() {
        return this;
    }

    @Override
    public String getMsg() {
        return "";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isLogout() {
        return false;
    }
}
