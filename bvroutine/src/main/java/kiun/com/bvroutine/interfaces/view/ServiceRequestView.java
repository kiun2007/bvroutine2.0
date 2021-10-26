package kiun.com.bvroutine.interfaces.view;

import androidx.loader.app.LoaderManager;

import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public interface ServiceRequestView extends BaseView{

    <T>T createService(Class<T> serviceClass);

    RequestBindingPresenter getRequestPresenter();

    LoaderManager getSupportLoaderManager();
}