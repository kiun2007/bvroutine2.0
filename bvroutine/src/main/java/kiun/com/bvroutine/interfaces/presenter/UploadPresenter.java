package kiun.com.bvroutine.interfaces.presenter;

import androidx.databinding.InverseBindingListener;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.DoublePartCaller;
import kiun.com.bvroutine.base.binding.PartCaller;

public interface UploadPresenter<T> {

    void init(RequestBVActivity activity, InverseBindingListener listener);

    void setNetCall(PartCaller caller);

    void setNetCall(DoublePartCaller caller);

    List<T> getData();
}