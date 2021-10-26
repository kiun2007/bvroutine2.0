package com.szxgm.gusustreet.model.dto;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DAT_CAMERA_INFO
 */
public class CameraInfo implements Parcelable {

    /**
     * 摄像头编码
     */
    private String code;

    /**
     * 摄像头所在位置
     */
    private String cameraLocation;

    /**
     * 摄像头状态 0 不可用 1可用
     */
    private String cameraStatus;

    /**
     * 设备创建时间
     */
    private Date devCreateTime;

    /**
     * 设备来源类型
     */
    private String devFormType;

    /**
     * 设备分组编码
     */
    private String devGroupCode;

    /**
     * 设备IP
     */
    private String devIp;

    /**
     * 设备型号
     */
    private String devModelType;

    /**
     * 域名编号
     */
    private String domainCode;

    /**
     * 是否启用录音 0未启用 1启用
     */
    private String enableVoice;

    /**
     * 设备安装高度
     */
    private Double height;

    /**
     * 是否外部域名
     */
    private String isExDomain;

    /**
     * 设备所在的纬度
     */
    private BigDecimal latitude;

    /**
     * 设备所在的经度
     */
    private BigDecimal longitude;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 网络类型
     */
    private String netType;

    /**
     * 录像设备编码
     */
    private String nvrCode;

    /**
     * 父设备编码
     */
    private String parentCode;

    /**
     * 存储设备
     */
    private String reserve;

    /**
     * 设备状态：
     * 0：离线
     * 1：在线
     * 2：休眠
     */
    private String status;

    /**
     * 是否为智能设备
     */
    private String supportIntelligent;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备厂家
     */
    private String vendorType;

    public CameraInfo(){}


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCameraLocation() {
        return cameraLocation;
    }

    public void setCameraLocation(String cameraLocation) {
        this.cameraLocation = cameraLocation == null ? null : cameraLocation.trim();
    }

    public String getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(String cameraStatus) {
        this.cameraStatus = cameraStatus == null ? null : cameraStatus.trim();
    }

    public Date getDevCreateTime() {
        return devCreateTime;
    }

    public void setDevCreateTime(Date devCreateTime) {
        this.devCreateTime = devCreateTime;
    }

    public String getDevFormType() {
        return devFormType;
    }

    public void setDevFormType(String devFormType) {
        this.devFormType = devFormType == null ? null : devFormType.trim();
    }

    public String getDevGroupCode() {
        return devGroupCode;
    }

    public void setDevGroupCode(String devGroupCode) {
        this.devGroupCode = devGroupCode == null ? null : devGroupCode.trim();
    }

    public String getDevIp() {
        return devIp;
    }

    public void setDevIp(String devIp) {
        this.devIp = devIp == null ? null : devIp.trim();
    }

    public String getDevModelType() {
        return devModelType;
    }

    public void setDevModelType(String devModelType) {
        this.devModelType = devModelType == null ? null : devModelType.trim();
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode == null ? null : domainCode.trim();
    }

    public String getEnableVoice() {
        return enableVoice;
    }

    public void setEnableVoice(String enableVoice) {
        this.enableVoice = enableVoice == null ? null : enableVoice.trim();
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getIsExDomain() {
        return isExDomain;
    }

    public void setIsExDomain(String isExDomain) {
        this.isExDomain = isExDomain == null ? null : isExDomain.trim();
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType == null ? null : netType.trim();
    }

    public String getNvrCode() {
        return nvrCode;
    }

    public void setNvrCode(String nvrCode) {
        this.nvrCode = nvrCode == null ? null : nvrCode.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve == null ? null : reserve.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSupportIntelligent() {
        return supportIntelligent;
    }

    public void setSupportIntelligent(String supportIntelligent) {
        this.supportIntelligent = supportIntelligent == null ? null : supportIntelligent.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType == null ? null : vendorType.trim();
    }

    public String getId(){
        return code.split("#")[0];
    }

    public File getLocalThumb(Context context){

        File dir = new File(context.getCacheDir(), "snapshot");
        File snapshotFile = new File(dir, getId() + ".jpg");

        if (snapshotFile.exists()){
            return snapshotFile;
        }
        return null;
    }

    protected CameraInfo(Parcel in) {
        code = in.readString();
        cameraLocation = in.readString();
        cameraStatus = in.readString();
        devFormType = in.readString();
        devGroupCode = in.readString();
        devIp = in.readString();
        devModelType = in.readString();
        domainCode = in.readString();
        enableVoice = in.readString();
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readDouble();
        }
        isExDomain = in.readString();
        name = in.readString();
        netType = in.readString();
        nvrCode = in.readString();
        parentCode = in.readString();
        reserve = in.readString();
        status = in.readString();
        supportIntelligent = in.readString();
        type = in.readString();
        vendorType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(cameraLocation);
        dest.writeString(cameraStatus);
        dest.writeString(devFormType);
        dest.writeString(devGroupCode);
        dest.writeString(devIp);
        dest.writeString(devModelType);
        dest.writeString(domainCode);
        dest.writeString(enableVoice);
        if (height == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(height);
        }
        dest.writeString(isExDomain);
        dest.writeString(name);
        dest.writeString(netType);
        dest.writeString(nvrCode);
        dest.writeString(parentCode);
        dest.writeString(reserve);
        dest.writeString(status);
        dest.writeString(supportIntelligent);
        dest.writeString(type);
        dest.writeString(vendorType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CameraInfo> CREATOR = new Creator<CameraInfo>() {
        @Override
        public CameraInfo createFromParcel(Parcel in) {
            return new CameraInfo(in);
        }

        @Override
        public CameraInfo[] newArray(int size) {
            return new CameraInfo[size];
        }
    };
}