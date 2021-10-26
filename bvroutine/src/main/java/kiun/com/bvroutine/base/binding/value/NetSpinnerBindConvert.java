package kiun.com.bvroutine.base.binding.value;

import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.viewmodel.SpinnerDataWrap;
import kiun.com.bvroutine.data.viewmodel.SpinnerItemVal;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.RetrofitUtil;
import retrofit2.Call;

public class NetSpinnerBindConvert extends SpinnerBindConvert<Object> implements AdapterView.OnItemSelectedListener {

    public static final Class clz = NetSpinnerBindConvert.class;
    private int itemLayoutId;
    private int dropDownViewId;

    @Override
    public boolean isOnlySet() {
        //只需要set不需要get
        return true;
    }

    public NetSpinnerBindConvert(Spinner view, @NonNull Call call, int itemLayoutId, int dropDownViewId) {
        super(view);

        this.itemLayoutId = itemLayoutId;
        this.dropDownViewId = dropDownViewId;

        if (view.getContext() instanceof RequestBVActivity){
            RequestBVActivity activity = (RequestBVActivity) view.getContext();
            RequestBindingPresenter p = activity.getRequestPresenter();
            p.addRequest(()->RetrofitUtil.unpackWrap(null, call.execute()), this::onListComplete);
        }
    }

    private void onListComplete(List<? extends SpinnerItemVal> list){
        setValue(new SpinnerDataWrap<>(list, itemLayoutId, dropDownViewId).listener(this::onSelected));
    }

    private void onSelected(SpinnerDataWrap<SpinnerItemVal> dataWrap){
        nowValue = dataWrap.getSelectedItem().value();
    }

    public static class Builder extends BindConvertBuilder<Spinner>{

        private Call netCall;
        private int itemLayoutId;
        private int dropDownViewId;

        public Builder(Call netCall, int itemLayoutId, int dropDownViewId) {
            this.netCall = netCall;
            this.itemLayoutId = itemLayoutId;
            this.dropDownViewId = dropDownViewId;
        }

        @Override
        public BindConvert<Spinner, ?, ?> build(Spinner view) {
            return new NetSpinnerBindConvert(view, netCall, itemLayoutId, dropDownViewId);
        }
    }

    public static Builder create(Call netCall, int itemLayoutId, int dropDownViewId){
        return new Builder(netCall, itemLayoutId, dropDownViewId);
    }
}
