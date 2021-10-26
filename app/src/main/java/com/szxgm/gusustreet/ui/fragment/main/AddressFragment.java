package com.szxgm.gusustreet.ui.fragment.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.general.GeneralListController;
import com.szxgm.gusustreet.base.presenter.imp.GeneralListPresenter;
import com.szxgm.gusustreet.databinding.FragmentAddressBinding;
import com.szxgm.gusustreet.model.dto.Address;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.model.vm.AddressViewModel;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.handlers.ListHandler;

/**
 * 通讯录页面
 */
public class AddressFragment extends RequestBVFragment<FragmentAddressBinding> implements GeneralListController, GeneralHandlerController {

    GeneralListPresenter listPresenter;
    AddressViewModel addressViewModel;
    @Override
    public int getViewId() {
        return R.layout.fragment_address;
    }

    @Override
    public void initView() {

        addressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);
        listPresenter = new GeneralListPresenter(this).setAutoIndexBar(mViewBinding.indexBar, addressViewModel.getAddressList());
        listPresenter.start();
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(Address.class).field("userName").setPageSize(Integer.MAX_VALUE);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_address;
    }

    @Override
    public RecyclerView getListView() {
        return mViewBinding.homePageMailList;
    }

    @Override
    public SwipeRefreshLayout getRefresh() {
        return mViewBinding.mainRefresh;
    }

    @Override
    public EditText getSearchEdit() {
        return mViewBinding.generalSearchText;
    }

    @SuppressLint("MissingPermission")
    private void callToAddress(Context context, Address address) {
        startPermission(new String[]{Manifest.permission.CALL_PHONE}, ()->{
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + address.getPhonenumber());
            intent.setData(data);
            context.startActivity(intent);
        });
    }

    @Override
    public ListHandler getHandler() {
        return new NormalHandler<Address>().addTag(0, this::callToAddress);
    }

    @Override
    public void onDataComplete(List list) {
    }
}
