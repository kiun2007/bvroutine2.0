package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.DatVersion;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

@AutoImport(RetrofitVariableSet.class)
public interface VersionService {

    /**
     * 获取云端版本ID.
     * @param packageName
     * @param sdkVersion
     * @param versionCode
     * @return
     */
    @FormUrlEncoded
    @POST("system/datVersionInfo/version")
    Call<NetWrapper<DatVersion>> getNewVersion(@Field("packageName") String packageName, @Field("sdkVersion") Integer sdkVersion,@Field("versionCode") Integer versionCode);

    /**
     * 下载云端APK文件.
     * @param url apk文件地址.
     * @param range 断点下载开始位置 bytes={begin}-{end}.
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadVersion(@Url String url,@Header("If-Range") String ifRange, @Header("Range") String range);
}
