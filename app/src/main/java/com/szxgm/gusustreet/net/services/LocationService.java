package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetWrapper;

import java.util.Map;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface LocationService {

    /**
     * 传输坐标到后台
     * @param latLng
     * @return
     */
    @POST("API/common/coordinate.html")
    Call<NetWrapper<String>> coordinate(@Body Map<String, Object> latLng);

    /**
     * 传输轨迹.
     * @param tailJson
     * @return
     */
    @POST("API/common/trajectory.html")
    Call<NetWrapper<String>> trajectory(@Body Map<String, String> tailJson);
}
