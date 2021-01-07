package com.szxgm.gusustreet.ui.activity.workbench;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.presenter.imp.GeneralListPresenter;
import com.szxgm.gusustreet.model.dto.mobile.CombinedChoose;
import com.szxgm.gusustreet.model.dto.mobile.Department;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.requests.mobile.CombinedDepartmentReq;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import com.szxgm.gusustreet.views.GeneralItemText;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.ListUtil;

/**
 * 协同处置部门选择
 */
public class CombinedDepartmentActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = CombinedDepartmentActivity.class;

    /**
     * 牵头部门ID.
     */
    public Department leadOffice = null;

    /**
     * 处置节点.
     */
    private String taskId;

    List<Department> departmentSelected;

    @Override
    public void initView() {
        taskId = getIntent().getStringExtra("taskId");

        super.initView();
        getBarItem().setTitle("综合协调处置部门");
        binding.filterBtn.setVisibility(View.GONE);

        getBarItem().getHandler().setRightCallBack(()->{
            List<CombinedChoose> combinedChooses = ListUtil.toList(departmentSelected,
                    item-> new CombinedChoose(item.getOfficeName(), item.getOfficeId(), item == leadOffice ? "1" : "0"));

            String value = JSON.toJSONString(combinedChooses);
            String title = ListUtil.join(combinedChooses, ",");

            setResult(Activity.RESULT_OK, new Intent().putExtra(GeneralItemText.TITLE, title).putExtra(GeneralItemText.ID, value));
            finish();
        });
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_department_multiple;
    }

    @Override
    public List requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {

        GeneralListQuery query = (GeneralListQuery) bean;
        if (bean.getPageNum() == 1){
            List<Department> departments = p.callServiceData(MobileService.class, s -> s.getHarmonizeOfficeList(new CombinedDepartmentReq(taskId)));
            if (!ListUtil.isEmpty(query.getSearch())){
                if (!query.getSearch().get(0).getValue().isEmpty()){
                    departments = ListUtil.filter(departments, item -> item.getOfficeName().contains(query.getSearch().get(0).getValue()));
                }
            }
            return departments;
        }
        return null;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery().field("officeName");
    }

    @Override
    public ListHandler getHandler() {
        return new ListHandler<Department>(BR.handler, R.layout.list_error_normal){
            @Override
            public void onClick(Context context, int tag, Department data) {
                if (tag == 0){
                    if (data.isSelected() && data == leadOffice){
                        leadOffice = null;
                    }
                    data.setSelected(!data.isSelected());
                }else if (tag == 1){
                    if (leadOffice != null){
                        leadOffice.onChanged();
                    }
                    leadOffice = data;
                }

                GeneralListPresenter<Department> departmentGeneral = general;

                departmentSelected = departmentGeneral.filter(item -> item.isSelected());
                getBarItem().setRightTitle(!departmentSelected.isEmpty()&&leadOffice!=null?"确定":null);
            }

            @Override
            public boolean itemChose(Department item) {

                if (leadOffice != null){
                    return leadOffice == item;
                }
                return false;
            }
        };
    }
}
