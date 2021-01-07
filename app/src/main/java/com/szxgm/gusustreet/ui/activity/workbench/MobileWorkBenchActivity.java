package com.szxgm.gusustreet.ui.activity.workbench;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.TaskDisposalType;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.base.TaskStatus;
import com.szxgm.gusustreet.model.dto.MobileOrder;
import com.szxgm.gusustreet.model.dto.user.PermitTree;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.model.dto.mobile.CombinedTask;
import com.szxgm.gusustreet.model.dto.mobile.OrderTask;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.ClassifyListActivity;
import com.szxgm.gusustreet.utils.RolesUtil;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 移动工作台
 *
 * @author wrf
 * @time 2020-05-31 12:28
 */
public class MobileWorkBenchActivity extends ClassifyListActivity<KeyValue<String, Integer>> implements GeneralHandlerController {

    public static final Class clz = MobileWorkBenchActivity.class;

    @Override
    protected List<KeyValue<String, Integer>> classifyList() throws Exception {
        List<KeyValue<String, Integer>> list = new LinkedList<>();
        list.add(new KeyValue<>("待办工单", 0));

        if (RolesUtil.minimum(PermitTree.WORK_STATION_ADMIN)){
            list.add(new KeyValue<>("疑难工单", 1));
        }
        list.add(new KeyValue<>("综合协调", 2));
        return list;
    }

    @Override
    protected String title(KeyValue<String, Integer> item) {
        return item.key();
    }

    @Override
    protected void onSelected(KeyValue<String, Integer> item) {
        selectByIndex(item.value());
    }

    private void selectByIndex(int index){
        general.getQuery().put("orderListType", QueryType.Eq, String.valueOf(index));
        general.getQuery().setItemClz(index < 2 ? OrderTask.class : CombinedTask.class);
    }

    @Override
    public void initView() {
        super.initView();

        getBarItem().setTitle("移动工作台");
        getSearchEdit().setHint("输入工单号搜索");
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
                .field("orderCode")
                .filterFields("endTime","startTime")
                .put("orderListType", QueryType.Eq, String.valueOf(getIntent().getIntExtra("index", 0)));
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        GeneralListQuery query = (GeneralListQuery) bean;
        User user = SharedUtil.getValue("User", new User());
        query.put("userId", QueryType.Eq, user.getUserId());
        return rbp.callServiceList(MobileService.class, s->s.getOrderToDoList(query.toSimple(), query.getItemClz().getName()), bean);
    }

    private void onClaimOk(String value){
        general.refresh();
        Toast.makeText(this, "签收成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public ListHandler getHandler() {

        return new ListHandler(BR.handler, R.layout.list_error_normal){

            @Override
            public void onClick(Context context, int tag, Object item) {

                Intent intent = null;
                if (item instanceof OrderTask){
                    OrderTask data = (OrderTask) item;
                    if (tag == 0){
                        WorkOrderDetailActivityHandler.openActivityNormal(context, new OrderInfoReq(data), null);
                    }else if (tag == 1){

                        if (data.getStatus() == TaskStatus.todo){
                            intent = WorkOrderDetailActivityHandler.openActivityIntent(context, new OrderInfoReq(data), data.getTaskDefKey());
                            intent.putExtra("isTimeOut", data.getVars().getMap().isTimeOut());
                        }else{
                            AlertUtil.build(context, "是否签收任务\"%s\"", data.getVars().getMap().getOrderTitle())
                                    .setPositiveButton("签收", (dialog, which) -> {
                                        rbp.addRequest(()->rbp.callServiceData(MobileService.class, s -> s.claimOrderForStreet(data.getTaskId())),
                                                MobileWorkBenchActivity.this::onClaimOk);
                                    }).setNegativeButton("不签收", null).show();
                        }
                    }
                }else if (item instanceof CombinedTask){
                    CombinedTask data = (CombinedTask) item;

                    if (tag == 0){
                        WorkOrderDetailActivityHandler.openActivityNormal(context, new OrderInfoReq(data.getId(), data.getOrderInfo().getProcInsId()), null);
                    }else{
                        //处置
                        intent = WorkOrderDetailActivityHandler.openActivityIntent(context, new OrderInfoReq(data.getId(), data.getOrderInfo().getProcInsId()), TaskDisposalType.unionDisposal.toString());
                    }
                }

                if (intent != null){
                    startForResult(intent, (v)->{
                        general.refresh();
                    });
                }
            }

            @Override
            public int getItemLayout(Object item) {
                if (item instanceof OrderTask){
                    return R.layout.item_mobile_workbench;
                }else if (item instanceof CombinedTask){
                    return R.layout.item_mobile_workbench_combined;
                }
                return super.getItemLayout(item);
            }
        };
    }

    public static Intent create(Context context, int tabIndex){
        return new Intent(context, MobileWorkBenchActivity.clz).putExtra("index", tabIndex);
    }
}
