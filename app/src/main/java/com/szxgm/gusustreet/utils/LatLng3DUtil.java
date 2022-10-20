package com.szxgm.gusustreet.utils;

import android.location.Location;

import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;
import com.szxgm.gusustreet.MainApplication;

public class LatLng3DUtil {
    /**
     * GPS坐标系转换成高德.
     * @param location
     * @return
     */
    public static LatLng fromGPS(Location location){
        return convert(location, CoordinateConverter.CoordType.GPS);
    }

    public static LatLng convert(Location sourceLatLng, CoordinateConverter.CoordType coord) {

        CoordinateConverter converter  = new CoordinateConverter();
        // CoordType.GPS 待转换坐标类型
        converter.from(coord);
        // sourceLatLng待转换坐标点
        converter.coord(new LatLng(sourceLatLng.getLatitude(), sourceLatLng.getLongitude()));
        // 执行转换操作

        LatLng desLatLng = converter.convert();
        return desLatLng;
    }
}
