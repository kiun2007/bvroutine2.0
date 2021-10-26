package com.szxgm.gusustreet.net.responses;

import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class NetOtherWrapper<T> implements DataWrap<T> {

    private String code;

    private String message;

    private T data;

    @Override
    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }

    @Override
    public boolean isLogout() {
        return false;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
