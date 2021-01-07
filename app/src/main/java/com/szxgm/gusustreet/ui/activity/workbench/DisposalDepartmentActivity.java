package com.szxgm.gusustreet.ui.activity.workbench;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.model.dto.mobile.Department;
import com.szxgm.gusustreet.model.dto.mobile.DepartmentType;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.requests.mobile.DepartmentReq;
import com.szxgm.gusustreet.net.requests.mobile.OfficeTypeReq;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.ClassifyListActivity;
import com.szxgm.gusustreet.views.GeneralItemText;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.RetrofitUtil;

/**
 * 处置部门选择.
 */
public class DisposalDepartmentActivity extends ClassifyListActivity<DepartmentType> implements GeneralHandlerController {

    public static final Class clz = DisposalDepartmentActivity.class;

    private DepartmentType selectItem;

    /**
     * 处置节点.
     */
    private String taskDefKey;


    @Override
    public void initView() {
        taskDefKey = getIntent().getStringExtra("taskDefKey");

        super.initView();
        getBarItem().setTitle("选择处理部门");
        setTabMode(TabLayout.MODE_FIXED);
        binding.filterBtn.setVisibility(View.GONE);
    }


    @Override
    protected List<DepartmentType> classifyList() throws Exception {
        return RetrofitUtil.callServiceData(MobileService.class, s -> s.getOfficeType(new OfficeTypeReq(taskDefKey)));
    }

    @Override
    protected String title(DepartmentType item) {
        return item.getDictLabel();
    }

    @Override
    protected void onSelected(DepartmentType item) {
        selectItem = item;
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        GeneralListQuery query = (GeneralListQuery) bean;
        DepartmentReq departmentReq = new DepartmentReq(selectItem.getDictValue());
        departmentReq.inherit(bean);
        departmentReq.setOfficeName(query.getSearch().isEmpty() ? null : query.getSearch().get(0).getValue());

        return p.callServiceList(MobileService.class, s -> s.getHarmonizeOfficeList(departmentReq), bean);
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery().field("officeName");
    }

    @Override
    public ListHandler getHandler() {
        return new NormalHandler<Department>().addTag(0, (context, data)->{
            setResult(Activity.RESULT_OK, new Intent().putExtra(GeneralItemText.TITLE, data.getOfficeName()).putExtra(GeneralItemText.ID, data.getOfficeId()));
            finish();
        });
    }
}
