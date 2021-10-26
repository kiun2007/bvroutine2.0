package com.szxgm.gusustreet.ui.fragment.main;

import com.szxgm.gusustreet.MainApplication;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.presenter.VersionUpdate;
import com.szxgm.gusustreet.base.presenter.imp.VersionUpdatePresenter;
import com.szxgm.gusustreet.databinding.FragmentDashboardBinding;
import com.szxgm.gusustreet.model.dto.user.User;

import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.utils.SharedUtil;

public class DashboardFragment extends BVBaseFragment<FragmentDashboardBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initView() {

        mViewBinding.setUser(SharedUtil.getValue(User.TAG, new User()));
        VersionUpdate versionUpdate = new VersionUpdatePresenter(getContext());
        mViewBinding.setVersionUpdate(versionUpdate);

        MainApplication.<MainApplication>getApplication().getPresenter().userLoginListener(v -> {
            mViewBinding.setIsLogin(v);
        });
    }
}
