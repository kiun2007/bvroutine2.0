package com.szxgm.gusustreet.base.general;

import android.app.Activity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public interface GeneralListController {

    Activity getActivity();

    PagerBean getQuery();

    int getItemLayout();

    RequestBindingPresenter getRequestPresenter();

    RecyclerView getListView();

    SwipeRefreshLayout getRefresh();

    EditText getSearchEdit();
}
