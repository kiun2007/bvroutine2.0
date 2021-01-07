package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.PagerCaller;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.presenters.RecyclerListPresenter;
import kiun.com.bvroutine.presenters.StepTreePresenter;
import kiun.com.bvroutine.presenters.list.ListProvider;
import kiun.com.bvroutine.presenters.list.NetListProvider;
import kiun.com.bvroutine.presenters.list.TreeProvider;

public class ListViewBinding {

    @BindingAdapter("android:netBlock")
    public static void setNetBlock(RecyclerView recyclerView, PagerCaller caller){
        NetListProvider listProvider = (NetListProvider) recyclerView.getTag(R.id.tagListPresenter);
        if (listProvider != null){
            listProvider.setCaller(caller);
            listProvider.refresh();
        }
    }

    @BindingAdapter({"android:listProvider", "android:netBlock"})
    public static void setListProvider(RecyclerView recyclerView, NetListProvider listProvider, PagerCaller caller){

        if (listProvider != null){
            listProvider.setCaller(caller);
        }
        setListProvider(recyclerView, listProvider);
    }

    @BindingAdapter({"android:listProvider"})
    public static void setListProvider(RecyclerView recyclerView, ListProvider listProvider){

        if (!(recyclerView.getContext() instanceof RequestBVActivity) || listProvider == null){
            return;
        }

        RequestBVActivity activity = (RequestBVActivity) recyclerView.getContext();
        SwipeRefreshLayout swipeRefreshLayout = null;
        if (recyclerView.getParent() instanceof SwipeRefreshLayout){
            swipeRefreshLayout = (SwipeRefreshLayout) recyclerView.getParent();
        }
        ListViewPresenter presenter = new RecyclerListPresenter(recyclerView, swipeRefreshLayout);
        presenter.initRequest(new PagerBean<>(), listProvider);
        listProvider.setPresenter(presenter);

        presenter.start(listProvider.getHandler(), listProvider.getItemLayoutId(), listProvider.getDataBind(), activity.getRequestPresenter());
        recyclerView.setTag(R.id.tagListPresenter, listProvider);
    }

    @BindingAdapter({"android:treeProvider"})
    public static void setTreeProvider(RecyclerView recyclerView, TreeProvider treeProvider){

        if (!(recyclerView.getContext() instanceof RequestBVActivity) || treeProvider == null){
            return;
        }

        if (recyclerView.getTag(R.id.tagListPresenter) instanceof TreeProvider){
            return;
        }

        RequestBVActivity activity = (RequestBVActivity) recyclerView.getContext();
        SwipeRefreshLayout swipeRefreshLayout = null;
        if (recyclerView.getParent() instanceof SwipeRefreshLayout){
            swipeRefreshLayout = (SwipeRefreshLayout) recyclerView.getParent();
        }

        StepTreePresenter presenter = new StepTreePresenter(recyclerView, swipeRefreshLayout, BR.treeHandler);
        presenter.initRequest(null, treeProvider);
        treeProvider.setPresenter(presenter);

        presenter.start(treeProvider.getHandler(), treeProvider.getItemLayoutId(), treeProvider.getDataBind(), activity.getRequestPresenter());
        recyclerView.setTag(R.id.tagListPresenter, treeProvider);
    }
}
