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
 * 我的审批.
 */
public class MineApprovalActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = MineApprovalActivity.class;

    @Override
    public int getItemLayout() {
        return R.layout.item_apply;
    }

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("我的审批");
        binding.generalSearch.setVisibility(View.GONE);
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        return p.callServiceList(GeneralListService.class, s -> s.allxjGetList(bean), bean);
    }

    @Override
    public ListHandler getHandler() {
        return new RefreshHandler<MineApply>(general, this){
            @Override
            public int getItemLayout(MineApply item) {
                return item.getLayout();
            }
        }.addTag(0, ApplyDetailActivityHandler::openActivityIntent);
    }
}
