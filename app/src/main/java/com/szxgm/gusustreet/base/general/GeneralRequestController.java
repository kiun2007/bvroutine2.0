package com.szxgm.gusustreet.base.general;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public interface GeneralRequestController extends GeneralListController {

    List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception;
}
