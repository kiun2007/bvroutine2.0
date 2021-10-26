package com.szxgm.gusustreet.ui.activity.list;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.dto.mobile.AreaRiverOffice;
import com.szxgm.gusustreet.model.dto.mobile.AreaRiverStreet;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import java.util.Arrays;
import java.util.List;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.MapUtil;
import kiun.com.bvroutine.utils.RetrofitUtil;

import static kiun.com.bvroutine.views.text.GeneralItemText.*;

/**
 * 区河长制处置部门列表.
 */
public class RiverAreaOfficeListActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = RiverAreaOfficeListActivity.class;
    private String riverId;
    private String orderType;

    @Override
    public int getItemLayout() {
        return R.layout.item_river_office;
    }

    @Override
    public void initView() {
        getBarItem().setTitle("请选择处置部门");
        riverId = getIntent().getStringExtra("riverId");
        orderType = getIntent().getStringExtra("orderType");
        if (riverId == null || orderType == null){
            Toast.makeText(this, "请先选择河流和事项", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        super.initView();
        binding.generalSearch.setVisibility(View.GONE);
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery();
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        if (bean.getPageNum() == 1){
            AreaRiverOffice areaRiverOffice = RetrofitUtil.callServiceData(MobileService.class, s -> s.riverOfficeList(MapUtil.as("riverId", riverId, "orderType", orderType)));
            return Arrays.asList(areaRiverOffice);
        }
        return null;
    }

    @Override
    public ListHandler getHandler() {

        return new ListHandler<Object>(BR.handler, R.layout.list_error_normal){
            @Override
            public void onClick(Context context, int tag, Object item) {

                AreaRiverOffice office = null;
                if (item instanceof AreaRiverOffice){
                    office = (AreaRiverOffice) item;
                }else if (item instanceof AreaRiverStreet){

                    AreaRiverStreet street = (AreaRiverStreet) item;

                    if (street.getAreaRiverOffice() != null){
                        street.getAreaRiverOffice().setStreets(Arrays.asList(street));
                        office = street.getAreaRiverOffice();
                    }
                }

                if (office != null){
                    Intent intent = new Intent().putExtra(ID, office.getDisposeOffice().getOfficeId());

                    if ("1".equals(office.getDisposeOffice().getIsUnderclass())){
                        intent.putExtra(TITLE, office.getStreets().get(0).getStreetIdName());
                    }else{
                        intent.putExtra(TITLE, office.getDisposeOffice().getOfficeName());
                    }

                    intent.putExtra(EXTRA, office);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }
}
