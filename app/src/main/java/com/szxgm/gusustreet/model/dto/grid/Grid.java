package com.szxgm.gusustreet.model.dto.grid;

import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.model.dto.mobile.EventType;

import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.interfaces.TreeItem;
import kiun.com.bvroutine.interfaces.binding.NetTextBean;

public class Grid implements NetTextBean, TreeItem {

    /**
     * 网格ID
     */
    private String gridId;

    /**
     * 网格名称
     */
    private String gridName;

    private List<Grid> childs;

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public List<Grid> getChilds() {
        return childs;
    }

    public void setChilds(List<Grid> childs) {
        this.childs = childs;
    }

    @Override
    public String getNetText() {
        return String.format("%s(编号:%s)", gridName, gridId);
    }

    @Override
    public int itemLayoutId() {
        return R.layout.item_grid_info;
    }

    @Override
    public boolean withChild(int parentLevel) {
        return parentLevel <= 2;
    }

    @Override
    public List<?> itemList() {
        List<Grid> newChilds = childs;
        childs = new ArrayList<>();
        return newChilds;
    }
}
