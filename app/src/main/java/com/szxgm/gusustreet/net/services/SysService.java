package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.Dict;
import com.szxgm.gusustreet.model.dto.OrderType;
import com.szxgm.gusustreet.model.query.DictReq;
import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface SysService {

    /**
     * 字典获取.
     * @param req
     * @return
     */
    @POST("API/pb/zdList")
    Call<NetWrapper<List<Dict>>> getDict(@Body DictReq req);

    @GET("app/river/getOrderTypes")
    Call<NetWrapper<List<OrderType>>> getOrderTypes();

    @FormUrlEncoded
    @POST("app/dict/listForKey")
    Call<NetWrapper<List<Dict>>> getAppDict(@Field("key") String key);
}
