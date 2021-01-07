package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetListWrapper;
import com.szxgm.gusustreet.model.dto.SysNotice;

import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface HomeService {

    @POST("app/home/messageList")
    @FormUrlEncoded
    Call<NetListWrapper<SysNotice>> messageList(@Body PagerBean pagerBean);
}
