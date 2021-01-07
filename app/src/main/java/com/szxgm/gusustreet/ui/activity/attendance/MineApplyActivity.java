package com.szxgm.gusustreet.ui.activity.attendance;

import android.view.View;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.list.handler.RefreshHandler;
import com.szxgm.gusustreet.model.dto.MineApply;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

/**
 * 文 件 名: MineApply
 * 作 者: 刘春杰
 * 创建日期: 2020/5/19 15:31
 * 说明: 我的申请
 */
public class MineApplyActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz  = MineApplyActivity.class;

    @Override
    public int getItemLayout() {
        return R.layout.item_apply;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        return p.callServiceList(GeneralListService.class, s -> s.allGetList(bean), bean);
    }

    @Override
    public void initView() {
        super.initView();
        binding.generalSearch.setVisibility(View.GONE);
        getBarItem().setTitle("我的申请");
    }

    @Override
    public ListHandler getHandler() {
        return new RefreshHandler<MineApply>(general, this){
            
        }.addTag(0, MineApplyDetailActivityHandler::openActivityIntent);
    }
}
