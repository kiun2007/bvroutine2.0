package com.szxgm.gusustreet.model.base;

import com.amap.api.maps2d.model.LatLng;
import com.szxgm.gusustreet.utils.LatLngUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.JSON;

public class LastLocation implements JSON {

    private double lat;

    private double lng;

    private String address;

    private Date time;

    public LastLocation(){
        time = new Date();
    }

    public LastLocation(double lat, double lng, String address) {
        this();
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public Date getTime() {
        return time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LatLng toLatLng(){
        return new LatLng(lat, lng);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> toMap(String latName, String lngName){
        Map<String, Object> map = new HashMap<>();
        map.put(latName, lat);
        map.put(lngName, lng);
        return map;
    }

    public LastLocation convert84(){
        double[] lngLat = LatLngUtil.gcj02ToWGS84(lng, lat);
        lng = lngLat[0];
        lat = lngLat[1];
        return this;
    }
}
