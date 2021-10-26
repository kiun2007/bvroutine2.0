package com.szxgm.gusustreet.ui.activity.list;

import android.app.Activity;
import android.content.Intent;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.list.handler.NormalHandler;
import com.szxgm.gusustreet.base.QueryType;
import com.szxgm.gusustreet.base.general.GeneralHandlerController;
import com.szxgm.gusustreet.model.dto.river.RiverItem;
import com.szxgm.gusustreet.model.dto.Street;
import com.szxgm.gusustreet.model.query.GeneralListQuery;
import com.szxgm.gusustreet.net.services.GeneralListService;
import com.szxgm.gusustreet.ui.activity.ClassifyListActivity;
import com.szxgm.gusustreet.ui.activity.river.RiverDetailActivityHandler;
import java.util.List;
import kiun.com.bvroutine.handlers.ListHandler;

import static com.szxgm.gusustreet.views.GeneralItemText.ID;
import static com.szxgm.gusustreet.views.GeneralItemText.RETURN;
import static com.szxgm.gusustreet.views.GeneralItemText.TITLE;

/**
 * 河流资料列表.
 */
public class RiverListActivity extends ClassifyListActivity<Street> implements GeneralHandlerController {

    public static final Class clz = RiverListActivity.class;

    @Override
    protected List<Street> classifyList() throws Exception {
        List<Street> streets = rbp.callServiceData(GeneralListService.class, s -> s.getStreets());
        streets.add(0, new Street(){{
            setId(null);
            setName("全部");
        }});
        return streets;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_river_info;
    }

    @Override
    protected String title(Street item) {
        return item.getName();
    }

    @Override
    protected void onSelected(Street item) {
        general.getQuery().put("area", QueryType.Eq, item.getId()).field("rivername");
    }

    @Override
    public void initView() {
        super.initView();
        if (getIntent().getBooleanExtra(RETURN, false)){
            getBarItem().setTitle("选择河道");
        }else{
            getBarItem().setTitle("河道管理");
        }
    }

    @Override
    public GeneralListQuery getQuery() {
        return new GeneralListQuery(RiverItem.class);
    }

    @Override
    public ListHandler getHandler() {
        return new NormalHandler<RiverItem>().addTag(0, (context, riverItem)->{
            if (getIntent().getBooleanExtra(RETURN, false)){
                setResult(Activity.RESULT_OK,new Intent().putExtra(ID, riverItem.getId()).putExtra(TITLE, riverItem.getRivername()));
                finish();
            }else{
                RiverDetailActivityHandler.openActivityNormal(context, riverItem);
            }
        });
    }
}
