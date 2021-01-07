package com.szxgm.gusustreet.ui.activity.list;

import android.view.View;

import com.szxgm.gusustreet.model.dto.attendance.WorkTimeTree;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.AttendanceService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.ListUtil;

public class ArriveTimesActivity extends GeneralListActivity {

    public static final Class clz = ArriveTimesActivity.class;

    String type = "0";

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        List<WorkTimeTree> timeTrees = p.callServiceData(AttendanceService.class, s -> s.treeWorkTimes(type));
        return ListUtil.filter(timeTrees, item -> !"0".equals(item.getpId()));
    }

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("排班班次");
        binding.generalSearch.setVisibility(View.GONE);
        type = getIntent().getStringExtra("type");
    }
}
