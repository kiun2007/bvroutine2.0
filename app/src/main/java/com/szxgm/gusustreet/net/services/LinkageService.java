package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.other.GridDept;
import com.szxgm.gusustreet.model.other.GridPatrol;
import com.szxgm.gusustreet.model.other.GridPatrolItem;
import com.szxgm.gusustreet.model.other.GridPatrolPoint;
import com.szxgm.gusustreet.model.other.GridPerson;
import com.szxgm.gusustreet.model.other.GridVisit;
import com.szxgm.gusustreet.model.other.GridVisitItem;
import com.szxgm.gusustreet.net.requests.GridPersonReq;
import com.szxgm.gusustreet.net.requests.linkage.PatrolListReq;
import com.szxgm.gusustreet.net.requests.linkage.VisitListReq;
import com.szxgm.gusustreet.net.responses.NetGridListWrapper;
import com.szxgm.gusustreet.net.responses.NetListWrapper;
import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.net.responses.OriginalListWrapper;

import java.util.List;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.net.Builder;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 联动相关服务.
 */
@AutoImport(RetrofitVariableSet.class)
public interface LinkageService {

    /**
     * 获取街道信息.
     * @param parentDeptId
     * @return
     */
    @GET("linkage/queryByParentDeptId")
    Call<NetWrapper<List<GridDept>>> queryByParentDeptId(@Query("parentDeptId") String parentDeptId);

    /**
     * 获取人员列表.
     * @param req
     * @return
     */
    @FormUrlEncoded
    @POST("linkage/layUIPage")
    Call<NetListWrapper<GridPerson>> queryPerson(@FieldMap GridPersonReq req);

    /**
     * 指派走访任务.
     * @param gridVisit
     * @return
     */
    @POST("linkage/saveGridVisit")
    Call<NetWrapper<String>> save(@Body GridVisit gridVisit);

    /**
     * 指派巡查任务.
     * @param gridPatrol
     * @return
     */
    @POST("linkage/saveGridPatrol")
    Call<NetWrapper<String>> save(@Body GridPatrol gridPatrol);

    /**
     * 获取巡查列表.
     * @return
     */
    @GET("linkage/gridPatrolList")
    Call<NetListWrapper<GridPatrolItem>> gridPatrolList(@QueryMap PatrolListReq param);

    /**
     * 获取走访列表.
     * @param param
     * @return
     */
    @GET("linkage/gridVisitList")
    Call<NetListWrapper<GridVisitItem>> gridVisitList(@QueryMap VisitListReq param);

    /**
     * 获取巡查轨迹.
     * @return
     */
    @GET("linkage/viewPoint")
    Call<NetListWrapper<GridPatrolPoint>> viewPoint(@Query("id") String id);
}
