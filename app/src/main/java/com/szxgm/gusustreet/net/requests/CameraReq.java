package com.szxgm.gusustreet.net.requests;

public class CameraReq {

    /**
     * 地图左下角纬度.
     */
    private double minLat;

    /**
     * 地图右上角纬度.
     */
    private double maxLat;

    /**
     * 地图左下角经度.
     */
    private double minLng;

    /**
     * 地图右上角经度.
     */
    private double maxLng;

    /**
     * 是否已经放大到极限.
     */
    private boolean isLevelMax;

    public CameraReq(double minLat, double maxLat, double minLng, double maxLng, boolean isLevelMax) {
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLng = minLng;
        this.maxLng = maxLng;
        this.isLevelMax = isLevelMax;
    }

    public CameraReq(){
    }

    public double getMaxDiff(){
        double latDiff = Math.abs(maxLat - minLat);
        double lngDiff = Math.abs(maxLng - minLng);
        return Math.max(latDiff, lngDiff);
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public double getMinLng() {
        return minLng;
    }

    public void setMinLng(double minLng) {
        this.minLng = minLng;
    }

    public double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(double maxLng) {
        this.maxLng = maxLng;
    }

    public boolean isLevelMax() {
        return isLevelMax;
    }

    public void setLevelMax(boolean levelMax) {
        isLevelMax = levelMax;
    }
}
