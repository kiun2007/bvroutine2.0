package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.dto.user.User;
import com.szxgm.gusustreet.net.responses.NetWrapper;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.net.Builder;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
@Builder("test")
public interface TestService {

    @FormUrlEncoded
    @POST("oauth/token")
    @Headers({"Authorization:Basic YXBwOmFwcA=="})
    Call<NetWrapper<Object>> login(@Field("username") String userName,
                                 @Field("password") String pwd,
                                 @Field("grant_type") String grant_type,
                                 @Header("scope") String scope);
}
