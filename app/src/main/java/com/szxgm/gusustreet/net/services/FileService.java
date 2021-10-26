package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetFileWrapper;
import com.szxgm.gusustreet.net.responses.NetWrapper;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

@AutoImport(RetrofitVariableSet.class)
public interface FileService {

    @POST("file/upload")
    @Multipart
    Call<NetWrapper<String>> upload(@Part MultipartBody.Part file);

    /**
     * ruoyi系统文件上传.
     * @param file
     * @return
     */
    @POST("common/upload")
    @Multipart
    Call<NetFileWrapper> commonUpload(@Part MultipartBody.Part file);

    @POST("common/uploadThumb")
    @Multipart
    Call<NetFileWrapper> commonUploadThumb(@Part MultipartBody.Part file, @Part MultipartBody.Part thumb);
}
