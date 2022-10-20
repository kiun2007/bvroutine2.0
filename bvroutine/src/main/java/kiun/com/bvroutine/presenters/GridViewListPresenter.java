package kiun.com.bvroutine.presenters;

import android.widget.GridView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.interfaces.view.LoadStartAdapter;
import kiun.com.bvroutine.views.adapter.BaseListAdapter;

public class GridViewListPresenter<T,Q,Req extends ListRequestView> extends RecyclerListPresenter<T,Q,Req, LoadStartAdapter>{

    public GridViewListPresenter(GridView listView, SwipeRefreshLayout refreshLayout) {
        super(listView, refreshLayout);
    }

    @Override
    protected LoadStartAdapter getRecyclerAdapter(int itemLayout, int dataBr, ListHandler<T> hanlder) {
        return new BaseListAdapter(mListView.getContext(), itemLayout, dataBr, hanlder);
    }
}
