package com.szxgm.gusustreet.ui.fragment.order;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.CoordinateConverter;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.FragmentOrderInfoMapBinding;
import com.szxgm.gusustreet.model.dto.mobile.OrderInfoDetailed;
import com.szxgm.gusustreet.utils.LatLng3DUtil;
import com.szxgm.gusustreet.utils.LatLngUtil;

import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.interfaces.view.SaveBindingView;
import kiun.com.bvroutine.utils.LocationUtils;

import static com.szxgm.gusustreet.model.base.StaticConst.ORDER_INFO;

/**
 * {@link com.szxgm.gusustreet.ui.activity.workbench.WorkOrderDetailActivity}
 */
public class OrderInfoMapFragment extends RequestBVFragment<FragmentOrderInfoMapBinding> implements SaveBindingView {

    @Override
    public int getViewId() {
        return R.layout.fragment_order_info_map;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mViewBinding.mainMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onEvent(Integer tag, Object value) {

        if (value instanceof OrderInfoDetailed){
            OrderInfoDetailed orderInfoDetailed = (OrderInfoDetailed) value;

            AMap aMap = mViewBinding.mainMapView.getMap();
            LatLng latLng = LatLng3DUtil.convert(LocationUtils.addLocation(orderInfoDetailed.getGisy(), orderInfoDetailed.getGisx()), CoordinateConverter.CoordType.GPS);
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition(latLng, 18, 30, 30))
            );

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.order_mark_icon));
            markerOptions.position(latLng);
            markerOptions.title("事件发生位置");
            aMap.addMarker(markerOptions);
        }
    }
}
