package com.szxgm.gusustreet.net.responses;


import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class NetFileWrapper implements DataWrap<String> {

    private String msg;
    private String fileName;
    private String code;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
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

    @Override
    public String getData() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}
