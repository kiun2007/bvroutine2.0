package com.szxgm.gusustreet.ui.activity.other;

import android.widget.Toast;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivitySubmitPublicityBinding;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.net.requests.PublicityReq;
import java.util.Date;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.SharedUtil;

/**
 * 文 件 名: SubmitPublicity
 * 作 者: 刘春杰
 * 创建日期: 2020/9/14 10:16
 * 说明: 宣传上报
 */
public class SubmitPublicityActivity extends RequestBVActivity<ActivitySubmitPublicityBinding> {

    public static final Class clz = SubmitPublicityActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_submit_publicity;
    }

    @Override
    public void initView() {
        PublicityReq req = new PublicityReq();
        User user = SharedUtil.getValue(User.TAG, new User());
        req.setPubDate(MCString.formatDate("yyyy-MM-dd", new Date()));
        binding.setData(req);
    }

    public void onComplete(String msg){
        Toast.makeText(this, "上报成功", Toast.LENGTH_LONG).show();
        finish();
    }
}