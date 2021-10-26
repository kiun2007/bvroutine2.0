package kiun.com.bvroutine.presenters;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.views.adapter.StepTreeAdapter;

/**
 *
 */
public class StepTreePresenter extends RecyclerListPresenter<TreeNode, TreeNode, TreeStepView, LoadAdapter> {

    private int rootLayout;
    private int expHandlerBr;
    private boolean isLoading;

    public StepTreePresenter(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout, int expHandlerBr) {
        super(recyclerView, refreshLayout);
        this.expHandlerBr = expHandlerBr;

        if (mRefreshLayout != null){
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter(int itemLayout, int dataBr, ListHandler<TreeNode> hanlder) {
        rootLayout = itemLayout;
        return new StepTreeAdapter(this, expHandlerBr, errLayout, dataBr, hanlder, mRequestView);
    }

    @Override
    public void reload() {
        loadAdapter.clearData();
        refreshing();
        presenter.addRequest(()->mRequestView.requestPager(presenter, null),
                            v -> onDataComplete(v, null, null));
    }

    protected void onDataComplete(List<Object> v, TreeNode parent, TreeViewNode treeViewNode) {

        if (parent == null){
            if (v != null){
                loadAdapter.add(TreeNode.addToRoot(loadAdapter.getAll(), rootLayout, v));
            }else {
                loadAdapter.error(null);
            }
        }else{
            if (v != null) {
                TreeNode.insertTo(loadAdapter.getAll(), parent, treeViewNode.getLayoutId(), v, treeViewNode.isChildren());
            }
            loadAdapter.notifySet();
        }


        if (parent != null) parent.setLoading(isLoading = false);

        if (mRefreshLayout != null){
            mRefreshLayout.setRefreshing(false);
        }
        mRequestView.loadComplete(this);
    }

    public void loadTree(TreeNode treeNode, TreeViewNode treeViewNode){
        treeNode.setLoading(isLoading = true);
        presenter.addRequest(()->mRequestView.requestPager(presenter, treeNode), v -> {
            if (v == null){
                treeNode.setPager(null);
                treeNode.expansion(false);
            }
            onDataComplete(v, treeNode, treeViewNode);
        });
    }

    public boolean isLoading(){
        return isLoading;
    }

    public void loadMore(TreeNode lastParent, TreeViewNode lastViewNode){

        if (lastParent == null){
            return;
        }

        PagerBean pager = lastParent.getPager();
        if (pager == null || pager.getPageNum() >= pager.getPages()){
            return;
        }
        pager.addPage();
        lastParent.setLoading(isLoading = true);
        presenter.addRequest(()->mRequestView.requestPager(presenter, lastParent), v -> {
            if (v != null) {
                pager.real(); //真实页码.
            }else{
                pager.rollbackPage(); //发生异常回滚页码.
            }
            onDataComplete(v, lastParent, lastViewNode);
        });
    }
}