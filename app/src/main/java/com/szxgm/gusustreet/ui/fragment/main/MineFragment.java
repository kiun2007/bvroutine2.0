package com.szxgm.gusustreet.ui.fragment.main;


import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.presenter.VersionUpdate;
import com.szxgm.gusustreet.base.presenter.imp.VersionUpdatePresenter;
import com.szxgm.gusustreet.databinding.FragmentMineBinding;
import com.szxgm.gusustreet.model.dto.user.User;

import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.utils.AppUtil;
import kiun.com.bvroutine.utils.SharedUtil;

public class MineFragment extends BVBaseFragment<FragmentMineBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        mViewBinding.setUser(SharedUtil.getValue(User.TAG, new User()));
        VersionUpdate versionUpdate = new VersionUpdatePresenter(getContext());
        mViewBinding.setVersionUpdate(versionUpdate);
        mViewBinding.setPackageInfo(AppUtil.getPackageInfo(getContext()));
    }
}