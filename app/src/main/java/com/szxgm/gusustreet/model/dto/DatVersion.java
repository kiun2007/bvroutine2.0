package com.szxgm.gusustreet.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import kiun.com.bvroutine.base.EventBean;

/**
 * 程序版本管理表
 * 
 * DAT_VERSION_INFO
 *
 * 2020-06-30 21:24:07
 */
public class DatVersion {
    /**
     * 版本编号
     */
    private String id;

    /**
     * 程序包名
     */
    private String domain;

    /**
     * 程序标题
     */
    private String name;

    /**
     * 程序构建版本
     */
    private BigDecimal build;

    /**
     * 上传到服务器的时间
     */
    private Date uploadTime;

    /**
     * 版本名称
     */
    private String version;

    /**
     * 上传者
     */
    private String createBy;

    /**
     * 最低允许SDK版本
     */
    private BigDecimal minSdk;

    /**
     * 最高允许SDK版本
     */
    private BigDecimal maxSdk;

    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 版本说明.
     */
    private String versionDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getBuild() {
        return build;
    }

    public void setBuild(BigDecimal build) {
        this.build = build;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public BigDecimal getMinSdk() {
        return minSdk;
    }

    public void setMinSdk(BigDecimal minSdk) {
        this.minSdk = minSdk;
    }

    public BigDecimal getMaxSdk() {
        return maxSdk;
    }

    public void setMaxSdk(BigDecimal maxSdk) {
        this.maxSdk = maxSdk;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
}