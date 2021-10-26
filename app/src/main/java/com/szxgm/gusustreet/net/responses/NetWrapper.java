package com.szxgm.gusustreet.net.responses;

import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class NetWrapper<T> implements DataWrap<T> {

    private T data;

    private String code;

    private String msg;

    @Override
    public T getData() {
        return data;
    }

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

    public void setData(T data) {
        this.data = data;
    }
}
