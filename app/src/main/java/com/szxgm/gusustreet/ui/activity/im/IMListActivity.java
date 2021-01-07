package com.szxgm.gusustreet.ui.activity.im;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.presenter.imp.GeneralListPresenter;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.general.GeneralListController;
import com.szxgm.gusustreet.databinding.FragmentAddressBinding;
import com.szxgm.gusustreet.model.dto.IMList;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.utils.SharedUtil;

public class IMListActivity extends RequestBVActivity<FragmentAddressBinding> implements GeneralListController, GeneralHandlerController {

    public static final Class clz = IMListActivity.class;

    GeneralListPresenter listPresenter;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(IMList.class).field("userName")
                    .put("userId", QueryType.Ne, SharedUtil.getValue(User.TAG, new User()).getUserId())
                    .setPageSize(Integer.MAX_VALUE);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_im;
    }

    @Override
    public RecyclerView getListView() {
        return binding.homePageMailList;
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
    public int getViewId() {
        return R.layout.fragment_address;
    }

    @Override
    public void initView() {
        listPresenter = new GeneralListPresenter(this).setAutoIndexBar(binding.indexBar, null);
        listPresenter.start();
        getBarItem().setTitle("视频直播");
        getBarView().setBarNoback(false);
    }

    @Override
    public ListHandler getHandler() {
        return new ListHandler<IMList>(BR.handler,R.layout.list_error_normal){
            @Override
            public void onClick(Context context, int tag, IMList data) {
                new AlertDialog.Builder(context).setTitle("提示")
                        .setMessage(String.format("是否拨打用户 %s", data.getUserName()))
                        .setNegativeButton("确认", (dialog, which) -> {
                            IMVideoActivityHandler.openActivityNormal(IMListActivity.this, data.getUserName(), data.getImUser(), tag,true);
                        })
                        .setPositiveButton("取消", null).show();
            }
        };
    }
}
