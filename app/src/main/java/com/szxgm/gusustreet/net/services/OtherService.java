package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.vo.Publicity;
import com.szxgm.gusustreet.model.vo.SentinelSurveillance;
import com.szxgm.gusustreet.net.requests.PublicityReq;
import com.szxgm.gusustreet.net.requests.SentinelReq;
import com.szxgm.gusustreet.net.responses.NetOtherWrapper;
import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.net.Builder;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 第三方接口.
 */
@Builder("other") //设置服务构建版本.
@AutoImport(RetrofitVariableSet.class)
public interface OtherService {

    //---------列表.
    /**
     * 获取全部上报信息.
     * @return
     */
    @GET("api/interface/getAllSentinelSurveillance")
    Call<NetOtherWrapper<List<SentinelSurveillance>>> getAllSentinelSurveillance();

    /**
     * 获取全部调查信息.
     * @return
     */
    @GET("api/interface/getAllSentinelSuggestion")
    Call<NetOtherWrapper<List<Publicity>>> getAllSentinelSuggestion();

    /**
     * 获取全部宣传信息.
     * @return
     */
    @GET("api/interface/getAllPublicity")
    Call<NetOtherWrapper<List<Publicity>>> getAllPublicity();


    //-------上报.
    /**
     * 提交哨所事件.
     * @param req 事件内容.
     * @return
     */
    @POST("api/interface/submitByLevelTwo")
    Call<NetOtherWrapper<String>> submitByLevelTwo(@Body SentinelReq req);

    /**
     * 宣传事件上报.
     * @param req 事件内容.
     * @return
     */
    @POST("api/interface/submitPublicity")
    Call<NetOtherWrapper<String>> submitPublicity(@Body PublicityReq req);

    /**
     * 上传图片.
     * @param file
     * @return
     */
    @POST("api/file/upload")
    @Multipart
    Call<NetOtherWrapper<String>> uploadFile(@Part MultipartBody.Part file);
}
