package com.szxgm.gusustreet.net;

import android.graphics.drawable.AnimationDrawable;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static okhttp3.Protocol.HTTP_1_1;

public class SimulationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().url().toString();
        Response response = null;
        Response.Builder builder = new Response.Builder();
        builder.protocol(HTTP_1_1);
        builder.request(chain.request());
        builder.code(200);

        String content = null;
        if (url.contains("/jdpt/API/pb/pbList")){
            content = "{\"msg\":\"成功\",\"code\":0,\"data\":[{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:56\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"0e674546e4414465903740fdf41cbd0d\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-01\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 13:05:57\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"36697ff7909743d28f4c748a75dec90e\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-01\",\"pbWeek\":null,\"pbTimes\":\"下午班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"13:00:00\",\"pbEnd\":\"17:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-02\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:56\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"baf544bc5f784ffbb151d62a4dbfac1a\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-02\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 13:05:57\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"0586ed841ac24fe6995aa8fbe6308121\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-02\",\"pbWeek\":null,\"pbTimes\":\"下午班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"13:00:00\",\"pbEnd\":\"17:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-02\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:57\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"b5b412c952b145a38feb3b5d893182ae\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-03\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:57\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"94d81c40c4f548a39e47565332655890\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-08\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:58\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"56f2ab3df4f84407a8c45974ac6df132\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-09\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:58\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"2dc2337ca3724727b19517789ecee7fb\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-10\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"30b19dd54a324f718e96da22b03b2d57\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-15\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"0bbc437671b446fbb15e045d88f407ad\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-16\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"3b14560d3ebd43108be99dde698b9946\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-17\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"2f9469c6e52c4cd68792bfccdd10737d\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-22\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"7b42b02299d24339a274605fd2efe103\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-23\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"f3782b4b94f948ce9a945e2c2c7fcd66\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-24\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"25d1e0c84d38427fbf7a30e97ef7c9c3\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-29\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false},{\"searchValue\":null,\"createBy\":\"1\",\"createTime\":\"2020-06-01 10:47:59\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"pbId\":\"548d87025c834e639a74dc0fa019f8e8\",\"pbDept\":\"姑苏区\",\"pbTime\":\"2020-06-30\",\"pbWeek\":null,\"pbTimes\":\"早班\",\"pbUser\":null,\"delFlag\":null,\"bcId\":null,\"pbBegin\":\"07:00:00\",\"pbEnd\":\"12:00:00\",\"pbStartDate\":\"2020-06-01\",\"pbEndDate\":\"2020-06-30\",\"deptUserIds\":null,\"flag\":false}]}";
        }

        if (url.contains("/jdpt/API/pb/dakaGetList")){
            content = "{\"msg\":\"成功\",\"code\":0,\"data\":[{\"dkDept\":\"姑苏区\",\"dkPbid\":\"b5b412c952b145a38feb3b5d893182ae\",\"dkBegin\":\"2020-06-03 07:02:55\",\"dkEnd\":\"2020-06-03 11:23:13\",\"dkLate\":\"0\",\"dkEarly\":\"0\",\"dkId\":\"48bc733a7dea4c408f48ff7a11437e3b\",\"dkPbname\":\"早班(07:00:00-12:00:00)\"},{\"dkUserid\":\"1\",\"dkDept\":\"姑苏区\",\"dkPbid\":\"b5b412c952b145a38feb3b5d893182ae\",\"dkBegin\":\"2020-06-04 07:00:00\",\"dkEnd\":null,\"dkLate\":\"1\",\"dkEarly\":\"1\",\"dkId\":\"48bc733a7dea4c408f48ff7a11437e3b\",\"dkPbname\":\"早班(07:00:00-12:00:00)\"}]}";
        }

        if (url.contains("/jdpt/API/pb/qjTotalList")){
            content = "{\"msg\":\"成功\",\"code\":0,\"data\":[{\"total\":\"16\",\"end\":\"2020-06-09 16:59:00\",\"begin\":\"2020-06-02 16:59:00\"}]}";
        }

        if (url.contains("/jdpt/API/pb/jbTotalList")){
            content = "{\"msg\":\"成功\",\"code\":0,\"data\":[{\"total\":\"16\",\"end\":\"2020-06-09 16:59:00\",\"begin\":\"2020-06-02 16:59:00\"}]}";
        }

        if (url.contains("/table/search")){
            content = "{\"msg\":\"操作成功\",\"code\":0,\"data\":{\"total\":2,\"rows\":[{\"imUser\":\"51020\",\"userId\":\"41\",\"deptId\":103,\"deptName\":\"研发部门\",\"userName\":\"视频通话测试\",\"sex\":\"0\",\"avatar\":null,\"id\":\"202007031730100145379959\",\"new\":true}],\"code\":0}}";
        }


        if (url.contains("/jdpt/login")){
            content = "{\"msg\":\"操作成功\",\"code\":0,\"data\":{\"searchValue\":null,\"createBy\":null,\"createTime\":\"2018-03-16 11:33:00\",\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"userId\":1,\"deptId\":103,\"parentId\":null,\"roleId\":null,\"loginName\":null,\"userName\":\"若依\",\"email\":\"ry@163.com\",\"phonenumber\":\"15888888888\",\"sex\":null,\"avatar\":\"\",\"password\":null,\"salt\":\"111111\",\"status\":\"0\",\"delFlag\":null,\"loginIp\":null,\"loginDate\":\"2020-07-03 17:37:58\",\"dept\":{\"searchValue\":null,\"createBy\":null,\"createTime\":null,\"updateBy\":null,\"updateTime\":null,\"remark\":null,\"params\":{},\"deptId\":null,\"parentId\":null,\"ancestors\":null,\"deptName\":null,\"orderNum\":null,\"leader\":null,\"phone\":null,\"email\":null,\"status\":null,\"delFlag\":null,\"parentName\":null},\"roles\":null,\"roleIds\":null,\"postIds\":null,\"imUser\":\"71039\",\"imPwd\":\"71039\",\"deptName\":\"研发部门\",\"admin\":true}}";
        }

        if (content != null){
            builder.body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), content));
            builder.message("Success");
            response = builder.build();
        }

        return response;
    }
}
