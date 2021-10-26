package kiun.com.bvroutine.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;

import kiun.com.bvroutine.base.jexl.ProxyEnable;
import kiun.com.bvroutine.base.jexl.ProxyHandler;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.view.BindingView;
import kiun.com.bvroutine.interfaces.view.SaveBindingView;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.PermissionUtil;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public abstract class BVBaseFragment<T extends ViewDataBinding> extends Fragment implements BindingView {

    protected T mViewBinding = null;
    protected BVBaseActivity baseActivity;

    PermissionUtil permissionUtil;

    /**
     * 导航条功能模型BR.
     * @return 重写此方法实现导航条模块使用.
     */
    protected int getNavigationBR(){
        return -1;
    }

    public boolean isWithActionBar(){
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = ViewBindingUtil.inflate(inflater, getViewId(), container, false);

        View rootView = mViewBinding.getRoot();
        rootView.setTag(this);

        permissionUtil = new PermissionUtil(getActivity());
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof BVBaseActivity){
            baseActivity = (BVBaseActivity) getActivity();
            baseActivity.registerListener(this, this::onEvent);
        }

        if (this instanceof SaveBindingView){
            ((SaveBindingView) this).initView(savedInstanceState);
        }else {
            initView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(baseActivity != null){
            baseActivity.unregisterListener(this);
        }
    }

    protected void putEvent(Integer tag, Object value, Class<? extends Fragment> filterClz){
        if (baseActivity != null){
            baseActivity.putEvent(tag, value, filterClz);
        }
    }

    protected void putEvent(Integer tag, Object value){
        putEvent(tag, value, null);
    }

    protected void onEvent(Integer tag, Object value){
    }

    public void startPermission(String[] permission, CallBack okCallBack, CallBack refuseCall){
        if (permissionUtil != null){
            permissionUtil.startPermission(permission, okCallBack, refuseCall);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissionUtil != null){
            permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void setVariable(int variableId, @Nullable Object value){

        if (value == null){
            return;
        }

        if (value.getClass().isAnnotationPresent(ProxyEnable.class)){
            value = ProxyHandler.createBy(getContext(), value.getClass(), value);
        }

        if (value instanceof EventBean){
            EventBean eventBean = (EventBean) value;
            eventBean.error(this::onError);
            eventBean.bind(variableId, mViewBinding);
        }
        mViewBinding.setVariable(variableId, value);
    }

    protected void onError(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public <T> T createService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }

    public LoaderManager getSupportLoaderManager() {
        return getActivity().getSupportLoaderManager();
    }

    public void startPermission(String[] permission, CallBack callBack){
        if (getContext() instanceof BVBaseActivity){
            BVBaseActivity activity = (BVBaseActivity) getContext();
            activity.startPermission(permission, callBack);
        }
    }
}