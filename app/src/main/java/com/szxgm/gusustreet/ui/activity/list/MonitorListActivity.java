package com.szxgm.gusustreet.ui.activity.list;

import android.location.Location;
import android.view.View;

import androidx.databinding.ObservableField;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.databinding.ActivityListMonitorBinding;
import com.szxgm.gusustreet.model.dto.CameraInfo;
import com.szxgm.gusustreet.model.dto.CameraMarker;
import com.szxgm.gusustreet.net.requests.CameraReq;
import com.szxgm.gusustreet.net.requests.MonitorListReq;
import com.szxgm.gusustreet.net.services.MonitorService;
import com.szxgm.gusustreet.service.TrailService;
import com.szxgm.gusustreet.ui.activity.AMapActivity;
import com.szxgm.gusustreet.ui.activity.monitor.MonitorPlayActivityHandler;
import com.szxgm.gusustreet.utils.LatLngUtil;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImportHandler;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.utils.ListHandlerUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ViewUtil;

public class MonitorListActivity extends AMapActivity<ActivityListMonitorBinding> implements AMap.OnCameraChangeListener, AMap.OnMarkerClickListener, AutoImportHandler<Object> {

    public static final Class clz = MonitorListActivity.class;

    NormalHandler normalHandler = null;

    @Override
    public void initView() {
        super.initView();
        normalHandler = new NormalHandler<CameraInfo>().addTag(0, MonitorPlayActivityHandler::openActivityNormal);
        binding.setIndex(new ObservableField<>(0));
        binding.setHandler(normalHandler);
        binding.setSearch(new MonitorListReq().listener((v)->{
            ListHandlerUtil.refresh(normalHandler);
        }));
    }

    @Override
    public void mapInit(Location location) {
        amap.setOnCameraChangeListener(this);
        amap.setOnMarkerClickListener(this);
        moveCamera(location);
    }

    @Override
    public int mapViewId() {
        return R.id.mainMapView;
    }

    @Override
    public int getViewId() {
        return R.layout.activity_list_monitor;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

        //东北角
        LatLng northeast = amap.getProjection().getVisibleRegion().latLngBounds.northeast;
        //西南角
        LatLng southwest = amap.getProjection().getVisibleRegion().latLngBounds.southwest;

        double[] southwestPoints = LatLngUtil.gcj02ToWGS84(southwest.longitude, southwest.latitude);
        double[] northeastPoints = LatLngUtil.gcj02ToWGS84(northeast.longitude, northeast.latitude);

        double minLat = southwestPoints[1], minLng = southwestPoints[0];
        double maxLat = northeastPoints[1], maxLng = northeastPoints[0];

        boolean isLevelMax = cameraPosition.zoom >= amap.getMaxZoomLevel();
        addRequest(()->rbp.callServiceData(MonitorService.class,
                s -> s.searchOfMap(new CameraReq(minLat, maxLat, minLng, maxLng, isLevelMax))), this::cameraInfoChanged);
    }

    private void cameraInfoChanged(List<CameraMarker> cameraMarkers){

        amap.clear();
        for (CameraMarker cameraMarker : cameraMarkers){
            View view = ViewUtil.makeViewOfBindLayout(this, R.layout.layout_marker_camera, cameraMarker);
            addMarker(cameraMarker.toLatLng(), BitmapDescriptorFactory.fromView(view), null).setObject(cameraMarker);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.getObject() instanceof CameraMarker){
            CameraMarker cameraMarker = (CameraMarker) marker.getObject();
            if (cameraMarker.getCameraCount() == 1){
                MonitorPlayActivityHandler.openActivityNormal(this, cameraMarker.getCameraInfo());
            }
        }
        return true;
    }

    @Override
    public void onImportComplete(Object value) {

        if (value instanceof TrailService){
            TrailService trailService = (TrailService) value;

            if (trailService.getLastLocation() != null){
                moveCamera(trailService.getLastLocation().toLatLng());
            }
        }
    }
}
