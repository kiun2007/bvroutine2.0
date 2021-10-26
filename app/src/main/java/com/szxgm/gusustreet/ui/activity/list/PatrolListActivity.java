package com.szxgm.gusustreet.ui.activity.list;

import android.content.Context;
import android.view.View;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.dto.river.RiverPatrol;
import com.szxgm.gusustreet.model.dto.river.RiverPlain;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import com.szxgm.gusustreet.ui.activity.river.PatrolDetailActivityHandler;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 巡查记录列表.
 */
public class PatrolListActivity extends GeneralListActivity implements GeneralHandlerController {

    @IntentValue
    RiverPlain riverPlain;

    @Override
    public void initView() {
        binding.generalSearch.setVisibility(View.GONE);
        getBarItem().setTitle("巡查记录");
        super.initView();
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_river_record;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(RiverPatrol.class)
                .put("riverId", QueryType.Eq, riverPlain.getId()).field("riverName").orderBy("CREATE_DATE", QueryType.Desc);
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        List<RiverPatrol> riverPatrols = p.callServiceList(GeneralListService.class, s -> s.search(bean), bean);
        for (RiverPatrol item : riverPatrols){
            item.setRiverName(riverPlain.getRiveRnme());
            item.setImage(riverPlain.getImage());
        }
        return riverPatrols;
    }

    @Override
    public ListHandler getHandler() {
        return new ListHandler<RiverPatrol>(BR.handler, R.layout.list_error_normal){
            @Override
            public void onClick(Context context, int tag, RiverPatrol data) {
                startForResult(PatrolDetailActivityHandler.openActivityIntent(getContext(), data, tag == 2),
                        intent->{
                            general.refresh();
                });
            }
        };
    }
}
