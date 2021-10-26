package com.szxgm.gusustreet.ui.activity.list;

import android.view.View;

import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.AttendanceService;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public class ColleagueListActivity extends GeneralListActivity {

    public static final Class clz = ColleagueListActivity.class;

    String workTimeId;

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("选被调班次人员");
        binding.generalSearch.setVisibility(View.GONE);
        workTimeId = getIntent().getStringExtra("workTimeId");
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        if (bean.getPageNum() > 1){
            return null;
        }
        return p.callServiceData(AttendanceService.class, s -> s.treeUser(workTimeId));
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }
}
