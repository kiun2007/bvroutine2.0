package com.szxgm.gusustreet.ui.activity.mine;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityMinePwdBinding;
import com.szxgm.gusustreet.model.query.Password;
import kiun.com.bvroutine.base.RequestBVActivity;

/**
 * 文 件 名: PwdChange
 * 作 者: 刘春杰
 * 创建日期: 2020/7/2 09:43
 * 说明: 修改密码
 */
public class PwdChangeActivity extends RequestBVActivity<ActivityMinePwdBinding> {

    public static final Class clz = PwdChangeActivity.class;

    @Override
    public int getViewId() {
        return R.layout.activity_mine_pwd;
    }

    @Override
    public void initView() {
        binding.setPassword(new Password());
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void onPasswordChange(View view){
        Toast.makeText(this, "密码修改成功", Toast.LENGTH_LONG).show();
        finish();
    }
}