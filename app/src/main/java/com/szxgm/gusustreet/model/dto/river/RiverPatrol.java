package com.szxgm.gusustreet.model.dto.river;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import kiun.com.bvroutine.utils.ParcelableUtil;

public class RiverPatrol implements Parcelable {

    /**
     * 巡查表主键
     */
    private String id;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除标记（0未删除，1已删除）
     */
    private String delFalg;

    /**
     * 事发河道
     */
    private String riverId;

    /**
     * 结束时间
     */
    private Date endTime;

    private String image;

    private String workOrderId;

    private String newPoint;

    private String riverName;

    public RiverPatrol(){
    }

    protected RiverPatrol(Parcel in) {
        id = in.readString();
        createBy = in.readString();
        updateBy = in.readString();
        remarks = in.readString();
        delFalg = in.readString();
        riverId = in.readString();
        workOrderId = in.readString();
        newPoint = in.readString();
        riverName = in.readString();
        createDate = ParcelableUtil.readDate(in);
        endTime = ParcelableUtil.readDate(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createBy);
        dest.writeString(updateBy);
        dest.writeString(remarks);
        dest.writeString(delFalg);
        dest.writeString(riverId);
        dest.writeString(workOrderId);
        dest.writeString(newPoint);
        dest.writeString(riverName);
        ParcelableUtil.writeDate(dest, createDate);
        ParcelableUtil.writeDate(dest, endTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RiverPatrol> CREATOR = new Creator<RiverPatrol>() {
        @Override
        public RiverPatrol createFromParcel(Parcel in) {
            return new RiverPatrol(in);
        }

        @Override
        public RiverPatrol[] newArray(int size) {
            return new RiverPatrol[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFalg() {
        return delFalg;
    }

    public void setDelFalg(String delFalg) {
        this.delFalg = delFalg == null ? null : delFalg.trim();
    }

    public String getRiverId() {
        return riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId == null ? null : riverId.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId == null ? null : workOrderId.trim();
    }

    public String getNewPoint() {
        return newPoint;
    }

    public void setNewPoint(String newPoint) {
        this.newPoint = newPoint == null ? null : newPoint.trim();
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
