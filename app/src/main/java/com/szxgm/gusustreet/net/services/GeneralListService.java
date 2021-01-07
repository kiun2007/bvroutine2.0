package com.szxgm.gusustreet.net.services;

import com.szxgm.gusustreet.net.responses.NetListWrapper;
import com.szxgm.gusustreet.net.responses.NetWrapper;
import com.szxgm.gusustreet.model.dto.MineApply;
import com.szxgm.gusustreet.model.dto.Person;
import com.szxgm.gusustreet.model.dto.Street;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import java.util.List;
import kiun.com.bvroutine.base.binding.variable.RetrofitVariableSet;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

@AutoImport(RetrofitVariableSet.class)
public interface GeneralListService {

    /**
     * 通用列表查询方法.
     * @param query
     * @return
     */
    @POST("table/search")
//    @Headers({ConstValue.Security+":"+ConstValue.B})
    Call<NetListWrapper<Object>> search(@Body PagerBean query);

    //申请列表.
    @POST("API/pb/allList")
    Call<NetListWrapper<MineApply>> allGetList(@Body PagerBean pagerBean);

    //审批列表.
    @POST("API/pb/xjList")
    Call<NetListWrapper<MineApply>> allxjGetList(@Body PagerBean pagerBean);

    //全部人员
    @POST("API/pb/peopleList")
    Call<NetListWrapper<Person>> allPerson(@Body PagerBean pagerBean);

    @GET("app/river/getStreets")
    Call<NetWrapper<List<Street>>> getStreets();
}
