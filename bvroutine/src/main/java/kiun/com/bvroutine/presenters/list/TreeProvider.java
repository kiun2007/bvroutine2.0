package kiun.com.bvroutine.presenters.list;

import android.content.Context;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.view.IndentTreeStepView;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.presenters.StepTreePresenter;

public abstract class TreeProvider implements IndentTreeStepView {

    protected RequestBVActivity context;
    protected ListHandler handler;
    protected StepTreePresenter presenter;

    public abstract int getDataBind();

    public abstract int getItemLayoutId();

    public TreeProvider(RequestBVActivity context, ListHandler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void setPresenter(StepTreePresenter presenter) {
        this.presenter = presenter;
    }

    public ListHandler getHandler() {

        if (handler == null){
            handler = new ListHandler(BR.handler, 0);
        }
        return handler;
    }

    @Override
    public void onCheckChanged(StepTreePresenter p) {
        
    }

    @Override
    public void onLoadMore(StepTreePresenter p) {
    }

    @Override
    public void loadComplete(ListViewPresenter<TreeNode, TreeNode, ?> p) {
    }

    @Override
    public Context getContext() {
        return context;
    }
}
