package com.szxgm.gusustreet.net;

import android.location.Location;
import android.os.Build;

import java.util.Date;

public class SimulationGPS {

    public static Location loc(double lat, double lng){
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    static Location ALoc = loc(31.278156184447745,120.60114386640929);
    static Location BLoc = loc(31.2781470149746,120.59980812631987);
    static Location CLoc = loc(31.2787980453775,120.59934678636931);
    static Location DLoc = loc(31.280508124959272,120.59923949800871);
    static Location ELoc = loc(31.281461721554262,120.5993628796234);

    private static int index = 0;

    /**
     * 根据部分特征参数设备信息来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("sdk_gphone_x86")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    public static Location getLocation(){

        int speed = 50;
        int locIndex = (index % 200) / speed;

        Location[] locations = new Location[]{ALoc, BLoc, CLoc, DLoc, ELoc};
        Location begin = locations[locIndex];
        Location end = locations[locIndex + 1];
        double lat = (index % speed) * (end.getLatitude() - begin.getLatitude()) / speed;
        double lng = (index % speed) * (end.getLongitude() - begin.getLongitude()) / speed;

        Location location = new Location("");
        location.setLatitude(begin.getLatitude() + lat);
        location.setLongitude(begin.getLongitude() + lng);
        location.setBearing(begin.bearingTo(end));
        location.setTime(new Date().getTime());

        index ++;
        return location;
    }
}
