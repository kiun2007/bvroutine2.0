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
import kiun.com.bvroutine.utils.ListUtil;

/**
 * 被调班审批.
 */
public class MineReplaceApprovalActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = MineReplaceApprovalActivity.class;

    @Override
    public int getItemLayout() {
        return R.layout.item_approval_replace;
    }

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("被调班审批");
        binding.generalSearch.setVisibility(View.GONE);
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        List<MineApply> mineApplies = p.callServiceList(GeneralListService.class, s -> s.secondTbList(bean), bean);
        ListUtil.map(mineApplies, item->{
            item.setName(item.getTbmenbefore());
            item.setReplace(true);
        });
        return mineApplies;
    }

    @Override
    public ListHandler getHandler() {
        return new RefreshHandler<MineApply>(general, this)
                .addTag(0, ApplyDetailActivityHandler::openActivityIntent);
    }
}
