package com.szxgm.gusustreet.model.base;

import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.szxgm.gusustreet.utils.LatLngUtil;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.data.QueryBean;

public abstract class AddressChooseBean extends QueryBean {

    @JSONField(serialize = false)
    private Parcelable chooseAddress;

    private static boolean isToWGS84 = true;

    public static void setIsToWGS84(boolean isToWGS84) {
        AddressChooseBean.isToWGS84 = isToWGS84;
    }

    /**
     * 选中位置的经纬度变化.
     * @param latitude 纬度(Y)
     * @param longitude 经度(X)
     */
    protected abstract void onAddressChange(String title, double latitude, double longitude, String adCode, String adName);

    public final void setChooseAddress(Object address){

        double lat, lng;
        String adCode;
        String adName;
        String title;

        if (address instanceof PoiItem){
            PoiItem poiItem = (PoiItem) address;
            chooseAddress = poiItem.getLatLonPoint();
            adName = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName();
            adCode = poiItem.getAdCode()+"000000";

            lat = poiItem.getLatLonPoint().getLatitude();
            lng = poiItem.getLatLonPoint().getLongitude();
            title = poiItem.getSnippet();
        }else if (address instanceof AMapLocation){

            AMapLocation aMapLocation = (AMapLocation) address;
            adName = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict();
            lat = aMapLocation.getLatitude();
            lng = aMapLocation.getLongitude();
            title = aMapLocation.getAddress();
            adCode = aMapLocation.getAdCode()+"000000";

            chooseAddress = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        }else{
            return;
        }

        if (isToWGS84){
            double[] latLng = LatLngUtil.gcj02ToWGS84(lng, lat);
            lat = latLng[1];
            lng = latLng[0];
        }

        onAddressChange(title, lat, lng, adCode, adName);
        onChanged(false);
    }

    public final Object getChooseAddress(){
        return chooseAddress;
    }
}
