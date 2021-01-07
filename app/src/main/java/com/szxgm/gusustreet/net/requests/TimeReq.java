package com.szxgm.gusustreet.net.requests;

public class TimeReq {

    public TimeReq(){
    }

    public TimeReq(String begin, String end){
        this.begin = begin;
        this.end = end;
    }

    private String begin;

    private String end;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
