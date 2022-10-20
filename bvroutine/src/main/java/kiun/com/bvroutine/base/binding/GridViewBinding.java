package kiun.com.bvroutine.base.binding;

import android.widget.GridView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.PagerCaller;
import kiun.com.bvroutine.interfaces.callers.PagerDataCaller;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.presenters.GridViewListPresenter;
import kiun.com.bvroutine.presenters.list.ListProvider;
import kiun.com.bvroutine.presenters.list.NetListProvider;

public class GridViewBinding {

    @BindingAdapter("android:dataBlock")
    public static void setDataBlock(GridView recyclerView, PagerDataCaller caller){
        NetListProvider listProvider = (NetListProvider) recyclerView.getTag(R.id.tagListPresenter);
        if (listProvider != null){
            listProvider.setCaller(caller);
            listProvider.refresh();
        }
    }

    @BindingAdapter({"android:listProvider", "android:dataBlock"})
    public static void setListDataProvider(GridView recyclerView, NetListProvider listProvider, PagerDataCaller caller){

        if (listProvider != null){
            listProvider.setCaller(caller);
        }
        setListProvider(recyclerView, listProvider);
    }

    @BindingAdapter("android:netBlock")
    public static void setNetBlock(GridView recyclerView, PagerCaller caller){
        NetListProvider listProvider = (NetListProvider) recyclerView.getTag(R.id.tagListPresenter);
        if (listProvider != null){
            listProvider.setCaller(caller);
            listProvider.refresh();
        }
    }

    @BindingAdapter({"android:listProvider", "android:netBlock"})
    public static void setListProvider(GridView recyclerView, NetListProvider listProvider, PagerCaller caller){

        if (listProvider != null){
            listProvider.setCaller(caller);
        }
        setListProvider(recyclerView, listProvider);
    }

    @BindingAdapter({"android:listProvider"})
    public static void setListProvider(GridView gridView, ListProvider listProvider){

        if (!(gridView.getContext() instanceof RequestBVActivity) || listProvider == null){
            return;
        }

        RequestBVActivity activity = (RequestBVActivity) gridView.getContext();
        SwipeRefreshLayout swipeRefreshLayout = null;
        if (gridView.getParent() instanceof SwipeRefreshLayout){
            swipeRefreshLayout = (SwipeRefreshLayout) gridView.getParent();
        }
        ListViewPresenter presenter = new GridViewListPresenter(gridView, swipeRefreshLayout);
        presenter.initRequest(new PagerBean<>(), listProvider);
        listProvider.setPresenter(presenter);

        presenter.start(listProvider.getHandler(), listProvider.getItemLayoutId(), listProvider.getDataBind(), activity.getRequestPresenter());
        gridView.setTag(R.id.tagListPresenter, listProvider);
    }
}
