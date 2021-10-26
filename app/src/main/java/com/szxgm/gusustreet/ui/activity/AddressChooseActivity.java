package com.szxgm.gusustreet.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityAddressChooseBinding;
import com.szxgm.gusustreet.model.base.PoiChooseItem;
import com.szxgm.gusustreet.utils.LatLngUtil;

import java.util.ArrayList;
import java.util.List;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.presenters.RecyclerListPresenter;
import kiun.com.bvroutine.utils.ListUtil;

import static com.szxgm.gusustreet.views.GeneralItemText.EXTRA;
import static com.szxgm.gusustreet.views.GeneralItemText.TITLE;

public class AddressChooseActivity extends AMapActivity<ActivityAddressChooseBinding> implements ListRequestView<PagerBean,Object>, AMap.OnCameraChangeListener {

    public static final Class clz = AddressChooseActivity.class;

    ListViewPresenter listViewPresenter = null;

    LatLonPoint searchPoint;

    PoiChooseItem lastChooseItem;

    Marker centerMarker;

    GeocodeSearch geocoderSearch;

    private boolean isNoTitle = false;

    @Override
    public void initView() {
        super.initView();
        getBarItem().getHandler().setRightCallBack(()->{
            if (lastChooseItem != null){
                Intent intent = new Intent().putExtra(EXTRA, lastChooseItem.getPoiItem());

                if (!isNoTitle){
                    intent.putExtra(TITLE, lastChooseItem.getPoiItem().getSnippet());
                }
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        if ("1".equals(getIntent().getStringExtra("NoTitle"))){
            isNoTitle = true;
        }

        geocoderSearch = new GeocodeSearch(this);
    }

    @Override
    public void mapInit(Location location) {

        if (listViewPresenter == null){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            searchPoint = new LatLonPoint(latLng.latitude, latLng.longitude);

            listViewPresenter = new RecyclerListPresenter(binding.mainList, binding.mainRefresh);
            listViewPresenter.initRequest(new PagerBean(), this);
            listViewPresenter.start(handler, R.layout.item_poi_item, BR.item, getRequestPresenter());

            moveCamera(latLng);
            centerMarker = addMarker(latLng, BitmapDescriptorFactory.HUE_RED, "搜索附近");

            amap.setOnCameraChangeListener(this);
        }
    }

    ListHandler<PoiChooseItem> handler = new ListHandler<PoiChooseItem>(BR.handler,R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, PoiChooseItem data) {

            data.setChose(true);
            if (lastChooseItem != null && data != lastChooseItem){
                lastChooseItem.setChose(false);
            }
            getBarItem().setRightTitle("确定");
            lastChooseItem = data;
        }

        @Override
        public boolean itemChose(PoiChooseItem item) {
            if (lastChooseItem != null && item != null
                    && item.getPoiItem().getPoiId().equals(lastChooseItem.getPoiItem().getPoiId())){
                lastChooseItem = item;
                return true;
            }
            return false;
        }
    };

    @Override
    public int mapViewId() {
        return R.id.mainMapView;
    }

    @Override
    public void onLocation(Location location) {
    }

    @Override
    public int getViewId() {
        return R.layout.activity_address_choose;
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        PoiSearch.Query query = new PoiSearch.Query("", "", "");

        query.setPageNum(bean.getPageNum());
        query.setPageSize(bean.getPageSize());

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(searchPoint, 500, true));
        PoiResult result = poiSearch.searchPOI();

        ArrayList<PoiItem> poiItems = result.getPois();
        bean.setPages(result.getPageCount());

        if(bean.getPageNum() == 1){
            //先获取到当前位置.
            RegeocodeQuery regeocodeQuery = new RegeocodeQuery(searchPoint, 100, GeocodeSearch.AMAP);
            RegeocodeAddress regeocodeAddress = geocoderSearch.getFromLocation(regeocodeQuery);

            poiItems.add(0, new PoiItem("标注位置", searchPoint, "标注位置", regeocodeAddress.getFormatAddress()));
        }

        return ListUtil.toList(poiItems, item->new PoiChooseItem(item));
    }

    @Override
    public void loadComplete(ListViewPresenter p) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        centerMarker.setPosition(cameraPosition.target);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        searchPoint.setLatitude(cameraPosition.target.latitude);
        searchPoint.setLongitude(cameraPosition.target.longitude);

        listViewPresenter.reload();
    }
}
