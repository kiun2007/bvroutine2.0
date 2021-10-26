package com.szxgm.gusustreet.ui.fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralListController;
import com.szxgm.gusustreet.base.presenter.imp.GeneralListPresenter;
import com.szxgm.gusustreet.databinding.LayoutGeneralListBinding;

import kiun.com.bvroutine.base.RequestBVFragment;

public abstract class GeneralListFragment extends RequestBVFragment<LayoutGeneralListBinding> implements GeneralListController {

    protected GeneralListPresenter general;

    @Override
    public int getViewId() {
        return R.layout.layout_general_list;
    }

    @Override
    public void initView() {
        general = new GeneralListPresenter(this);
    }

    @Override
    public int getItemLayout() {
        return R.layout.layout_general_list;
    }

    @Override
    public RecyclerView getListView() {
        return mViewBinding.list;
    }

    @Override
    public SwipeRefreshLayout getRefresh() {
        return mViewBinding.mainRefresh;
    }
}
