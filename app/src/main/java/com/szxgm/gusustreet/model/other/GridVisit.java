package com.szxgm.gusustreet.model.other;


import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.base.AddressChooseBean;

import java.util.Date;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.GeneralItemTextListener;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.views.text.GeneralItemText;

/**
 * 网格走访任务.
 */
@Verify(value = NotNull.class, desc = "必填项")
public class GridVisit extends AddressChooseBean implements GeneralItemTextListener {

    //走访类型.
    private Integer visitType = 4;

    //走访名称.
    private String visitName;

    //执行对象.
    private String dealPersonName;

    //执行人账户
    private String dealPerson;

    //执行人ID.
    private String dealPersonId;

    //共同执行人姓名.
    @Verify(value = NotNull.class, pass = "true")
    private String togeDealPersonName;

    //共同执行人账户
    @Verify(value = NotNull.class, pass = "true")
    private String togeDealPerson;

    //共同执行人ID
    @Verify(value = NotNull.class, pass = "true")
    private String togeDealPersonId;

    //服务对象姓名.
    private String visitObject;

    //服务对象地址.
    private String visitAddress;

    //联系电话.
    private String lxdh;

    //截止时间.
    @JSONField(format = "yyyy-MM-dd")
    private Date completeTime;

    //服务内容.
    private String visitProblem;

    private String wgbm;

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public String getDealPersonName() {
        return dealPersonName;
    }

    public void setDealPersonName(String dealPersonName) {
        this.dealPersonName = dealPersonName;
    }

    public String getDealPerson() {
        return dealPerson;
    }

    public void setDealPerson(String dealPerson) {
        this.dealPerson = dealPerson;
    }

    public String getDealPersonId() {
        return dealPersonId;
    }

    public void setDealPersonId(String dealPersonId) {
        this.dealPersonId = dealPersonId;
    }

    public String getTogeDealPersonName() {
        return togeDealPersonName;
    }

    public void setTogeDealPersonName(String togeDealPersonName) {
        this.togeDealPersonName = togeDealPersonName;
    }

    public String getTogeDealPerson() {
        return togeDealPerson;
    }

    public void setTogeDealPerson(String togeDealPerson) {
        this.togeDealPerson = togeDealPerson;
    }

    public String getTogeDealPersonId() {
        return togeDealPersonId;
    }

    public void setTogeDealPersonId(String togeDealPersonId) {
        this.togeDealPersonId = togeDealPersonId;
    }

    public String getVisitObject() {
        return visitObject;
    }

    public void setVisitObject(String visitObject) {
        this.visitObject = visitObject;
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getVisitProblem() {
        return visitProblem;
    }

    public void setVisitProblem(String visitProblem) {
        this.visitProblem = visitProblem;
    }

    public String getWgbm() {
        return wgbm;
    }

    public void setWgbm(String wgbm) {
        this.wgbm = wgbm;
    }

    @Override
    public void onChanged(GeneralItemText view, String id, String title, Object extra) {
        if (view.getId() == R.id.generalTextDealPerson){
            dealPerson = id;
            dealPersonName = title;
            if (extra instanceof GridPerson){
                dealPersonId = ((GridPerson) extra).getId();
                wgbm = ((GridPerson) extra).getWgbm();
            }
        }else if (view.getId() == R.id.generalTextToGoDealPerson){
            togeDealPerson = id;
            togeDealPersonName = title;
            if (extra instanceof String){
                togeDealPersonId = (String) extra;
            }
        }
        onChanged();
    }

    @Override
    protected void onAddressChange(String title, double latitude, double longitude, String adCode, String adName) {
        visitAddress = title;
    }
}
