package com.szxgm.gusustreet.ui.activity.workbench;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.material.tabs.TabLayout;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.base.OrderActivityType;
import com.szxgm.gusustreet.model.dto.MobileOrder;
import com.szxgm.gusustreet.model.dto.mobile.ActivityOrderItem;
import com.szxgm.gusustreet.model.dto.mobile.CombinedTask;
import com.szxgm.gusustreet.model.dto.mobile.OrderTask;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.ClassifyListActivity;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

/**
 * 移动工作台
 *
 * @author wrf
 * @time 2020-05-31 12:28
 */
public class MobileWorkBenchActivity extends ClassifyListActivity<KeyValue<String, Integer>> implements GeneralHandlerController {

    public static final Class clz = MobileWorkBenchActivity.class;

    private static final String KEY_ACTIVITY = "activity";

    private AlertDialog alertDialog = null;

    @Override
    protected List<KeyValue<String, Integer>> classifyList() throws Exception {
        List<KeyValue<String, Integer>> list = new LinkedList<>();
        list.add(new KeyValue<>("待处理", 0));

        String activity = general.getQuery() == null ? null : general.getQuery().findValue(KEY_ACTIVITY);
        if (!OrderActivityType.grid.getActivity().equals(activity)){
            list.add(new KeyValue<>("待核查", 1));
        }
        return list;
    }

    @Override
    protected String title(KeyValue<String, Integer> item) {
        return item.key();
    }

    @Override
    protected void onSelected(KeyValue<String, Integer> item) {

        general.getQuery().put("index", String.valueOf(item.value()));
        String activity = general.getQuery().findValue(KEY_ACTIVITY);

        if (activity != null){
            OrderActivityType type = OrderActivityType.getType(activity, item.value() == 1);
            general.getQuery().put(KEY_ACTIVITY, QueryType.Eq, type.getActivity());
        }
    }

    @Override
    public void initView() {
        super.initView();

        getBarItem().setTitle("移动工作台");
        getSearchEdit().setHint("输入关键词或工单号搜索");
        setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected boolean isSelect(int index, KeyValue<String, Integer> item) {
        return getIntent().getIntExtra("index", 0) == index;
    }

    @Override
    public int getFilterDialogLayout() {
        return R.layout.dialog_filter_order;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_mobile_workbench;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(MobileOrder.class)
                .field("searchValue")
                .filterFields("endTime", "startTime", "orderType", "typeName")
                .put(KEY_ACTIVITY, QueryType.Eq, OrderActivityType.ottff.getActivity())
                .putEvent(this::onFieldChanged);
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        GeneralListQuery query = (GeneralListQuery) bean;
        return rbp.callServiceList(MobileService.class, s->s.getActivityOrder(query.findValue(KEY_ACTIVITY), query.toSimple()), bean);
    }

    private boolean onFieldChanged(String field){
        if ("searchValue".equals(field)){

            GeneralListQuery.Query query = general.getQuery().find(field, QueryType.Like);
            String value = query == null ? null : query.getValue();
            if (!TextUtils.isEmpty(value)){
                if (value.matches("^[A-Z]{2}\\d{1,5}$") || value.matches("\\d{1,5}$")){
                    //存在歧义, 询问用户.
                    if (alertDialog != null){
                        return true;
                    }
                    alertDialog = new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("当前查询存在歧义,请问您是查询?")
                            .setNegativeButton("查工单", (dialog, v)->{
                                general.getQuery()
                                        .put(OrderActivityType.isOther(general.getQuery().findValue(KEY_ACTIVITY)) ? "duijieId" : "orderCode", value);
                                general.refresh();
                                alertDialog = null;
                            })
                            .setPositiveButton("查关键词", (dialog, v)->{
                                general.getQuery().put("keyWord", value);
                                general.refresh();
                                alertDialog = null;
                            }).show();
                    return false;
                }else if (value.matches("^[A-Z]{2}\\d{5,20}$") || value.matches("\\d{5,24}$")){
                    //不存在歧义，查询工单号
                    general.getQuery()
                            .put(OrderActivityType.isOther(general.getQuery().findValue(KEY_ACTIVITY)) ? "duijieId" : "orderCode", value);
                }else {
                    //不存在歧义，查询关键词
                    general.getQuery().put("keyWord", value);
                }
            }else {
                general.getQuery().clear("duijieId");
                general.getQuery().clear("orderCode");
                general.getQuery().clear("keyWord");
            }
        }
        return true;
    }

    @Override
    protected void onRefresh() {
        reloadTab();
    }

    @Override
    public ListHandler getHandler() {

        return new ListHandler<ActivityOrderItem>(BR.handler, R.layout.list_error_normal){

            @Override
            public void onClick(Context context, int tag, ActivityOrderItem item) {

                Intent intent = WorkOrderDetailActivityHandler.openActivityIntent(context,
                        new OrderInfoReq(item.getOrderId(), item.getTaskId(), item.getTaskDefKey(), item.getProcInsId(), "todo"), item.getTaskDefKey());

                if (intent != null){
                    startForResult(intent, (v)->{
                        general.refresh();
                    });
                }
            }
        };
    }

    public static Intent create(Context context, int tabIndex){
        return new Intent(context, MobileWorkBenchActivity.clz).putExtra("index", tabIndex);
    }
}
