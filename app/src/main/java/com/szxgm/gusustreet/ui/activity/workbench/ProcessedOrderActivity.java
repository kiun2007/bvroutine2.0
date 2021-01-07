package com.szxgm.gusustreet.ui.activity.workbench;

import android.content.Intent;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.model.dto.mobile.OrderInfoDetailed;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import java.util.List;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

/**
 * 已完成工单.
 */
public class ProcessedOrderActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = ProcessedOrderActivity.class;

    @Override
    public void initView() {
        super.initView();
        getSearchEdit().setHint("输入工单号搜索");
        getBarItem().setTitle("已办工单");
    }

    @Override
    public int getFilterDialogLayout() {
        return R.layout.dialog_filter_his_order;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_mobile_history;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery().field("orderCode").filterFields("params[beginTime]", "params[endTime]");
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        GeneralListQuery query = (GeneralListQuery) bean;
        return p.callServiceList(MobileService.class, s -> s.getHistoricList(query.toSimple()), bean);
    }

    @Override
    public ListHandler getHandler() {
        return new NormalHandler<OrderInfoDetailed>().addTag(1, ((context, data) -> {
            Intent intent = WorkOrderDetailActivityHandler.openActivityIntent(context, new OrderInfoReq(data.getProcInsId()), null);
            context.startActivity(intent);
        }));
    }
}
