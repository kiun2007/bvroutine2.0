package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.Address;
import com.szxgm.gusustreet.model.dto.user.User;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.security.ConstValue;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface UserService {

    @FormUrlEncoded
    @POST("app/user/address")
    @Headers({ConstValue.Security+":"+ConstValue.P})
    Call<NetWrapper<List<Address>>> address(@Field("searchName") String searchName);

    @FormUrlEncoded
    @POST("system/user/profile/resetPwd")
    Call<NetWrapper<String>> resetPwd(@Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("app/user/getById")
    Call<NetWrapper<User>> getUserById(@Field("id") String id);
}