package com.szxgm.gusustreet.ui.activity.other;

import android.widget.Toast;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivitySubmitSentinelBinding;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.net.requests.SentinelReq;

import java.util.Date;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 文 件 名: SubmitSentinel
 * 作 者: 刘春杰
 * 创建日期: 2020/8/24 20:17
 * 说明: 非法行医上报
 */
public class SubmitSentinelActivity extends RequestBVActivity<ActivitySubmitSentinelBinding> {

    public static final Class clz = SubmitSentinelActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_submit_sentinel;
    }

    @Override
    public void initView() {
        SentinelReq req = new SentinelReq();
        User user = SharedUtil.getValue(User.TAG, new User());
        req.setReportMan(user.getUserName());
        req.setReportPath(user.getDeptName());
        req.setCreatedOn(MCString.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
        binding.setData(req);
    }

    public void onComplete(String msg){
        Toast.makeText(this, "上报成功", Toast.LENGTH_LONG).show();
        finish();
    }
}