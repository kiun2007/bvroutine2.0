package kiun.com.bvroutine.presenters.loader;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.KeyValuePut;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.view.ServiceRequestLoadingView;
import kiun.com.bvroutine.interfaces.view.ServiceRequestView;

public class GetAsyncLoader extends AsyncTaskLoader<Object> {

    private static Map<Thread, KeyValuePut<Integer>> threadSetCallerMap = new HashMap<>();

    private ServiceRequestLoadingView loadingView;

    public static void startLoading(Integer integer, String title){

        KeyValuePut<Integer> setCaller = threadSetCallerMap.remove(Thread.currentThread());
        if (setCaller != null && integer != null){
            setCaller.put(title, integer);
        }
    }

    GetTNoParamCall<Object> asyncCaller;
    SetCaller<Object> callbackCaller;
    SetCaller<Exception> errCallbackCaller;

    public GetAsyncLoader(@NonNull ServiceRequestView view, GetTNoParamCall<Object> getCall, SetCaller<Object> callback, SetCaller<Exception> errCaller) {
        super(view.getContext());
        if (view instanceof ServiceRequestLoadingView){
            loadingView = (ServiceRequestLoadingView) view;
        }
        asyncCaller = getCall;
        callbackCaller = callback;
        errCallbackCaller = errCaller;
    }

    Object mApps;

    public void reload(){
        mApps = null;
        startLoading();
    }

    @Override
    public void deliverResult(Object apps) {
        if (isReset()) {
            if (apps != null) {
                onReleaseResources(apps);
            }
        }
        Object oldApps = mApps;
        mApps = apps;

        if (isStarted()) {
            super.deliverResult(apps);
        }

        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mApps != null) {
            deliverResult(mApps);
        }
        if (takeContentChanged() || mApps == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        mApps = null;
    }

    @Override
    public void onCanceled(Object apps) {
        super.onCanceled(apps);
        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }


    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mApps != null) {
            onReleaseResources(mApps);
            mApps = null;
        }
    }

    protected void onReleaseResources(Object apps) {
    }

    @Override
    public Object loadInBackground() {
        try{
            threadSetCallerMap.put(Thread.currentThread(), (key, value) -> {
                if (loadingView != null){
                    loadingView.startLoading(value, key, this);
                }
            });
            return asyncCaller.call();
        }catch (Exception ex){
            ex.printStackTrace();
            return ex;
        }
    }

    public SetCaller<Object> getCallback() {
        return callbackCaller;
    }

    public SetCaller<Exception> getErrorCallBack(){
        return errCallbackCaller;
    }
}