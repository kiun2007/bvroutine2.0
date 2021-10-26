package com.szxgm.gusustreet.ui.fragment.order;

import android.app.Activity;
import android.widget.Toast;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.TaskDisposalType;
import com.szxgm.gusustreet.databinding.FragmentOrderInfoBaseBinding;
import com.szxgm.gusustreet.model.dto.mobile.CombinedTaskInfo;
import com.szxgm.gusustreet.model.dto.mobile.OrderInfoDetailed;
import com.szxgm.gusustreet.model.dto.mobile.OrderTask;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.requests.mobile.TaskDisposalReq;
import com.szxgm.gusustreet.net.requests.mobile.TaskUnionDisposalReq;
import com.szxgm.gusustreet.net.services.MobileService;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.utils.RetrofitUtil;

/**
 * {@link com.szxgm.gusustreet.ui.activity.workbench.WorkOrderDetailActivity}
 */
public class OrderInfoBaseFragment extends RequestBVFragment<FragmentOrderInfoBaseBinding> {

    TaskDisposalType type;

    TaskDisposalReq disposalReq;

    @Override
    public int getViewId() {
        return R.layout.fragment_order_info_base;
    }

    @Override
    public void initView() {

        OrderInfoReq req = getActivity().getIntent().getParcelableExtra("req");

        if (req.isHis()){
            getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceData(MobileService.class, s -> s.getHistoryOrderInfo(req)), this::onGetTaskComplete);
            return;
        }

        if (req.isCombined()){
            getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceData(MobileService.class, s -> s.getHarmonizeDetail(req)), this::onGetCombinedTask);
        }else {
            getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceData(MobileService.class, s -> s.getTaskInfo(req)), this::onGetTaskComplete);
        }

        String taskDefKey = getActivity().getIntent().getStringExtra("taskDefKey");

        //需要处置.
        if (taskDefKey != null){
            type = TaskDisposalType.valueOf(taskDefKey);
            mViewBinding.setType(type);

            if (req.isCombined()){
                return;
            }
            setVariable(BR.data, disposalReq = new TaskDisposalReq(req));
        }
    }

    private void setOrder(OrderInfoDetailed order){
        mViewBinding.setOrder(order);
        if (disposalReq != null){
            disposalReq.setAppealType(order.getAppealType());
            disposalReq.setDifficultSheet(order.getDifficultSheet());
            disposalReq.setDifficult(!"0".equals(order.getDifficult()));
            disposalReq.setTimeOut(getActivity().getIntent().getBooleanExtra("isTimeOut", false));
            setVariable(BR.data, disposalReq);
        }
    }

    private void onGetTaskComplete(OrderTask taskInfo){
        setOrder(taskInfo.getXtczOrderInfo());
        putEvent(0, taskInfo.getXtczOrderInfo());
    }

    private void onGetCombinedTask(CombinedTaskInfo combinedTask){
        mViewBinding.setData(new TaskUnionDisposalReq(combinedTask.getId(), combinedTask.getHandleContent()));
        setOrder(combinedTask.getOrderInfo());
        putEvent(0, combinedTask.getOrderInfo());
    }

    public void onComplete(Object object){
        Toast.makeText(getContext(), "处理完成", Toast.LENGTH_LONG).show();
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
