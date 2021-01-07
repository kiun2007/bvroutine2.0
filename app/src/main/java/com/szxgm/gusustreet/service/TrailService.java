package com.szxgm.gusustreet.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.model.base.LastLocation;
import com.szxgm.gusustreet.model.dto.PatrolPoints;
import com.szxgm.gusustreet.model.dto.river.RiverPatrol;
import com.szxgm.gusustreet.model.dto.river.RiverPlain;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.net.services.LocationService;
import com.szxgm.gusustreet.net.services.RiverService;
import com.szxgm.gusustreet.ui.activity.river.PatrolRiverActivityHandler;
import com.szxgm.gusustreet.utils.AMapLocationUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.ActivityApplication;
import kiun.com.bvroutine.base.BaseService;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ServiceVariableSet;
import kiun.com.bvroutine.interfaces.Scheduled;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.AgileThread;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.LocationUtils;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.RetrofitUtil;

@AutoImport(ServiceVariableSet.class)
public class TrailService extends BaseService implements AMapLocationListener {

    private AMapLocationClient locationClient;
    private LastLocation lastLocation;
    private List<List<LastLocation>> allPoints = new LinkedList<>();
    private ObservableField<List<List<LastLocation>>> pointsField = new ObservableField<>();
    private SetCaller<Location> locationSetCaller;

    private String patrolId = null;

    private RiverPatrol riverPatrol;

    private List<LastLocation> pointTail = new LinkedList<>();

    @Override
    public void onCreate() {
        locationClient = AMapLocationUtil.startLocation(this,5 * 1000,this, false);
        new AgileThread(this, (thread)->{

            riverPatrol = RetrofitUtil.callServiceData(RiverService.class, s -> s.getLastUnFinished());
            if (riverPatrol != null){

                patrolId = riverPatrol.getId();
                startScheduled("AutoPoints");

                GeneralListQuery query = new GeneralListQuery(RiverPlain.class).put("id", QueryType.Eq, riverPatrol.getRiverId());
                List<RiverPlain> riverDetails = RetrofitUtil.callServiceList(GeneralListService.class, s -> s.search(query));
                if (!ListUtil.isEmpty(riverDetails)){
                    riverPatrol.setRiverName(riverDetails.get(0).getRiveRnme());
                }

                List<PatrolPoints> patrolPoints = RetrofitUtil.callServiceData(RiverService.class, s -> s.getPoints(riverPatrol.getId()));
                for (PatrolPoints points : patrolPoints){
                     List<Location> locations = LocationUtils.uncompress(points.getPoints());
                     List<LastLocation> lastLocations = ListUtil.toList(locations, item->new LastLocation(item.getLatitude(), item.getLongitude(),null));
                     allPoints.add(lastLocations);
                }
                allPoints.add(new LinkedList<>());
                pointsField.set(allPoints);

                thread.into(()->{

                    String message = String.format("%s的巡查还未完成(开始时间:%s),将继续巡查,如需结束巡查请至巡查页面.",
                            riverPatrol.getRiverName(), MCString.formatDate("M月dd日HH时mm分", riverPatrol.getCreateDate()));

                    Activity activity = ActivityApplication.getApplication().getTop();
                    new AlertDialog.Builder(activity).setTitle("提示").setMessage(message)
                            .setNegativeButton("关闭",null)
                            .setPositiveButton("查看巡查", (dialog, which) -> {
                                PatrolRiverActivityHandler.openFromRiverPatrol(activity, riverPatrol);
                            }).show();
                });
            }
        }).start();
        super.onCreate();

        startScheduled("Coordinate");
        startScheduled("CoordinateTail");
    }

    public void start(String patrolId){
        this.patrolId = patrolId;
        startScheduled("AutoPoints");
        allPoints.add(new LinkedList<>());
        new AgileThread(this, (thread)->{
            riverPatrol = RetrofitUtil.callServiceData(RiverService.class, s -> s.getLastUnFinished());
        }).start();
    }

    public RiverPatrol getRiverPatrol() {
        return riverPatrol;
    }

    public void stop(String patrolId){
        this.patrolId = null;
        this.riverPatrol = null;
        allPoints.clear();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationClient != null){
            locationClient.stopLocation();
        }
    }

    @Scheduled(value = 30 * 1000, key = "Coordinate", async = true)
    public void uploadPoint() throws Exception{

        if (lastLocation == null){
            return;
        }

        synchronized (pointTail){
            RetrofitUtil.callServiceData(LocationService.class, s -> s.coordinate(lastLocation.convert84().toMap("latitude","longitude")));
            pointTail.add(lastLocation);
        }
    }

    @Scheduled(value = 10 * 60 * 1000, key = "CoordinateTail", async = true)
    public void uploadPoints() throws Exception{

        synchronized (pointTail){
            List<Map<String, Object>> points = ListUtil.toList(pointTail, item-> item.convert84().toMap("latitude","longitude"));

            String json = JSON.toJSONString(points);
            Map<String, String> jsonMap = new HashMap<String, String>(){{put("coordinates", json);}};
            RetrofitUtil.callServiceData(LocationService.class, s -> s.trajectory(jsonMap));
            pointTail.clear();
        }
    }

    @Scheduled(value = 5 * 60 * 1000, key = "AutoPoints", async = true)
    public void submitPoints() throws Exception{
        if (commitPoints()){
            Toast.makeText(this, "自动保存成功!", Toast.LENGTH_LONG).show();
        }
    }

    public void setLocationChangedListener(SetCaller<Location> locationSetCaller){
        this.locationSetCaller = locationSetCaller;

        if (lastLocation != null){
            locationSetCaller.call(LocationUtils.addLocation(lastLocation.getLat(), lastLocation.getLng()));
        }
    }

    public ObservableField<List<List<LastLocation>>> getAllPoints() {
        return pointsField;
    }

    private boolean commitPoints() throws Exception{

        if (patrolId == null){
            return false;
        }

        List<LastLocation> locations = allPoints.get(allPoints.size() - 1);
        if (locations.size() <= 1){
            return false;
        }
        allPoints.add(new LinkedList<>());

        PatrolPoints patrolPoints = new PatrolPoints();
        patrolPoints.setCreateDate(locations.get(0).getTime());
        patrolPoints.setPatrolId(patrolId);
        patrolPoints.setPoints(LocationUtils.compressLocation(ListUtil.toList(locations, (item)-> LocationUtils.addLocation(item.getLat(), item.getLng()))));
        patrolPoints.setEndDate(locations.get(locations.size()-1).getTime());

        String msg = RetrofitUtil.callServiceData(RiverService.class, s -> s.commitPoints(patrolPoints));
        return true;
    }

    public LastLocation getLastLocation() {
        return lastLocation;
    }

    /**
     * 高德定位反馈.
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        LastLocation nowLocation = new LastLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getAddress());

//        Toast.makeText(getBaseContext(), "定位:"+aMapLocation.getAddress()+", POINT x = " + aMapLocation.getLatitude() + ", y = " + aMapLocation.getLongitude() , Toast.LENGTH_LONG).show();

        if (patrolId != null){
            allPoints.get(allPoints.size() - 1).add(nowLocation);
            pointsField.notifyChange();
            if (this.locationSetCaller != null){
                this.locationSetCaller.call(aMapLocation);
            }
        }

//        if (lastLocation != null && lastLocation.getLat() > 0){
//            float mileage = AMapUtils.calculateLineDistance(nowLocation.toLatLng(), lastLocation.toLatLng());
//            if (mileage < 0.1){
//                return;
//            }
//        }
        lastLocation = nowLocation;
    }

    public String getPatrolId() {
        return patrolId;
    }
}
