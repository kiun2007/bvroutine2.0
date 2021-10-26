package com.szxgm.gusustreet.ui.activity.mine;

import android.content.Context;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityMineAboutBinding;

import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 文 件 名: About
 * 作 者: 刘春杰
 * 创建日期: 2020/7/2 09:39
 * 说明: 关于我们
 */
public class AboutActivity extends RequestBVActivity<ActivityMineAboutBinding> {

    public static final Class clz = AboutActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_mine_about;
    }

    @Override
    public void initView() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}