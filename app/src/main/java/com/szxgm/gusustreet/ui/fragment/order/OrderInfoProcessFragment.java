package com.szxgm.gusustreet.ui.fragment.order;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.FragmentOrderInfoProcessBinding;
import com.szxgm.gusustreet.model.dto.mobile.OrderFlowTail;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.services.MobileService;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.utils.RetrofitUtil;

/**
 * {@link com.szxgm.gusustreet.ui.activity.workbench.WorkOrderDetailActivity}
 */
public class OrderInfoProcessFragment extends RequestBVFragment<FragmentOrderInfoProcessBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_order_info_process;
    }

    @Override
    public void initView() {
        OrderInfoReq req = getActivity().getIntent().getParcelableExtra("req");
        getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceData(MobileService.class, s -> s.histoicFlowList(req.getProcInsId())), this::onFlowComplete);
    }

    private void onFlowComplete(List<OrderFlowTail> flowTails){
        mViewBinding.setData(flowTails);
    }
}
