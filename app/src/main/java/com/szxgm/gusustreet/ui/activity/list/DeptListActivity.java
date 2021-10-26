package com.szxgm.gusustreet.ui.activity.list;

import com.szxgm.gusustreet.model.dto.Dept;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.ui.activity.GeneralListActivity;

public class DeptListActivity extends GeneralListActivity {

    public static final Class clz = DeptListActivity.class;

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(Dept.class);
    }
}
