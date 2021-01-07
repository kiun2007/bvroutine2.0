package com.szxgm.gusustreet.ui.activity.river;

import android.content.Context;
import android.widget.Toast;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityRiverProblemCreateBinding;
import com.szxgm.gusustreet.model.dto.river.RiverOrder;
import com.szxgm.gusustreet.ui.activity.CommitBaseActivity;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: RiverProblem
 * 作 者: 刘春杰
 * 创建日期: 2020/6/1 00:14
 * 说明: 发现问题
 */
public class RiverProblemActivity extends CommitBaseActivity<ActivityRiverProblemCreateBinding> {

    public static final Class clz = RiverProblemActivity.class;

    @IntentValue
    public String riverId;

    @IntentValue
    public String patrolId;

    @IntentValue
    public String name;

    @Override
    public int getViewId() {
        return R.layout.activity_river_problem_create;
    }

    @Override
    public void initView() {

        RiverOrder riverOrder = new RiverOrder();
        riverOrder.setIncidentRiver(riverId);
        riverOrder.setPatrolId(patrolId);
        riverOrder.setRiverName(name);

        binding.setRiverFixed(riverId != null);
        setVariable(BR.riverOrder, riverOrder);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void onComplete(String id){
        Toast.makeText(getApplicationContext(), "问题提交成功", Toast.LENGTH_LONG).show();
        finish();
    }
}