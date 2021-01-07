package com.szxgm.gusustreet.ui.activity.river;

import android.graphics.Color;
import android.location.Location;
import android.text.TextUtils;
import android.widget.Toast;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolylineOptions;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityRiverDetailBinding;
import com.szxgm.gusustreet.model.dto.river.RiverDetail;
import com.szxgm.gusustreet.model.dto.river.RiverItem;
import com.szxgm.gusustreet.net.services.RiverService;
import com.szxgm.gusustreet.ui.activity.AMapActivity;
import com.szxgm.gusustreet.utils.LatLngUtil;
import java.util.List;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.LocationUtils;
import kiun.com.bvroutine_apt.IntentValue;

public class RiverDetailActivity extends AMapActivity<ActivityRiverDetailBinding> {

    @IntentValue
    RiverItem riverItem;

    @Override
    public void mapInit(Location location) {
        rbp.addRequest(this::getRiverDetail, value->{
            if (value == null){
                Toast.makeText(getContext(), "获取详情失败", Toast.LENGTH_SHORT).show();
                return;
            }
            value.setArea(riverItem.getAreaName());
            binding.setRiverDetail(value);
            if (!TextUtils.isEmpty(value.getLnglat())){

                List<LatLng> latLngs = ListUtil.toList(LocationUtils.listFromJSON(value.getLnglat()), LatLngUtil::fromGPS);

                if (latLngs.size() > 0){
                    moveCamera(latLngs.get(0));
                    addMarker(latLngs.get(0), riverItem.getImage(), String.format("%s(%s)",riverItem.getRivername(),riverItem.getAreaName()));
                }

                amap.addPolyline(new PolylineOptions().width(8).color(Color.GREEN).addAll(latLngs));
                amap.invalidate();
            }
        });
    }

    private RiverDetail getRiverDetail() throws Exception {
        return rbp.callServiceData(RiverService.class, s -> s.getRiverDetail(riverItem.getRiverId()));
    }

    @Override
    public int mapViewId() {
        return R.id.mainMapView;
    }

    @Override
    public void onLocation(Location location) {

    }

    @Override
    public int getViewId() {
        return R.layout.activity_river_detail;
    }
}
