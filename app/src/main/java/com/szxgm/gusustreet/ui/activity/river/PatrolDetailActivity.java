package com.szxgm.gusustreet.ui.activity.river;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityRiverPatrolDetailBinding;
import com.szxgm.gusustreet.model.dto.PatrolLog;
import com.szxgm.gusustreet.model.dto.PatrolPoints;
import com.szxgm.gusustreet.model.dto.river.RiverPatrol;
import com.szxgm.gusustreet.net.services.RiverService;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine_apt.IntentValue;

public class PatrolDetailActivity extends RequestBVActivity<ActivityRiverPatrolDetailBinding> {

    @IntentValue
    RiverPatrol riverPatrol;

    @IntentValue
    boolean edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.mainMapView.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {

        binding.setRiverPatrol(riverPatrol);
        binding.setEdit(edit);

        if (edit && getBarItem() != null) {
            getBarItem().setRightTitle("提交");
        }

        rbp.addRequest(this::loadData, binding::setPatrolLog);
    }

    private PatrolLog loadData() throws Exception{

        PatrolLog patrolLog = rbp.callServiceData(RiverService.class, s->s.getPatrolLog(riverPatrol.getId()));
        List<PatrolPoints> points = rbp.callServiceData(RiverService.class, s -> s.getPoints(riverPatrol.getId()));
        binding.setPatrolPoints(points);
        return patrolLog;
    }

    public void onComplete(String data){
        Toast.makeText(this, "日志提交成功", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public int getViewId() {
        return R.layout.activity_river_patrol_detail;
    }
}
