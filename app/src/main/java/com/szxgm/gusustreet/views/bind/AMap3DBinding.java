package com.szxgm.gusustreet.views.bind;

import android.view.View;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.base.LastLocation;
import com.szxgm.gusustreet.model.base.MapPoint;
import com.szxgm.gusustreet.model.dto.PatrolPoints;

import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.LocationUtils;

public class AMap3DBinding extends BindConvert<MapView, Object, Object> {

    PolylineOptions polylineOptions = new PolylineOptions();
    Polyline polyline;
    AMap mapBase;
    View startView;
    Marker startMarker = null;

    public AMap3DBinding(MapView view) {
        super(view);
        mapBase = view.getMap();
        polylineOptions.setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.texture_polyline_blue));
        polylineOptions.setUseTexture(true);
        polylineOptions.width(32);
        polyline = mapBase.addPolyline(polylineOptions);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {

        if (value instanceof List){
            List list = (List) value;

            if (list.isEmpty()){
                return;
            }

            List<LatLng> latLngs = new LinkedList<>();

            if (list.get(0) instanceof List){
                List<List<LastLocation>> lastLocations = list;

                for (List<LastLocation> locations : lastLocations){
                    latLngs.addAll(ListUtil.toList(locations, item-> new LatLng(item.getLat(), item.getLng())));
                }
            }else if (list.get(0) instanceof PatrolPoints){

                ListUtil.map((List<PatrolPoints>) list, item->{
                    List<LatLng> latLngList = ListUtil.toList(LocationUtils.uncompress(item.getPoints()), point-> new LatLng(point.getLatitude(), point.getLongitude()));
                    latLngs.addAll(latLngList);
                });

                if (latLngs.size() >= 2){
                    MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(startView)).title("巡查终止点");
                    markerOptions.position(latLngs.get(latLngs.size() - 1));
                    mapBase.addMarker(markerOptions);
                }
            }else if (list.get(0) instanceof MapPoint){
                ListUtil.map((List<MapPoint>) list, item->{
                    latLngs.add(new LatLng(item.lat(), item.lng()));
                });
            }

            if (startMarker == null){
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(startView)).title("巡查起始点");
                markerOptions.position(latLngs.get(0));
                startMarker = mapBase.addMarker(markerOptions);

                mapBase.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds.builder().include(latLngs.get(0)).include(latLngs.get(latLngs.size() - 1)).build(), 10));
            }
            polyline.setPoints(latLngs);
            mapBase.reloadMap();
        }
    }
}
