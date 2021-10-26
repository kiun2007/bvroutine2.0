package com.szxgm.gusustreet.model.dto;

import android.location.Location;

import com.amap.api.maps2d.model.LatLng;
import com.szxgm.gusustreet.utils.LatLngUtil;

import kiun.com.bvroutine.utils.LocationUtils;

public class CameraMarker {

    /**
     * 标注包含的监控数量.
     */
    private int cameraCount;

    /**
     * 只包含一个监控时监控摄像头信息.
     */
    private CameraInfo cameraInfo;

    /**
     * 标注所在纬度.
     */
    private double latitude;

    /**
     * 标注所在经度.
     */
    private double longitude;

    public int getCameraCount() {
        return cameraCount;
    }

    public void setCameraCount(int cameraCount) {
        this.cameraCount = cameraCount;
    }

    public CameraInfo getCameraInfo() {
        return cameraInfo;
    }

    public void setCameraInfo(CameraInfo cameraInfo) {
        this.cameraInfo = cameraInfo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng toLatLng(){
        //转化坐标系
        return LatLngUtil.fromGPS(LocationUtils.addLocation(latitude, longitude));
    }

    public Integer[] count(){
        return new Integer[]{cameraCount};
    }
}
