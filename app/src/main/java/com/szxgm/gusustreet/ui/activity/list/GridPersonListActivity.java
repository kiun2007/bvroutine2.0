package com.szxgm.gusustreet.ui.activity.list;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityGridPersonChooseListBinding;
import com.szxgm.gusustreet.model.other.GridPerson;
import com.szxgm.gusustreet.net.requests.GridPersonReq;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.presenters.list.ListProvider;
import kiun.com.bvroutine.utils.ListHandlerUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine_apt.IntentValue;

import static com.szxgm.gusustreet.views.GeneralItemText.*;

/**
 * 网格人员.
 */
public class GridPersonListActivity extends RequestBVActivity<ActivityGridPersonChooseListBinding> {

    public static final Class clz = GridPersonListActivity.class;

    @IntentValue
    String gridCode;

    @Override
    public int getViewId() {
        return R.layout.activity_grid_person_choose_list;
    }

    @Override
    public void initView() {

        binding.setHandler(listHandler);

        GridPersonReq req = new GridPersonReq();
        req.setWgbm(getIntent().getStringExtra("gridCode"));
        binding.setPersonReq(req.listener(this::onRequestChanged));

        getBarItem().getHandler().setRightCallBack(()->{

            ListProvider<GridPerson> provider = (ListProvider) listHandler.getTag();
            List<GridPerson> selectedPerson = provider.getPresenter().filter(item -> item.isChecked());

            if (selectedPerson.isEmpty()){
                Toast.makeText(getContext(), "未选中任何人员", Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra(TITLE, ListUtil.join(selectedPerson, ","));
            intent.putExtra(ID, ListUtil.join(selectedPerson, ",", GridPerson::getYhm));
            intent.putExtra(EXTRA, ListUtil.join(selectedPerson, ",", GridPerson::getId));
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void onRequestChanged(GridPersonReq req){
        ListHandlerUtil.refresh(listHandler);
    }

    ListHandler<GridPerson> listHandler = new ListHandler<GridPerson>(BR.handler, R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, GridPerson data) {

            if (tag == 1){
                data.setChecked(!data.isChecked());
            }
        }
    };
}
