package com.szxgm.gusustreet.net.responses;

import java.math.BigDecimal;

import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class OriginalNumber extends BigDecimal implements DataWrap {

    public OriginalNumber() {
        super(0);
    }

    public OriginalNumber(int val) {
        super(val);
    }

    public OriginalNumber(String val) {
        super(val);
    }

    public OriginalNumber(char[] in, int offset, int len) {
        super(in, offset, len);
    }

    @Override
    public Object getData() {
        return this;
    }

    @Override
    public String getMsg() {
        return null;
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
