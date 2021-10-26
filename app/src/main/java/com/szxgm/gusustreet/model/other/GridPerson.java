package com.szxgm.gusustreet.model.other;

import java.io.Serializable;

import kiun.com.bvroutine.base.EventBean;

/**
 * 网格人员
 */
public class GridPerson extends EventBean implements Serializable {

    private String id;
    private String wgbm;
    private String wgylbdm;
    private String gmsfhm;
    private String xm;
    private String yhm;
    private String xzzXzqhdm;
    private String xzzDzbm;
    private String xzzQhnxxdz;

    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        onChanged();
    }

    public String getWgbm() {
        return wgbm;
    }

    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
    }

    public String getWgylbdm() {
        return wgylbdm;
    }

    public void setWgylbdm(String wgylbdm) {
        this.wgylbdm = wgylbdm;
    }

    public String getGmsfhm() {
        return gmsfhm;
    }

    public void setGmsfhm(String gmsfhm) {
        this.gmsfhm = gmsfhm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm;
    }

    public String getXzzXzqhdm() {
        return xzzXzqhdm;
    }

    public void setXzzXzqhdm(String xzzXzqhdm) {
        this.xzzXzqhdm = xzzXzqhdm;
    }

    public String getXzzDzbm() {
        return xzzDzbm;
    }

    public void setXzzDzbm(String xzzDzbm) {
        this.xzzDzbm = xzzDzbm;
    }

    public String getXzzQhnxxdz() {
        return xzzQhnxxdz;
    }

    public void setXzzQhnxxdz(String xzzQhnxxdz) {
        this.xzzQhnxxdz = xzzQhnxxdz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return xm;
    }
}
