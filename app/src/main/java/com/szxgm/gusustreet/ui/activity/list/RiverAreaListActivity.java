package com.szxgm.gusustreet.ui.activity.list;

import android.view.View;

import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.RetrofitUtil;

/**
 * 区河长制河段列表.
 */
public class RiverAreaListActivity extends GeneralListActivity {

    public static final Class clz = RiverAreaListActivity.class;

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("请选择河段");
        binding.generalSearch.setVisibility(View.GONE);
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        if (bean.getPageNum() == 1){
            return RetrofitUtil.callServiceData(MobileService.class, s -> s.riverTreeData());
        }
        return null;
    }
}
