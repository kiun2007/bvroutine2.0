package com.szxgm.gusustreet.model.vo;

import java.util.LinkedList;
import java.util.List;

public class Publicity {

    private int id;
    private String createdOn;
    private int createdBy;
    private int updatedBy;
    private String updatedOn;
    private int versionNo;
    private boolean isDeleted;
    private String pubDate;
    private String address;
    private double longitude;
    private double latitude;
    private int bannerNo;
    private int dataNo;
    private int panelNo;
    private int attachId;
    private String other;
    private String organizer;
    private Object pathId;
    private AttachmentInfo attachmentInfo;

    public AttachmentInfo getAttachmentInfo() {
        return attachmentInfo;
    }

    public void setAttachmentInfo(AttachmentInfo attachmentInfo) {
        this.attachmentInfo = attachmentInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getBannerNo() {
        return bannerNo;
    }

    public void setBannerNo(int bannerNo) {
        this.bannerNo = bannerNo;
    }

    public int getDataNo() {
        return dataNo;
    }

    public void setDataNo(int dataNo) {
        this.dataNo = dataNo;
    }

    public int getAttachId() {
        return attachId;
    }

    public void setAttachId(int attachId) {
        this.attachId = attachId;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Object getPathId() {
        return pathId;
    }

    public void setPathId(Object pathId) {
        this.pathId = pathId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getPanelNo() {
        return panelNo;
    }

    public void setPanelNo(int panelNo) {
        this.panelNo = panelNo;
    }

    public String getOrganizer() {
        return organizer;
    }

    public List<String> getPictures(){
        List<String> pictures = new LinkedList<>();
        if (attachmentInfo != null){
            String[] paths = attachmentInfo.getPath().split(",");
            for (int i = 0; i < paths.length; i++) {
                pictures.add(String.format("http://106.14.143.145/image/%s", paths[i]));
            }
        }
        return pictures;
    }
}
