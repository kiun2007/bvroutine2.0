package com.szxgm.gusustreet.ui.activity.list;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.model.dto.Notice;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import com.szxgm.gusustreet.ui.activity.NoticeActivityHandler;

import kiun.com.bvroutine.handlers.ListHandler;

/**
 * 消息列表.
 */
public class NoticeListActivity extends GeneralListActivity implements GeneralHandlerController {

    public static final Class clz = NoticeListActivity.class;

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("系统通知");
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_notice_message;
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(Notice.class).field("noticeTitle").put("status", QueryType.Eq, "0").orderBy("CREATE_TIME", QueryType.Desc);
    }

    @Override
    public ListHandler getHandler() {
        return new NormalHandler<Notice>().addTag(0, NoticeActivityHandler::openActivityNormal);
    }
}
