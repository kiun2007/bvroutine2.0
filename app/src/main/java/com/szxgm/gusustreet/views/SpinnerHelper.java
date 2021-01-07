package com.szxgm.gusustreet.views;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.loader.app.LoaderManager;
import android.widget.Spinner;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.dto.Dict;

import java.util.List;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.data.SpinnerValue;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ServiceRequestView;
import kiun.com.bvroutine.interfaces.view.SpinnerView;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.presenters.RetrofitRequestPresenter;
import kiun.com.bvroutine.presenters.SpinnerPresenter;
import retrofit2.Call;

public class SpinnerHelper implements SpinnerView, ServiceRequestView {

    public static class Src<T> {
        GetCaller<T, String> labelCaller;
        GetCaller<T, String> valueCaller;
        Call call;
    }

    private Context context;
    private Src src;
    private RequestBindingPresenter p;

    public static final int TAG = R.id.tagSpinner;

    public SpinnerHelper(Context context, Src src){
        this.context = context;
        this.src = src;
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }

    @Override
    public RequestBindingPresenter getRequestPresenter() {
        if (p != null){
            return p;
        }
        return p = new RetrofitRequestPresenter(this);
    }

    @Override
    public LoaderManager getSupportLoaderManager() {

        if(context instanceof BVBaseActivity){
            return ((AppCompatActivity) context).getSupportLoaderManager();
        }
        return null;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public static<T> Src createSrc(GetCaller<T, String> labelCaller, GetCaller<T, String> valueCaller, Call call){
        Src src = new Src();

        src.labelCaller = labelCaller;
        src.valueCaller = valueCaller;
        src.call = call;
        return src;
    }

    public static Src createDict(Call call){
        return createSrc(Dict.labelGet(), Dict.valueGet(), call);
    }

    private SpinnerValue getType(Object bean) throws Exception{

        DataWrap<List> dataWarp = p.execute(src.call);
        return new SpinnerValue<>(0, dataWarp.getData(), src.labelCaller);
    }

    private static SpinnerPresenter getHelper(Spinner spinner){

        if (!(spinner.getTag(TAG) instanceof SpinnerPresenter)){

            SpinnerHelper spinnerHelper = new SpinnerHelper(spinner.getContext(), null);
            SpinnerPresenter presenter = new SpinnerPresenter(R.layout.item_spinner_text, spinnerHelper, spinner);
            spinner.setTag(TAG, presenter);
        }
        return (SpinnerPresenter) spinner.getTag(TAG);
    }

    @BindingAdapter("src")
    public static void setRequest(Spinner spinner, Src src){
        SpinnerPresenter presenter = getHelper(spinner);
        SpinnerHelper helper = (SpinnerHelper) presenter.getSpinnerView();

        if (helper != null && helper.src == null){
            helper.src = src;
            presenter.setGetter(0, helper::getType).setNormal(src.valueCaller).start();
        }
    }

    @BindingAdapter("original")
    public static void setOriginal(Spinner spinner, boolean value){
        SpinnerPresenter presenter = getHelper(spinner);
        if (presenter != null){
            presenter.setOriginal(value);
        }
    }

    @BindingAdapter("value")
    public static void setValue(Spinner spinner, Object value){
    }

    @InverseBindingAdapter(attribute = "value", event = "valueAttrChanged")
    public static Object getValue(Spinner spinner) {

        SpinnerPresenter presenter = getHelper(spinner);
        if (presenter != null){
            return presenter.getValue(0);
        }
        return null;
    }

    @BindingAdapter(value = {"valueAttrChanged"}, requireAll = false)
    public static void setSelectedValueChanged(Spinner spinner, InverseBindingListener valueListener) {

        SpinnerPresenter presenter = getHelper(spinner);
        if (presenter != null){
            presenter.setValueListener(valueListener);
        }
    }
}
