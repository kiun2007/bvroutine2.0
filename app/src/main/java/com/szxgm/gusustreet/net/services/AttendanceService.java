package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.dto.ClockTimeTree;
import com.szxgm.gusustreet.model.dto.attendance.WorkTimePerson;
import com.szxgm.gusustreet.model.dto.attendance.WorkTimeTree;
import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.Apply;
import com.szxgm.gusustreet.model.dto.attendance.ArriveClock;
import com.szxgm.gusustreet.model.dto.LeaveApply;
import com.szxgm.gusustreet.model.dto.attendance.LeaveClock;
import com.szxgm.gusustreet.model.dto.LeaveDetail;
import com.szxgm.gusustreet.model.dto.Overtime;
import com.szxgm.gusustreet.model.dto.ReplaceLeave;
import com.szxgm.gusustreet.model.dto.statistics.TimeTotal;
import com.szxgm.gusustreet.model.dto.attendance.WorkTime;
import com.szxgm.gusustreet.net.requests.ApplyReq;
import com.szxgm.gusustreet.net.requests.ClockHistoryReq;
import com.szxgm.gusustreet.net.requests.TimeReq;
import com.szxgm.gusustreet.net.requests.WorkTimeReq;
import com.szxgm.gusustreet.net.responses.OriginalListWrapper;
import com.szxgm.gusustreet.net.responses.OriginalNumber;
import java.util.List;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 考勤相关.
 */
@AutoImport(RetrofitVariableSet.class)
public interface AttendanceService {


    //考勤打卡---------------------------------------
    /**
     * 获取排班时间.
     * @param workTimeReq
     * @return
     */
    @POST("API/pb/pbList")
    Call<NetWrapper<List<WorkTime>>> pbList(@Body WorkTimeReq workTimeReq);

    /**
     * 获取排班班次.
     * @param type
     *  type = 0 就是查找该用户的已有排班
     *  type = 1 就是查找该部门没有这个用户的排班
     * @return
     */
    @GET("shift/rcgPbxxPb/treedata/{type}")
    Call<OriginalListWrapper<WorkTimeTree>> treeWorkTimes(@Path("type") String type);

    /**
     * 上班打卡.
     * @param arriveClock
     * @return
     */
    @POST("API/pb/dakaBeginSaveList")
    Call<NetWrapper<String>> dakaBeginSaveList(@Body ArriveClock arriveClock);

    /**
     * 下班打卡.
     * @param leaveClock
     * @return
     */
    @POST("API/pb/dakaEndSaveList")
    Call<NetWrapper<String>> dakaEndSaveList(@Body LeaveClock leaveClock);

    /**
     * 打卡历史.
     */
    @POST("API/pb/dakaGetList")
    Call<NetWrapper<List<LeaveClock>>> clockHistory(@Body ClockHistoryReq req);

    //申请---------------------------
    /**
     * 请假.
     * @param leaveApply
     * @return
     */
    @POST("shift/rcgPbxxQjsq/add")
    @FormUrlEncoded
    Call<NetWrapper<String>> qjSaveList(@FieldMap LeaveApply leaveApply);

    /**
     * 加班.
     * @param overtime
     * @return
     */
    @POST("API/pb/jbSaveList")
    Call<NetWrapper<String>> jbSaveList(@Body Overtime overtime);

    /**
     * 获取班次人员.
     * @param workTimeId 班次ID.
     * @return
     */
    @GET("shift/rcgPbxxPb/treeuserdata/{workTimeId}")
    Call<OriginalListWrapper<WorkTimePerson>> treeUser(@Path("workTimeId") String workTimeId);

    /**
     * 获取打卡异常记录
     * @param type 申报类型 1迟到 2早退
     * @return
     */
    @GET("shift/rcgPbxxDaka/dakadata/{type}")
    Call<OriginalListWrapper<ClockTimeTree>> treeClock(@Path("type") String type);

    /**
     * 换班.
     * @param replaceLeave
     * @return
     */
    @POST("shift/rcgPbxxTbsq/add")
    @FormUrlEncoded
    Call<NetWrapper<String>> tbSaveList(@FieldMap ReplaceLeave replaceLeave);

    /**
     * 补卡申报.
     * @param apply
     * @return
     */
    @POST("shift/rcgPbxxSbsq/add")
    @FormUrlEncoded
    Call<NetWrapper<String>> sbSaveList(@FieldMap Apply apply);

    //审核-----------
    /**
     * 请假审核.
     * @param req
     * @return
     */
    @POST("API/pb/qjshSaveList")
    Call<NetWrapper<String>> qjshSaveList(@Body ApplyReq req);

    /**
     *  加班审核.
     * @param req
     * @return
     */
    @POST("API/pb/jbshSaveList")
    Call<NetWrapper<String>> jbshSaveList(@Body ApplyReq req);

    /**
     * 调班审核.
     * @param req
     * @return
     */
    @POST("API/pb/tbshSaveList")
    Call<NetWrapper<String>> tbshSaveList(@Body ApplyReq req);

    /**
     * 申报审核
     * @param req
     * @return
     */
    @POST("API/pb/sbshSaveList")
    Call<NetWrapper<String>> sbshSaveList(@Body ApplyReq req);

    //---统计专用
    /**
     * 请假统计.
     * @param req
     * @return
     */
    @POST("API/pb/qjTotalList")
    Call<NetWrapper<List<TimeTotal>>> qjTotalList(@Body TimeReq req);

    /**
     * 加班统计
     * @param req
     * @return
     */
    @POST("API/pb/jbTotalList")
    Call<NetWrapper<List<TimeTotal>>> jbTotalList(@Body TimeReq req);

    @FormUrlEncoded
    @POST("API/pb/qjXqList")
    Call<NetWrapper<LeaveDetail>> qjXqList(@Field("id") String id);

    @FormUrlEncoded
    @POST("API/pb/jbXqList")
    Call<NetWrapper<LeaveDetail>> jbXqList(@Field("id") String id);

    @FormUrlEncoded
    @POST("API/pb/tbXqList")
    Call<NetWrapper<LeaveDetail>> tbXqList(@Field("id") String id);

    @FormUrlEncoded
    @POST("API/pb/sbXqList")
    Call<NetWrapper<LeaveDetail>> sbXqList(@Field("id") String id);

    @POST("shift/rcgPbxxPb/selectPbTimeAll/{start}/{end}")
    Call<OriginalNumber> getLeaveTime(@Path("start") String start, @Path("end") String end);
}
