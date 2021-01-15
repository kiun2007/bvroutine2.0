package com.szxgm.gusustreet.ui.activity.list;

import android.view.View;

import com.szxgm.gusustreet.model.dto.ClockTimeTree;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.AttendanceService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.ListUtil;

public class BadClockListActivity extends GeneralListActivity {

    public static Class clz = BadClockListActivity.class;

    /**
     * 申报类型 1迟到 2早退
     */
    String type;

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("异常打卡记录");
        binding.generalSearch.setVisibility(View.GONE);
        type = getIntent().getStringExtra("type");
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        if (bean.getPageNum() > 1){
            return null;
        }
        List<ClockTimeTree> clockTimeTrees = p.callServiceData(AttendanceService.class, s -> s.treeClock(type));
        return ListUtil.filter(clockTimeTrees, item -> !"0".equals(item.getpId()));
    }

    @Override
    public PagerBean getQuery() {
        return new GeneralListQuery();
    }
}
