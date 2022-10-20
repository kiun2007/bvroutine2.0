package com.szxgm.gusustreet.ui.activity.river;

import android.location.Location;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps2d.LocationSource;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityRiverPatrolBinding;
import com.szxgm.gusustreet.model.dto.river.RiverPatrol;
import com.szxgm.gusustreet.model.dto.river.RiverPlain;
import com.szxgm.gusustreet.service.TrailService;
import com.szxgm.gusustreet.ui.activity.AMap3DActivity;
import com.szxgm.gusustreet.ui.activity.workbench.WorkOrderReportActivityHandler;

import kiun.com.bvroutine.base.binding.variable.AutoImportHandler;
import kiun.com.bvroutine.utils.ContextUtil;
import kiun.com.bvroutine_apt.ActivityOpen;

/**
 * 开始巡河
 */
public class PatrolRiverActivity extends AMap3DActivity<ActivityRiverPatrolBinding> implements LocationSource, AutoImportHandler {

    private RiverPlain riverPlain;

    private RiverPatrol riverPatrol;

    private OnLocationChangedListener onLocationChangedListener;

    private String riverId = null;
    private String riverName = null;

    @Override
    public void initView() {
        super.initView();

        if (riverPlain != null){ //开始新的巡查.
            binding.setRiverPlain(riverPlain);
            getBarItem().setTitle(ContextUtil.getString(this, "title"));
        }else if (riverPatrol != null){ //从服务中获取到历史巡查记录
            getBarItem().setTitle(riverPatrol.getRiverName());
            binding.setPatrolId(riverPatrol.getId());
        }
    }

    @ActivityOpen
    public void openFromRiverPlain(RiverPlain riverPlain){
        this.riverPlain = riverPlain;
        riverId = riverPlain.getId();
        riverName = riverPlain.getRiveRnme();
    }

    @ActivityOpen
    public void openFromRiverPatrol(RiverPatrol riverPatrol){
        this.riverPatrol = riverPatrol;
        riverId = riverPatrol.getRiverId();
        riverName = riverPatrol.getRiverName();
    }

    public void onProblemClick(View view){
        WorkOrderReportActivityHandler.openActivityNormal(this, "30", riverId, riverName, binding.getTrailService().getPatrolId());
    }

    @Override
    public void mapInit(Location location) {

        useLocationButton();
        amap.setLocationSource(this);
        amap.setMyLocationEnabled(true);
    }

    public void onStart(String patrolId){
        binding.setPatrolId(patrolId);
        binding.getTrailService().start(patrolId);
    }

    public void onEnd(String patrolId){
        binding.getTrailService().stop(patrolId);
        Toast.makeText(this, "巡查完成", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public int mapViewId() {
        return R.id.mainMapView;
    }

    @Override
    public int getViewId() {
        return R.layout.activity_river_patrol;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        this.onLocationChangedListener = onLocationChangedListener;
        if (binding.getTrailService() != null){
            binding.getTrailService().setLocationChangedListener(onLocationChangedListener::onLocationChanged);
        }
    }

    @Override
    public void deactivate() {}

    @Override
    public void onImportComplete(Object value) {
        if (value instanceof TrailService){
            binding.getTrailService().setLocationChangedListener(onLocationChangedListener::onLocationChanged);
        }
    }
}
