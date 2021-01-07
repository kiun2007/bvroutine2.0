package com.szxgm.gusustreet.net.requests;

import com.szxgm.gusustreet.model.base.AddressChooseBean;

import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

/**
 * 宣传接收数据
 */
public class PublicityReq extends AddressChooseBean {

    /**
     * 宣传时间 yyyy-M-dd
     */
    private String pubDate;

    /**
     * 宣传地点
     */
    @Verifys({@Verify(value = NotNull.class,desc = "请选择地点")})
    private String address;

    /**
     * 条幅（个）
     */
    @Verifys({@Verify(value = NotNull.class,desc = "请输入条幅个数")})
    private Integer bannerNo;

    /**
     * 宣传资料（份）
     */
    @Verifys({@Verify(value = NotNull.class,desc = "请输入宣传资料份数")})
    private Integer dataNo;

    /**
     * 展板（块）
     */
    @Verifys({@Verify(value = NotNull.class,desc = "请输入展板块数")})
    private Integer panelNo;

    /**
     * 其他
     */
    private String other;

    /**
     * 图片地址
     */
    private String attachment;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBannerNo() {
        return bannerNo;
    }

    public void setBannerNo(Integer bannerNo) {
        this.bannerNo = bannerNo;
    }

    public Integer getDataNo() {
        return dataNo;
    }

    public void setDataNo(Integer dataNo) {
        this.dataNo = dataNo;
    }

    public Integer getPanelNo() {
        return panelNo;
    }

    public void setPanelNo(Integer panelNo) {
        this.panelNo = panelNo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    protected void onAddressChange(String title, double latitude, double longitude, String adCode, String adName) {
        address = title;
    }
}
