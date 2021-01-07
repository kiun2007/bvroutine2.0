package kiun.com.bvroutine.presenters;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kiun.com.bvroutine.data.ExtraValue;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.ServiceCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ServiceRequestLoadingView;
import kiun.com.bvroutine.interfaces.view.ServiceRequestView;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;
import kiun.com.bvroutine.presenters.loader.GetAsyncLoader;
import kiun.com.bvroutine.utils.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitRequestPresenter implements RequestBindingPresenter, LoaderManager.LoaderCallbacks {

    ServiceRequestView viewInterface;
    LoaderManager loaderManager;
    Map<Integer, ExtraValue<GetTNoParamCall, SetCaller, SetCaller>> requestMaps = new HashMap<>();

    public RetrofitRequestPresenter(ServiceRequestView viewInterface){
        this.viewInterface = viewInterface;
        loaderManager = viewInterface.getSupportLoaderManager();
    }

    @Override
    public void addRequest(GetTNoParamCall<Void> serviceCaller) {
        int taskId = (int) (Math.random() * 1000 * 1000 * 1000);
        requestMaps.put(taskId, new ExtraValue(serviceCaller, null, null));
        loaderManager.restartLoader(taskId, new Bundle(), this);
    }

    @Override
    public <T> void addRequest(GetTNoParamCall<T> serviceCaller, SetCaller<T> setCaller) {

        addRequest(serviceCaller, setCaller, null);
    }

    @Override
    public <T> void addRequest(GetTNoParamCall<T> serviceCaller, SetCaller<T> setCaller, SetCaller<Exception> errCaller) {
        int taskId = (int) (Math.random() * 1000 * 1000 * 1000);
        requestMaps.put(taskId, new ExtraValue<>(serviceCaller, setCaller, errCaller));
        loaderManager.restartLoader(taskId, new Bundle(), this);
    }

    @Override
    public boolean isConcurrent() {
        return true;
    }

    @Override
    public <T, E> E callServiceData(Class<T> serviceClass, ServiceCaller<T> callback) throws Exception {
        return RetrofitUtil.callServiceData(serviceClass, callback, viewInterface::createService);
    }

    @Override
    public <T, E> List<E> callServiceList(Class<T> serviceClass, ServiceCaller<T> callback, PagerBean pagerBean) throws Exception {
        return RetrofitUtil.callServiceList(serviceClass, callback, pagerBean, viewInterface::createService);
    }

    @Override
    public DataWrap execute(Call call) throws Exception{

        try {
            Response response = call.execute();
            return (DataWrap) response.body();
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public ListWrap executeList(Call call) throws IOException {
        try {
            Response response = call.execute();
            return (ListWrap) response.body();
        } catch (IOException e) {
            throw e;
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        if(requestMaps.containsKey(id)){
            ExtraValue<GetTNoParamCall, SetCaller, SetCaller> request = requestMaps.remove(id);
            return new GetAsyncLoader(viewInterface, request.key(), request.value(), request.extra());
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        if (loader instanceof GetAsyncLoader){
            GetAsyncLoader asyncLoader = (GetAsyncLoader) loader;
            if (data instanceof Exception){

                if (viewInterface instanceof ServiceRequestLoadingView){
                    ((ServiceRequestLoadingView) viewInterface).loadError(asyncLoader, (Exception) data);
                }

                if (asyncLoader.getErrorCallBack() != null){
                    asyncLoader.getErrorCallBack().call((Exception) data);
                }
            }else{
                if (viewInterface instanceof ServiceRequestLoadingView){
                    ((ServiceRequestLoadingView) viewInterface).loadComplete(asyncLoader);
                }
                asyncLoader.getCallback().call(data);
                loaderManager.destroyLoader(loader.getId());
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
    }
}
