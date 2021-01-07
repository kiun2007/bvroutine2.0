package com.szxgm.gusustreet.base.presenter.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.mcxtzhang.indexlib.suspension.ISuspensionInterface;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.general.GeneralListController;
import com.szxgm.gusustreet.base.general.GeneralRequestController;
import com.szxgm.gusustreet.base.general.GeneralSearchRequestController;
import com.szxgm.gusustreet.model.base.GeneralItem;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.views.AutoIndexBar;
import com.szxgm.gusustreet.views.SuspensionDecoration;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.CompareCaller;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.ListRequestView;
import kiun.com.bvroutine.presenters.RecyclerListPresenter;
import kiun.com.bvroutine.utils.ListUtil;

public class GeneralListPresenter<T> implements ListRequestView<PagerBean, ISuspensionInterface> {

    ListViewPresenter viewPresenter;
    protected PagerBean query;
    private GeneralListController controller;
    boolean isStart = false;
    SuspensionDecoration mDecoration;
    AutoIndexBar indexBar;
    List indexBarList;

    public GeneralListPresenter(GeneralListController controller){

        this.controller = controller;
        viewPresenter = new RecyclerListPresenter(controller.getListView(), controller.getRefresh());
        viewPresenter.initRequest(query = controller.getQuery(), this);

        controller.getSearchEdit().setOnEditorActionListener((v, actionId, event) -> {
            if (getQuery().getField() != null){
                getQuery().put(getQuery().getField(), QueryType.Like, v.getText().toString());
                refresh();
            }
            return true;
        });
    }

    public GeneralListPresenter setAutoIndexBar(AutoIndexBar indexBar, List list){

        indexBarList = list;
        this.indexBar = indexBar;
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        controller.getListView().setLayoutManager(manager);
        indexBar.setmLayoutManager(manager);

        if (!ListUtil.isEmpty(indexBarList)){
            setIndexBarList(indexBarList);
        }
        return this;
    }

    private ListHandler listHandler = new ListHandler<GeneralItem>(BR.handler, R.layout.list_error_normal) {
        @Override
        public void onClick(Context context, int tag, GeneralItem item) {
            Intent intent = new Intent().putExtra("id", item.getId()).putExtra("title", item.getTitle());
            controller.getActivity().setResult(Activity.RESULT_OK, intent);
            controller.getActivity().finish();
        }
    };

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        if (!ListUtil.isEmpty(indexBarList)){
            List list = indexBarList;
            indexBarList = null;
            return list;
        }

        if (controller instanceof GeneralRequestController){
            return ((GeneralRequestController) controller).requestPager(p, bean);
        }
        return p.callServiceList(GeneralListService.class, s->s.search(bean), bean);
    }

    public void start(){
        start(true);
    }

    public void start(boolean isInitStart){

        if (controller instanceof GeneralHandlerController){
            listHandler = ((GeneralHandlerController) controller).getHandler();
        }

        listHandler.setInitStart(isInitStart);
        viewPresenter.start(listHandler, controller.getItemLayout(), BR.item, controller.getRequestPresenter());
        isStart = true;
    }

    public void refresh(){
        if (isStart){
            viewPresenter.reload();
        }
    }

    public GeneralListQuery getQuery() {
        if (query instanceof GeneralListQuery){
            return (GeneralListQuery) query;
        }
        return new GeneralListQuery();
    }

    public List<T> filter(CompareCaller<T> caller){
        return viewPresenter.filter(caller);
    }

    private void setIndexBarList(List list){
        if (mDecoration == null){
            controller.getListView().addItemDecoration(mDecoration = new SuspensionDecoration(controller.getActivity(), list));
        }else{
            mDecoration.setDatas(list);
        }
        indexBar.setData(list);
    }

    @Override
    public void loadComplete(ListViewPresenter<ISuspensionInterface, PagerBean, ?> p) {

        if (this.indexBar != null){
            List<? extends ISuspensionInterface> list = p.list();
            setIndexBarList(list);
            if (indexBarList != null){
                indexBarList.clear();
                indexBarList.addAll(list);
            }
        }
    }

    @Override
    public Context getContext() {
        return controller.getActivity();
    }
}
