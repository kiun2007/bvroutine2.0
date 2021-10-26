package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.dto.PatrolLog;
import com.szxgm.gusustreet.model.dto.river.RiverPatrol;
import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.PatrolPoints;
import com.szxgm.gusustreet.model.dto.river.RiverDetail;
import com.szxgm.gusustreet.model.dto.river.RiverOrder;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 河流相关接口
 */
@AutoImport(RetrofitVariableSet.class)
public interface RiverService {

    /**
     * 开始巡查.
     * @param riverId
     * @return
     */
    @POST("app/river/startPatrol")
    @FormUrlEncoded
    Call<NetWrapper<String>> startPatrol(@Field("riverId") String riverId);

    /**
     * 结束巡查.
     * @param id
     * @return
     */
    @POST("app/river/endPatrol")
    @FormUrlEncoded
    Call<NetWrapper> endPatrol(@Field("id") String id);

    /**
     * 提交问题.
     * @param order 问题.
     * @return
     */
    @POST("app/river/commitProblem")
    Call<NetWrapper> commitProblem(@Body RiverOrder order);

    @GET("app/river/getPatrolLog")
    Call<NetWrapper<PatrolLog>> getPatrolLog(@Query("patrolId") String patrolId);

    @POST("app/river/commitPoints")
    Call<NetWrapper<String>> commitPoints(@Body PatrolPoints points);

    @POST("app/river/commitPatrolLog")
    Call<NetWrapper<String>> commitPatrolLog(@Body PatrolLog log);

    @GET("app/river/getPoints")
    Call<NetWrapper<List<PatrolPoints>>> getPoints(@Query("patrolId") String patrolId);

    @FormUrlEncoded
    @POST("app/river/riverDetail")
    Call<NetWrapper<RiverDetail>> getRiverDetail(@Field("riverId") String riverId);

    @GET("app/river/getLastUnFinished")
    Call<NetWrapper<RiverPatrol>> getLastUnFinished();
}
