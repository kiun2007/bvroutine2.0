package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.user.User;


import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface AuthorizeService {

    @FormUrlEncoded
    @POST("login")
    @Headers({"login:.AppLogin"})
    Call<NetWrapper<User>> login(@Field("username") String userName,
                                 @Field("password") String pwd,
                                 @Field("rememberMe") Boolean rememberMe,
                                 @Header("token") String token);
}