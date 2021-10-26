package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.dto.CameraInfo;
import com.szxgm.gusustreet.model.dto.CameraMarker;
import com.szxgm.gusustreet.net.requests.CameraReq;
import com.szxgm.gusustreet.net.responses.NetListWrapper;
import com.szxgm.gusustreet.net.responses.NetWrapper;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.data.PagerBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 视频管理相关接口.
 */
@AutoImport(RetrofitVariableSet.class)
public interface MonitorService {

    @POST("app/camera/searchOfMap")
    Call<NetWrapper<List<CameraMarker>>> searchOfMap(@Body CameraReq cameraReq);

    @POST("app/camera/getStreamUrl")
    @FormUrlEncoded
    Call<NetWrapper<String>> getStreamUrl(@Field("code") String code);

    @GET("app/camera/list")
    Call<NetListWrapper<CameraInfo>> list(@QueryMap PagerBean pagerBean);
}
