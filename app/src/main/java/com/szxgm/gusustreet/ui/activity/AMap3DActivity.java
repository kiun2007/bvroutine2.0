package com.szxgm.gusustreet.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.szxgm.gusustreet.R;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.BitmapUtil;
import kiun.com.bvroutine.utils.LocationUtils;

public abstract class AMap3DActivity<T extends ViewDataBinding> extends RequestBVActivity<T> {

    public abstract void mapInit(Location location);

    public abstract int mapViewId();

    protected AMap amap;
    protected MapView mapView;
    protected LatLng lastCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (mapViewId() != -1){
            mapView = binding.getRoot().findViewById(mapViewId());
            mapView.onCreate(savedInstanceState);
            amap = mapView.getMap();
        }

        DataBindingUtil.inflate(LayoutInflater.from(this), getViewId(), findViewById(kiun.com.bvroutine.R.id.view_content), true);
        startPermission(LocationUtils.PERMISSION, this::startLocation);
    }

    @Override
    public void initView() {
    }

    private void startLocation(){

        Location location = LocationUtils.getLocation(getContext());
        mapInit(location);
    }

    protected void useLocationButton(){

        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.navi_map_gps_locked));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色

        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细

        amap.setMyLocationStyle(myLocationStyle);
        amap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    protected Marker addMarker(LatLng latLng, BitmapDescriptor image, String title){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        if (image != null){
            markerOptions.icon(image);
        }
        markerOptions.title(title);
        return amap.addMarker(markerOptions);
    }

    protected Marker addMarker(LatLng latLng, String imageUrl, String title){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);

        Marker marker = amap.addMarker(markerOptions);

        Glide.with(getContext()).load(imageUrl).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapUtil.drawableToBitmap(resource, new Size(80,80))));
            }
        });

        return marker;
    }

    protected Marker addMarker(LatLng latLng, @DrawableRes int imageRes, String title){
        return addMarker(latLng, BitmapDescriptorFactory.fromResource(imageRes), title);
    }

    protected Marker addMarker(LatLng latLng, float image, String title){
        return addMarker(latLng, BitmapDescriptorFactory.defaultMarker(image), title);
    }

    protected void moveCamera(LatLng latLng){
        lastCamera = latLng;
        amap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, 18, 30, 30))
        );
    }

    protected void moveCamera(Location location){
        moveCamera(new LatLng(location.getLatitude(), location.getLongitude()));
    }
}
