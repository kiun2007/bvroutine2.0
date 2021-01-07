package com.szxgm.gusustreet.ui.activity.list;

import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.model.vo.Publicity;
import com.szxgm.gusustreet.model.vo.SentinelSurveillance;
import com.szxgm.gusustreet.net.services.OtherService;
import com.szxgm.gusustreet.ui.activity.ClassifyListActivity;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public class HealthSupervisionListActivity extends ClassifyListActivity<KeyValue<String, Integer>> implements GeneralHandlerController {

    public static final Class clz = HealthSupervisionListActivity.class;

    private int selectType = 0;

    @Override
    protected List<KeyValue<String, Integer>> classifyList() throws Exception {
        List<KeyValue<String, Integer>> list = new LinkedList<>();
        list.add(new KeyValue<>("非法行医", 0));
        list.add(new KeyValue<>("宣传信息", 1));
        return list;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_other_post;
    }

    @Override
    protected String title(KeyValue<String, Integer> item) {
        return item.key();
    }

    @Override
    protected void onSelected(KeyValue<String, Integer> item) {
        selectType = item.value();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        if (selectType == 0){
            return p.callServiceData(OtherService.class, s -> s.getAllSentinelSurveillance());
        }else if (selectType == 1){
            return p.callServiceData(OtherService.class, s -> s.getAllPublicity());
        }
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("事件列表");
        binding.generalSearch.setVisibility(View.GONE);
        setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public ListHandler getHandler() {

        return new ListHandler<Object>(BR.handler, R.layout.list_error_normal){

            @Override
            public int getItemLayout(Object item) {

                if(item instanceof SentinelSurveillance){
                    return R.layout.item_other_post;
                }else if (item instanceof Publicity){
                    return R.layout.item_other_publicity;
                }
                return 0;
            }
        };
    }
}
