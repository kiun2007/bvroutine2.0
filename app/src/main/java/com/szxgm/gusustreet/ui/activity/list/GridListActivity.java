package com.szxgm.gusustreet.ui.activity.list;

import android.app.Activity;
import android.content.Intent;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.dto.grid.Grid;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.ui.activity.ClassifyListActivity;
import com.szxgm.gusustreet.views.GeneralItemText;
import java.util.List;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.utils.RetrofitUtil;

public class GridListActivity extends ClassifyListActivity<Grid> implements GeneralHandlerController {

    public static final Class clz = GridListActivity.class;

    @Override
    public void initView() {
        super.initView();
        getBarItem().setTitle("社区网格");
    }

    @Override
    protected List<Grid> classifyList() throws Exception {

        List<Grid> gridList = RetrofitUtil.callServiceList(GeneralListService.class,
                s->s.search(new GeneralListQuery(Grid.class).put("parentId", QueryType.Eq, "100")));
        gridList.add(0, new Grid(){{setGridName("全部");}});
        return gridList;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_grid_info;
    }

    @Override
    protected String title(Grid item) {
        return item.getGridName();
    }

    @Override
    protected void onSelected(Grid item) {
        general.getQuery().put("parentId", QueryType.Eq, item.getGridId() == null ? null : item.getGridId());
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(Grid.class).field("gridName").put("gridLevel", QueryType.Eq, "2").setPageSize(20);
    }

    @Override
    public ListHandler getHandler() {
        return new NormalHandler<Grid>().addTag(0, (context, data)->{
            setResult(Activity.RESULT_OK, new Intent().putExtra(GeneralItemText.TITLE, data.getGridName()).putExtra(GeneralItemText.ID, data.getGridId()));
            finish();
        });
    }
}
