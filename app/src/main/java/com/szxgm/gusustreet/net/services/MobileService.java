package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.model.dto.mobile.AppealType;
import com.szxgm.gusustreet.model.dto.mobile.AreaRiver;
import com.szxgm.gusustreet.model.dto.mobile.AreaRiverOffice;
import com.szxgm.gusustreet.model.dto.mobile.CombinedTaskInfo;
import com.szxgm.gusustreet.model.dto.mobile.Department;
import com.szxgm.gusustreet.model.dto.mobile.DepartmentType;
import com.szxgm.gusustreet.model.dto.mobile.EventType;
import com.szxgm.gusustreet.model.dto.mobile.OrderDelay;
import com.szxgm.gusustreet.model.dto.mobile.OrderDelayTimer;
import com.szxgm.gusustreet.model.dto.mobile.OrderFlowTail;
import com.szxgm.gusustreet.model.dto.mobile.OrderInfoDetailed;
import com.szxgm.gusustreet.model.dto.mobile.OrderTask;
import com.szxgm.gusustreet.model.dto.mobile.PersonDisposal;
import com.szxgm.gusustreet.model.dto.mobile.TimeLimitCase;
import com.szxgm.gusustreet.model.query.OrderFieldReq;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.requests.mobile.CombinedDepartmentReq;
import com.szxgm.gusustreet.net.requests.mobile.DepartmentReq;
import com.szxgm.gusustreet.net.requests.mobile.OfficeTypeReq;
import com.szxgm.gusustreet.net.requests.mobile.TaskDisposalReq;
import com.szxgm.gusustreet.net.requests.mobile.TaskUnionDisposalReq;
import com.szxgm.gusustreet.net.responses.NetListRowsWrapper;
import com.szxgm.gusustreet.net.responses.NetListWrapper;
import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.grid.Grid;
import com.szxgm.gusustreet.model.dto.MobileOrder;
import com.szxgm.gusustreet.net.responses.OriginalListWrapper;

import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.QueryBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

@AutoImport(RetrofitVariableSet.class)
public interface MobileService {

    /**
     * 提交移动工单.
     * @param order
     * @return
     */
    @FormUrlEncoded
    @POST("xtcz/xtczOrderInfo/saveAddWithSend")
    Call<NetWrapper<String>> commitOrder(@FieldMap MobileOrder order);

    /**
     * 获取网格.
     * @return
     */
    @POST("API/common/gridList")
    Call<NetWrapper<List<Grid>>> getGrid(@Body QueryBean queryBean);

    /**
     * 获取诉求类型.
     * @return
     */
    @POST("API/order/getAppealTypeList")
    Call<NetWrapper<List<AppealType>>> getAppealType();

    /**
     * 获取事件类型.
     * @return
     */
    @POST("API/order/getOrderTypeList")
    Call<NetWrapper<List<EventType>>> getEventType();

    /**
     * 获取代办列表
     * @param fieldReq
     * @return
     */
    @POST("activiti/getToDoOrderList")
    @FormUrlEncoded
    Call<NetListRowsWrapper<OrderTask>> getOrderToDoList(@FieldMap OrderFieldReq fieldReq);

    /**
     * 获取代办工单
     * @return
     */
    @POST("API/order/getOrderToDoList")
    Call<NetListWrapper<Object>> getOrderToDoList(@Body Map<String, String> orderReq, @Header("itemClz") String clzName);

    /**
     * 获取历史记录
     * @param req
     * @return
     */
    @POST("activiti/getHistoricList")
    @FormUrlEncoded
    Call<NetListRowsWrapper<OrderInfoDetailed>> getHistoricList(@FieldMap Map<String, String> req);

    @POST("API/order/getHistoryOrderInfo")
    Call<NetWrapper<OrderTask>> getHistoryOrderInfo(@Body OrderInfoReq req);

    /**
     * 签收工单.
     * @param taskId
     * @return
     */
    @GET("activiti/claim")
    Call<NetWrapper<String>> claimOrderForStreet(@Query("taskId") String taskId);

    /**
     * 获取任务详情.
     * @param req
     * @return
     */
    @POST("API/order/getTaskInfo")
    Call<NetWrapper<OrderTask>> getTaskInfo(@Body OrderInfoReq req);

    /**
     * 获取协同处置事件详情
     * @param req
     * @return
     */
    @POST("API/order/getHarmonizeDetail")
    Call<NetWrapper<CombinedTaskInfo>> getHarmonizeDetail(@Body OrderInfoReq req);


    /**
     * 获取工单流转信息.
     * @return
     */
    @GET("activiti/histoicFlowList")
    Call<NetWrapper<List<OrderFlowTail>>> histoicFlowList(@Query("procInsId") String procInsId);

    /**
     * 获取处置部门类型.
     * @return
     */
    @POST("API/order/getOfficeType")
    Call<NetWrapper<List<DepartmentType>>> getOfficeType(@Body OfficeTypeReq req);

    /**
     * 获取部门名称.
     * @param req
     * @return
     */
    @POST("xtcz/xtczHandleOffice/selectOfficeTreeList")
    @FormUrlEncoded
    Call<NetListRowsWrapper<Department>> getHarmonizeOfficeList(@FieldMap DepartmentReq req);

    /**
     * 获取时间限制列表.
     * @return
     */
    @POST("API/order/getLimitTimeList")
    Call<NetWrapper<List<TimeLimitCase>>> getLimitTimeList();

    /**
     * 处理工单.
     * @param taskDisposalReq
     * @return
     */
    @POST("API/order/handleTaskInfo")
    Call<NetWrapper<String>> handleTaskInfo(@Body TaskDisposalReq taskDisposalReq);

    /**
     * 获取类型时间.
     * @param type
     * @return
     */
    @POST("xtcz/xtczOrderDelay/getHourNumber")
    @FormUrlEncoded
    Call<NetWrapper<OrderDelayTimer>> getHourNumber(@Field("appealType") String appealType, @Field("type") String type);

    @POST("API/order/getHarmonizeOfficeList")
    Call<NetWrapper<List<Department>>> getHarmonizeOfficeList(@Body CombinedDepartmentReq req);

    /**
     * 获取部门处置情况.
     * @param orderId
     * @return
     */
    @POST("xtcz/xtczHandleOfficeRefine/getListByOrderId")
    @FormUrlEncoded
    Call<NetWrapper<List<PersonDisposal>>> getListByOrderId(@Field("orderId") String orderId);

    /**
     * 申请延期处理.
     * @param orderDelay
     * @return
     */
    @POST("xtcz/xtczOrderDelay/add")
    @FormUrlEncoded
    Call<NetWrapper<String>> applyOrderDelay(@FieldMap OrderDelay orderDelay);

    /**
     * 处置协同事件.
     * @param req
     * @return
     */
    @FormUrlEncoded
    @POST("xtcz/xtczHandleOfficeRefine/edit")
    Call<NetWrapper<String>> handleHarmonize(@FieldMap TaskUnionDisposalReq req);

    /**
     * 获取区河长制河段.
     * @return
     */
    @GET("xtcz/xtczOrderInfo/riverTreeData")
    Call<OriginalListWrapper<AreaRiver>> riverTreeData();

    @POST("API/hzz/riverOfficeList")
    Call<NetWrapper<AreaRiverOffice>> riverOfficeList(@Body Map<String, Object> req);
}
