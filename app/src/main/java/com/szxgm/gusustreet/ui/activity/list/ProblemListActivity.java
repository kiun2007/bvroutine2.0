package com.szxgm.gusustreet.ui.activity.list;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.dto.river.RiverProblem;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;
import com.szxgm.gusustreet.ui.activity.river.RiverProblemActivity;

public class ProblemListActivity extends GeneralListActivity {

    public static final Class clz = ProblemListActivity.class;

    @Override
    public int getItemLayout() {
        return R.layout.item_river_problem;
    }

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("问题列表");
        getBarItem().setRightTitle("上报问题");
        getBarItem().getHandler().setRightCallBack(()->{
            startForResult(RiverProblemActivity.clz, intent->{
            });
        });
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(RiverProblem.class);
    }
}
