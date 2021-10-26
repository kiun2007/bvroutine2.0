package com.szxgm.gusustreet.model.query;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.szxgm.gusustreet.base.TaskDisposalType;
import com.szxgm.gusustreet.model.dto.mobile.OrderInfo;
import com.szxgm.gusustreet.model.dto.mobile.OrderTask;

public class OrderInfoReq implements Parcelable {

    /**
     * 协调专用.
     */
    private String id;

    private String taskId;

    private String taskDefKey;

    private String procInsId;

    private String status;

    private String businessId;

    public OrderInfoReq(){
    }

    @JSONField(serialize = false)
    public boolean isHis() {
        return taskDefKey == null && id == null && businessId == null && taskId == null && procInsId != null;
    }

    @JSONField(serialize = false)
    public boolean isCombined(){
        return id != null;
    }

    public OrderInfoReq(String procInsId){
        this.procInsId = procInsId;
    }

    public OrderInfoReq(String id, String procInsId) {
        this.id = id;
        this.procInsId = procInsId;
        taskDefKey = TaskDisposalType.unionDisposal.toString();
    }

    public OrderInfoReq(String orderId, String taskId, String taskDefKey, String procInsId, String status) {
        this.businessId = orderId;
        this.taskId = taskId;
        this.taskDefKey = taskDefKey;
        this.procInsId = procInsId;
        this.status = status;
    }

    protected OrderInfoReq(Parcel in) {
        id = in.readString();
        taskId = in.readString();
        taskDefKey = in.readString();
        procInsId = in.readString();
        status = in.readString();
        businessId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taskId);
        dest.writeString(taskDefKey);
        dest.writeString(procInsId);
        dest.writeString(status);
        dest.writeString(businessId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderInfoReq> CREATOR = new Creator<OrderInfoReq>() {
        @Override
        public OrderInfoReq createFromParcel(Parcel in) {
            return new OrderInfoReq(in);
        }

        @Override
        public OrderInfoReq[] newArray(int size) {
            return new OrderInfoReq[size];
        }
    };

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
