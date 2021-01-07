package kiun.com.bvroutine.presenters.list;

import android.content.Context;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;

public abstract class ListProvider<T> implements ListRequestView<PagerBean, T> {

    protected RequestBVActivity context;

    protected ListViewPresenter presenter;

    public abstract ListHandler getHandler();

    public abstract int getDataBind();

    public abstract int getItemLayoutId();

    public abstract void refresh();

    public ListProvider(RequestBVActivity context){
        this.context = context;
    }

    @Override
    public void loadComplete(ListViewPresenter p) {
    }

    public void setPresenter(ListViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public ListViewPresenter<T,?,?> getPresenter() {
        return presenter;
    }
}
