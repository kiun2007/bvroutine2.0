package com.szxgm.gusustreet.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.szxgm.gusustreet.MainActivity;
import com.szxgm.gusustreet.MainApplication;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.ActivityRootLoginBinding;
import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.net.services.TestService;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.utils.SharedUtil;

public class LoginActivity extends RequestBVActivity<ActivityRootLoginBinding> {

    @Override
    public int getViewId() {
        return R.layout.activity_root_login;
    }

    @Override
    public void initView() {
        User user = SharedUtil.getValue(User.TAG, new User());
        binding.setUser(user == null ? new User() : user);

        addRequest(()-> rbp.callServiceData(TestService.class, s-> s.login("admin", "123456", "password", "server")), v->{
            System.out.println(v);
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void login(User user){

        if (!TextUtils.isEmpty(user.getImUser())){
            MainApplication.<MainApplication>getApplication().getPresenter().loginUser(user.getImUser(), user.getImPwd());
        }

        user.setLoginName(binding.getUser().getLoginName());
        SharedUtil.saveValue(User.TAG, user);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
