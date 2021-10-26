package com.szxgm.gusustreet.ui.fragment.main;

import android.widget.Toast;

import com.szxgm.gusustreet.MainApplication;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.databinding.FragmentHomeBinding;
import com.szxgm.gusustreet.model.base.TaskStatus;
import com.szxgm.gusustreet.model.dto.Notice;
import com.szxgm.gusustreet.model.dto.mobile.OrderTask;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.model.query.OrderFieldReq;
import com.szxgm.gusustreet.model.query.OrderInfoReq;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.net.services.MobileService;
import com.szxgm.gusustreet.ui.activity.NoticeActivityHandler;
import com.szxgm.gusustreet.ui.activity.workbench.WorkOrderDetailActivityHandler;

import java.util.List;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.utils.AlertUtil;
import kiun.com.bvroutine.utils.RetrofitUtil;

/**
 * 首页
 */
public class HomeFragment extends RequestBVFragment<FragmentHomeBinding>{

    GeneralListQuery messageBean;

    @Override
    public int getViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        //查消息
        messageBean = new GeneralListQuery(Notice.class).orderBy("CREATE_TIME", QueryType.Desc).put("status", QueryType.Eq, "0").setLimit(3);
        getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceList(GeneralListService.class, s -> s.search(messageBean)), mViewBinding::setMessageList);


//        searchTask();
//        mViewBinding.totalRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            searchTask();
//        });

        MainApplication.<MainApplication>getApplication().getPresenter().userLoginListener(v -> {
            mViewBinding.setIsLogin(v);
        });

//        mViewBinding.setTaskHandler(new NormalHandler<OrderTask>().addTag(0, (context, item)->{
//            if (item.getStatus() == TaskStatus.todo){
//                WorkOrderDetailActivityHandler.openActivityNormal(context, new OrderInfoReq(item), item.getTaskDefKey());
//            }else{
//                AlertUtil.build(context, "是否签收任务\"%s\"", item.getVars().getMap().getOrderTitle())
//                        .setPositiveButton("签收", (dialog, which) -> {
//                            getRequestPresenter().addRequest(()->RetrofitUtil.callServiceData(MobileService.class, s -> s.claimOrderForStreet(item.getTaskId())),v -> {
//                                Toast.makeText(getContext(), "签收任务成功", Toast.LENGTH_LONG).show();
//                                item.setStatus(TaskStatus.todo);
//                            });
//                        }).setNegativeButton("不签收", null).show();
//            }
//        }));

        mViewBinding.setMessageHandler(new NormalHandler<Notice>().addTag(0, NoticeActivityHandler::openActivityNormal));
    }

    private void searchTask(){
        getRequestPresenter().addRequest(()-> RetrofitUtil.callServiceList(MobileService.class,
                s -> s.getOrderToDoList(new OrderFieldReq(mViewBinding.totalRadio.isChecked()))), this::onTaskListComplete);
    }

    private void onTaskListComplete(List taskList){
        mViewBinding.setTaskList(taskList.subList(0, Math.min(3, taskList.size())));
    }
}
