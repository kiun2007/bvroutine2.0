package kiun.com.bvroutine.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;

public class LocationUtils {

    public static final String[] PERMISSION = new String[]{
        Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    private static LocationManager locationManager;

    private static GetTNoParamCall<Location> locationGetCall;

    public static final int ACCURACY = 10000000;

    public static void setLocationGetCall(GetTNoParamCall<Location> caller){
        locationGetCall = caller;
    }

    @SuppressLint("MissingPermission")
    public static Location getLocation(Context context){

        if (locationGetCall != null){
            try {
                return locationGetCall.call();
            } catch (Exception e) {
            }
        }

        if (locationManager == null){
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                int a = 0;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                int a = 0;
            }

            @Override
            public void onProviderEnabled(String provider) {
                int a = 0;
            }

            @Override
            public void onProviderDisabled(String provider) {
                int a = 0;
            }
        });
//        for (String provider : providers) {
//            Location l = locationManager.getLastKnownLocation(provider);
//
//            if (l == null) {
//                locationManager.requestLocationUpdates(provider, 2000, 1, new LocationListener() {
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        int a = 0;
//                    }
//
//                    @Override
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//                        int a = 0;
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String provider) {
//                        int a = 0;
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String provider) {
//                        int a = 0;
//                    }
//                });
//                continue;
//            }
//            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
//                // Found best last known location: %s", l);
//                bestLocation = l;
//            }
//        }
        return bestLocation;
    }

    public static String getAddress(Context context){
        return getAddress(getLocation(context), context);
    }

    public static String getAddress(Location location, Context context){

        Geocoder geocoder = new Geocoder(context, Locale.CHINA);
        try {
            List<Address> mAddresses= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if (mAddresses.size() > 0){
                Address address = mAddresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "未知地址";
    }

    public static String compressLocation(List<Location> locations){

        Location contrast = locations.get(0);
        byte[] latLngBytes = new byte[(locations.size() - 1) * 4 + 16];

        System.arraycopy(ByteUtil.doubleToBytes(contrast.getLatitude()),0, latLngBytes, 0, 8);
        System.arraycopy(ByteUtil.doubleToBytes(contrast.getLongitude()),0, latLngBytes, 8, 8);

        int index = 16;
        for (Location location : locations) {

            if (location == contrast){
                continue;
            }

            double lat = (location.getLatitude() - contrast.getLatitude()) * ACCURACY;
            double lng = (location.getLongitude() - contrast.getLongitude()) * ACCURACY;

            boolean outAccuracyRange = false;
            //超出精度范围为无效点，可能存在定位问题.
            if (lat > Short.MAX_VALUE || lat < Short.MIN_VALUE || lng > Short.MAX_VALUE || lng < Short.MIN_VALUE){
                outAccuracyRange = true;
                lat = 0;
                lng = 0;
            }

            short latInc = (short) lat;
            short lngInc = (short) lng;

            System.arraycopy(ByteUtil.shortToBytes(latInc), 0, latLngBytes, index, 2);
            System.arraycopy(ByteUtil.shortToBytes(lngInc), 0, latLngBytes, index + 2, 2);

            //只保存精度范围内的点.
            if (!outAccuracyRange){
                contrast = location;
            }
            index += 4;
        }

        return Base64.encodeToString(ByteUtil.compress(latLngBytes), Base64.NO_WRAP);
    }

    public static List<Location> uncompress(String locations){

        byte[] latLngBytes = ByteUtil.uncompress(Base64.decode(locations, Base64.NO_WRAP));

        double startLat = ByteUtil.bytesToDouble(latLngBytes, 0);
        double startLng = ByteUtil.bytesToDouble(latLngBytes, 8);

        int pointCount = (latLngBytes.length - 16) / 4;
        int index = 16;
        Location firstLoc = new Location("");
        firstLoc.setLatitude(startLat);
        firstLoc.setLongitude(startLng);

        List<Location> locationList = new LinkedList<>();
        locationList.add(firstLoc);

        for (int i = 0; i < pointCount; i++) {

            Location location = new Location("");

            startLat = startLat + (double) ByteUtil.bytesToShort(latLngBytes, index) / ACCURACY;
            startLng = startLng + (double) ByteUtil.bytesToShort(latLngBytes, index + 2) / ACCURACY;
            location.setLatitude(startLat);
            location.setLongitude(startLng);

            locationList.add(location);
            index += 4;
        }

        return locationList;
    }

    public static List<Location> listFromJSON(String jsonArray){

        List<Location> locations = new LinkedList<>();
        JSONArray array = JSONObject.parseArray(jsonArray);
        for (int k = 0; k < array.size(); k++) {
            JSONArray latLngs = array.getJSONArray(k);
            locations.add(addLocation(latLngs.getBigDecimal(1).doubleValue(),latLngs.getBigDecimal(0).doubleValue()));
        }
        return locations;
    }

    public static Location addLocation(double lat, double lng){

        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }
}
