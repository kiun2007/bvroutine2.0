package kiun.com.bvroutine.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.GetThrowCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.FormView;
import kiun.com.bvroutine.interfaces.view.MessageView;
import kiun.com.bvroutine.interfaces.view.ServiceRequestLoadingView;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.presenters.RetrofitRequestPresenter;
import kiun.com.bvroutine.presenters.loader.GetAsyncLoader;
import kiun.com.bvroutine.utils.JexlUtil;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public abstract class RequestBVActivity<T extends ViewDataBinding> extends BVBaseActivity<T> implements ServiceRequestLoadingView, FormView, MessageView {

    private Map<GetAsyncLoader, ViewDataBinding> loadingViewBinding = new HashMap<>();

    Lock lock = new ReentrantLock();

    @FunctionalInterface
    public interface RequestGetCaller<V> extends GetThrowCaller<RequestBindingPresenter,V>{
    }

    protected RequestBindingPresenter rbp;

    public boolean onData(String tag, DataWrap warp){

        boolean isSuccess = warp.isSuccess();

        if (isSuccess){
            if (tag != null){
                JexlUtil.run(tag, "handler", this, "data", warp.getData());
            }
        }else{
            errorMsg(warp.getMsg());
        }
        return isSuccess;
    }

    public void errorMsg(String msg){
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public String pwd(String name) {
        String text = string(name);
        return null;
    }

    @Override
    public String string(String name) {
        TextView text = binding.getRoot().findViewWithTag(name);
        if (text == null){
            return null;
        }
        return text.getText().toString();
    }

    @Override
    public int intValue(String name) {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        getRequestPresenter();
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getRequestPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }
    
    @Override
    public RequestBindingPresenter getRequestPresenter() {
        if(rbp == null){
            rbp = new RetrofitRequestPresenter(this);
        }
        return rbp;
    }

    public <E>void addRequest(GetTNoParamCall<E> serviceCaller, SetCaller<E> setCaller){
        getRequestPresenter().addRequest(serviceCaller, setCaller);
    }

    public <E>void requestPresenter(RequestGetCaller<E> serviceCaller, SetCaller<E> setCaller){
        getRequestPresenter().addRequest(()->serviceCaller.call(rbp), setCaller);
    }

    @Override
    public void startLoading(Integer layoutId, String title, GetAsyncLoader loader) {

        if (layoutId != null){

            ViewGroup viewGroup = binding.getRoot().findViewWithTag("RequestLoadingView");
            if (viewGroup != null){

                viewGroup.post(()->{

                    ViewDataBinding viewBinding = loadingViewBinding.get(loader);

                    if (viewBinding == null){
                        viewBinding = ViewBindingUtil.inflate(LayoutInflater.from(this), layoutId, null, false);
                        loadingViewBinding.put(loader, viewBinding);

                        if (viewGroup.getParent() instanceof ViewGroup){
                            ViewGroup parentGroup = (ViewGroup) viewGroup.getParent();
                            int index = parentGroup.indexOfChild(viewGroup);
                            parentGroup.addView(viewBinding.getRoot(), index, new ViewGroup.LayoutParams(-1, -1));
                            viewGroup.setVisibility(View.GONE);
                        }
                    }

                    viewBinding.setVariable(BR.title, title);
                    viewBinding.setVariable(BR.loader, loader);
                    viewBinding.setVariable(BR.error, null);
                });
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void loadComplete(GetAsyncLoader loader) {

        ViewDataBinding viewDataBinding = loadingViewBinding.remove(loader);
        ViewGroup viewGroup = binding.getRoot().findViewWithTag("RequestLoadingView");

        if (viewGroup != null){
            viewGroup.setVisibility(View.VISIBLE);
        }

        if (viewDataBinding != null){
            ViewGroup parentGroup = (ViewGroup) viewGroup.getParent();
            parentGroup.removeView(viewDataBinding.getRoot());
        }
    }

    @Override
    public void loadError(GetAsyncLoader loader, Exception e) {

        ViewDataBinding viewDataBinding = loadingViewBinding.get(loader);
        if (viewDataBinding != null){
            viewDataBinding.setVariable(BR.error, e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingViewBinding.clear();
    }
}