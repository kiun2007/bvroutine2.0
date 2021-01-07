package com.szxgm.gusustreet.utils;

import android.content.Context;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import kiun.com.bvroutine.BuildConfig;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.AgileThread;

public class AMapLocationUtil {

    public static AMapLocationClient startLocation(Context context, AMapLocationListener setCaller){
        return startLocation(context, 1000*60*5, setCaller);
    }

    public static AMapLocationClient startLocation(Context context, int interval, AMapLocationListener setCaller, boolean isOnce){
        AMapLocationClient locationClient = new AMapLocationClient(context);
        AMapLocationClientOption option = new AMapLocationClientOption();

        if (BuildConfig.DEBUG){
            option.setMockEnable(true);
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        }else{
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        }
        option.setOnceLocation(isOnce);
        option.setInterval(interval);

        locationClient.setLocationOption(option);
        locationClient.setLocationListener(aMapLocation -> {
            if (aMapLocation.getErrorCode() == 0){
                if (setCaller != null){

                    if (!aMapLocation.getAddress().isEmpty()){
                        setCaller.onLocationChanged(aMapLocation);
                        return;
                    }

                    new AgileThread(context, v -> {

                        GeocodeSearch geocoderSearch = new GeocodeSearch(context);
                        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 100, GeocodeSearch.AMAP);
                        RegeocodeAddress regeocodeAddress = geocoderSearch.getFromLocation(regeocodeQuery);

                        aMapLocation.setAdCode(regeocodeAddress.getAdCode());
                        aMapLocation.setAddress(regeocodeAddress.getFormatAddress());
                        aMapLocation.setCity(regeocodeAddress.getCity());
                        aMapLocation.setDistrict(regeocodeAddress.getDistrict());
                        aMapLocation.setProvince(regeocodeAddress.getProvince());
                        v.into(()->setCaller.onLocationChanged(aMapLocation));
                    }).start();
                }
            }else{
                Toast.makeText(context, aMapLocation.getErrorInfo(), Toast.LENGTH_LONG).show();
            }
        });
        locationClient.startLocation();
        return locationClient;
    }

    public static AMapLocationClient startLocation(Context context, int interval, AMapLocationListener setCaller){
        return startLocation(context, interval, setCaller, true);
    }

    public static void weatherSearch(Context context, String cityName, SetCaller<LocalWeatherLive> caller){

        WeatherSearch search = new WeatherSearch(context);
        search.setQuery(new WeatherSearchQuery(cityName, WeatherSearchQuery.WEATHER_TYPE_LIVE));
        search.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {

            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
                caller.call(localWeatherLiveResult.getLiveResult());
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
            }
        });

        search.searchWeatherAsyn();
    }
}
