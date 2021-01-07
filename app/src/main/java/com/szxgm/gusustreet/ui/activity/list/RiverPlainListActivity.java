package com.szxgm.gusustreet.ui.activity.list;

import android.app.AlertDialog;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.list.handler.RefreshHandler;
import com.szxgm.gusustreet.model.dto.river.RiverPlain;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import com.szxgm.gusustreet.ui.activity.river.PatrolRiverActivityHandler;
import kiun.com.bvroutine.handlers.ListHandler;

public class RiverPlainListActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = RiverPlainListActivity.class;

    @Override
    public int getItemLayout() {
        return R.layout.item_river_plain;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(RiverPlain.class).field("riveRnme");
    }

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("巡查管理");
    }

    @Override
    public ListHandler getHandler() {
        RefreshHandler<RiverPlain> refreshHandler = new RefreshHandler<>(general, this);
        refreshHandler.addTag(1, ((context, riverPlain) -> {
            if (binding.getTrailService().getRiverPatrol() != null){
                new AlertDialog.Builder(this).setTitle("提示").setMessage("发现有未完成的巡查,请完成上次巡查")
                        .setNegativeButton("关闭页面", (dialog, which) -> finish())
                        .setPositiveButton("继续巡查",(dialog, which) -> {
                            PatrolRiverActivityHandler.openFromRiverPatrol(getContext(), binding.getTrailService().getRiverPatrol());
                        }).setCancelable(false).show();
                return null;
            }
            return PatrolRiverActivityHandler.openFromRiverPlainIntent(context, riverPlain);
        }));

        refreshHandler.addTag(2, PatrolListActivityHandler::openActivityIntent);
        return refreshHandler;
    }
}
