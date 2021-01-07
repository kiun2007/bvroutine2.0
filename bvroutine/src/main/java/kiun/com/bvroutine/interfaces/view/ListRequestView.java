package kiun.com.bvroutine.interfaces.view;

import java.util.List;

import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public interface ListRequestView<Q,T extends Object> extends BaseView{

    List requestPager(RequestBindingPresenter p, Q bean) throws Exception;

    void loadComplete(ListViewPresenter<T, Q, ?> p);
}