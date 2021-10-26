package com.szxgm.gusustreet.ui.activity;

import android.content.Context;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityNoticeBinding;
import com.szxgm.gusustreet.model.dto.Notice;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: Notice
 * 作 者: 刘春杰
 * 创建日期: 2020/7/5 22:27
 * 说明: 消息详细
 */
public class NoticeActivity extends RequestBVActivity<ActivityNoticeBinding> {

    public static final Class clz = NoticeActivity.class;

    @IntentValue
    Notice notice;

    @Override
    public int getViewId() {
        return R.layout.activity_notice;
    }

    @Override
    public void initView() {

        if (notice != null){
            getBarItem().setTitle(notice.getNoticeTitle());
            binding.webView.loadData(notice.getNoticeContent(), "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}