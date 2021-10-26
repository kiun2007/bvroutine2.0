package kiun.com.bvroutine.presenters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.CompareCaller;
import kiun.com.bvroutine.interfaces.presenter.ExceptionCatcher;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.views.adapter.RecyclerSimpleAdapter;

public class RecyclerListPresenter<T,Q,Req extends ListRequestView,ADA extends LoadAdapter>
        extends RecyclerView.OnScrollListener implements ListViewPresenter<T,Q,Req>, SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected Req mRequestView;
    protected Q rootRequest;
    protected RequestBindingPresenter presenter;
    protected ADA loadAdapter;
    protected ExceptionCatcher catcher;
    int errLayout = 0;
    protected View footerView;

    public RecyclerListPresenter(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout){
        mRecyclerView = recyclerView;
        mRefreshLayout = refreshLayout;
        if (refreshLayout != null){
            refreshLayout.setOnRefreshListener(this);
        }
        setFootViewLayout(R.layout.view_footer);

    }

    public void setFootViewLayout(int layout){
        footerView = LayoutInflater.from(mRecyclerView.getContext()).inflate(layout, null);
    }

    @Override
    public Q initRequest(Q request, Req requestView) {
        mRequestView = requestView;
        rootRequest = request;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requestView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return request;
    }

    protected BaseRecyclerAdapter getRecyclerAdapter(int itemLayout, int dataBr, ListHandler<T> hanlder){
        return new RecyclerSimpleAdapter(this, itemLayout, errLayout, dataBr, hanlder);
    }

    @Override
    public void start(ListHandler<T> handler, int itemLayout, int dataBr, RequestBindingPresenter p) {
        presenter = p;
        errLayout = handler.getErrorLayout();
        mRecyclerView.setAdapter((BaseRecyclerAdapter) (loadAdapter = (ADA) getRecyclerAdapter(itemLayout, dataBr, handler)));
        catcher = new ErrorMessageCatcher(loadAdapter::error, this::onError);

        if (handler.isInitStart()){
            reload();
        }
    }

    @Override
    public void loadMore() {

        if(rootRequest instanceof PagerBean){
            if (!((PagerBean) rootRequest).isPageOver()){
                ((PagerBean) rootRequest).addPage();
            }else{
                return;
            }
        }
        loadAdapter.addFooterView(footerView);
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnabled(false);
        }
        presenter.addRequest(()->mRequestView.requestPager(presenter, rootRequest), this::onDataComplete, catcher::onCatch);
    }

    protected void refreshing(){

        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void reload() {

        loadAdapter.clear();
        if(rootRequest instanceof PagerBean) {
            ((PagerBean) rootRequest).setPageNum(1);
        }

        refreshing();
        presenter.addRequest(this::requestStart, this::onDataComplete, catcher::onCatch);
    }

    private List<T> requestStart() throws Exception {
        List<T> list = mRequestView.requestPager(presenter, rootRequest);
        return list;
    }

    @Override
    public void notifySet() {
        loadAdapter.notifySet();
    }

    @Override
    public int count() {
        if(rootRequest instanceof PagerBean) {
            return  ((PagerBean) rootRequest).getTotal();
        }
        return list().size();
    }

    @Override
    public int[] filterCount(CompareCaller<T>... callers) {
        return filterCount(false, callers);
    }

    @Override
    public int[] filterCount(boolean repeat, CompareCaller<T>... callers) {

        List<T> items = loadAdapter.getAll();
        int[] rets = new int[callers.length];
        for (int i = 0; i < rets.length; i++) rets[i] = 0;

        for (T item : items) {
            for (int i = 0; i < callers.length; i++) {
                if (callers[i].call(item)){
                    rets[i] ++;
                    if (!repeat){
                        break;
                    }
                }
            }
        }
        return rets;
    }

    @Override
    public List<T> filter(CompareCaller<T> caller) {
        return filters(caller)[0];
    }

    @Override
    public List<T>[] filters(CompareCaller<T>... callers) {
        return filters(false, callers);
    }

    @Override
    public List<T>[] filters(boolean repeat, CompareCaller<T>... callers) {
        return ListUtil.filters(loadAdapter.getAll(), repeat, callers);
    }

    @Override
    public List<T> list() {
        return loadAdapter.getAll();
    }

    protected void onDataComplete(List<T> v){

        PagerBean pager = rootRequest instanceof PagerBean ? (PagerBean) rootRequest : null;
        if (pager != null){
            if (v != null) {
                pager.real(); //真实页码.
            }else{
                pager.rollbackPage(); //发生异常回滚页码.
            }
        }

        if (v != null){
            loadAdapter.add(v);
        }
        loadAdapter.removeFooter();
        mRequestView.loadComplete(this);

        if (mRefreshLayout != null){
            mRefreshLayout.setEnabled(true);
            mRefreshLayout.setRefreshing(false);
        }
    }

    protected void onError(ExceptionCatcher catcher){
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnabled(true);
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        reload();
    }
}