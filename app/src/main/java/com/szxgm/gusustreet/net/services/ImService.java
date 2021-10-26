package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.user.User;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface ImService {

    @FormUrlEncoded
    @POST("app/im/user")
    Call<NetWrapper<User>> getUser(@Field("remoteNum") String remoteNum);
}
