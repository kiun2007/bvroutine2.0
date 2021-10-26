package com.szxgm.gusustreet.net.requests;

import com.szxgm.gusustreet.model.base.AddressChooseBean;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.verify.LengthLimit;
import kiun.com.bvroutine.data.verify.NotNull;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.interfaces.verify.Verifys;

public class SentinelReq extends AddressChooseBean {

    /**
     * 上报地点
     */
    @Verifys({@Verify(value = NotNull.class,desc = "请选择地点")})
    private String address;

    /**
     * 上报详情说明
     */
    @Verifys({
            @Verify(value = NotNull.class,desc = "请详细描述违法行为"),
            @Verify(value = LengthLimit.class, extra = "[10,500]", desc = "字数请控制在10-500")
    })
    private String description;

    /**
     * 图片
     */
    @Verifys({
            @Verify(value = NotNull.class,desc = "至少上传一张违法图片")
    })
    private String attachment;

    /**
     * 上报时间.
     */
    private String createdOn;

    /**
     * 上报人
     */
    private String reportMan;

    /**
     * 所在哨点.
     */
    private String reportPath;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        onChanged();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        onChanged();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
        onChanged();
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getReportMan() {
        return reportMan;
    }

    public void setReportMan(String reportMan) {
        this.reportMan = reportMan;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    @Override
    protected void onAddressChange(String title, double latitude, double longitude, String adCode, String adName) {
        address = title;
    }
}
