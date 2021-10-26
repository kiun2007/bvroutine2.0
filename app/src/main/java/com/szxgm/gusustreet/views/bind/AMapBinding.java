package com.szxgm.gusustreet.views.bind;

import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.base.LastLocation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.utils.ListUtil;

public class AMapBinding extends BindConvert<MapView, Object, Object> {

    PolylineOptions polylineOptions = new PolylineOptions().width(8).color(Color.BLUE);
    Polyline polyline;
    AMap mapBase;
    View startView;
    Marker startMarker = null;

    public AMapBinding(MapView view) {
        super(view);
        polyline = view.getMap().addPolyline(polylineOptions);
        mapBase = view.getMap();
        startView = LayoutInflater.from(view.getContext()).inflate(R.layout.ic_start_marker, null);

    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

        if (value instanceof List){
            List list = (List) value;

            if (list.get(0) instanceof List){
                List<List<LastLocation>> lastLocations = list;
                List<LatLng> latLngs = new LinkedList<>();

                for (List<LastLocation> locations : lastLocations){
                    latLngs.addAll(ListUtil.toList(locations, item-> new LatLng(item.getLat(), item.getLng())));
                }

                if (startMarker == null){
                    MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(startView)).title("巡查起始点");
                    markerOptions.position(latLngs.get(0));
                    startMarker = mapBase.addMarker(markerOptions);
                }

                polyline.setPoints(latLngs);
                mapBase.invalidate();
            }
        }
    }
}
