package kiun.com.bvroutine.base;

import androidx.databinding.ViewDataBinding;
import android.widget.Toast;

import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.MessageView;
import kiun.com.bvroutine.interfaces.view.ServiceRequestView;
import kiun.com.bvroutine.presenters.RetrofitRequestPresenter;

public abstract class RequestBVFragment<T extends ViewDataBinding> extends BVBaseFragment<T> implements ServiceRequestView, MessageView {

    protected RequestBindingPresenter reqBinding;

    @Override
    public <S> S createService(Class<S> serviceClass) {
        return super.createService(serviceClass);
    }

    @Override
    public RequestBindingPresenter getRequestPresenter() {

        if(reqBinding == null){
            reqBinding = new RetrofitRequestPresenter(this);
        }
        return reqBinding;
    }

    public void errorMsg(String msg){
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}