package com.szxgm.gusustreet.ui.activity;

import android.app.Activity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.presenter.imp.GeneralListPresenter;
import com.szxgm.gusustreet.base.general.GeneralRequestController;
import com.szxgm.gusustreet.databinding.LayoutGeneralListBinding;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import java.util.List;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.views.dialog.MCDialogManager;

/**
 * 文 件 名: GeneralListActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/5/25 14:28
 * 说明: 通用列表基类.
 */
public abstract class GeneralListActivity extends RequestBVActivity<LayoutGeneralListBinding> implements GeneralRequestController {

    public static final Class clz = GeneralListActivity.class;

    protected GeneralListPresenter general;

    public int getItemLayout(){
        return R.layout.item_general;
    }

    public abstract PagerBean getQuery();

    public int getFilterDialogLayout(){
        return 0;
    }

    public int getDataBr(){
        return BR.data;
    }

    public int getDialogBr(){
        return BR.dialog;
    }

    @Override
    public int getViewId() {
        return R.layout.layout_general_list;
    }

    @Override
    public void initView() {
        general = new GeneralListPresenter(this);
        startList();
        binding.filterBtn.setVisibility(getFilterDialogLayout() != 0?View.VISIBLE:View.GONE);
    }

    protected void startList(){
        general.start();
    }

    @Override
    public RecyclerView getListView() {
        return binding.list;
    }

    @Override
    public SwipeRefreshLayout getRefresh() {
        return binding.mainRefresh;
    }

    @Override
    public EditText getSearchEdit() {
        return binding.generalSearchText;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        return p.callServiceList(GeneralListService.class, s->s.search(bean), bean);
    }

    protected void onRefresh(){
    }

    @Override
    public void onDataComplete(List list) {
    }

    public void onFilter(View view){
        if (getFilterDialogLayout() != 0){
            MCDialogManager.create(this, getFilterDialogLayout(), general.getQuery(), getDataBr(), getDialogBr())
                    .setGravity(Gravity.RIGHT).setCancelable(true)
                    .show()
                    .bindValue(BR.listPresenter, general)
                    .setOnCancelListener((dialogInterface)->{
                        binding.filterBtn.setSelected(!general.getQuery().isEmpty());
                    })
                    .setCaller(v -> {
                        onRefresh();
                        general.refresh();
                        binding.filterBtn.setSelected(!general.getQuery().isEmpty());
                    });
        }
    }
}