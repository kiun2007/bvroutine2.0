package com.szxgm.gusustreet.model.dto.mobile;

import com.szxgm.gusustreet.R;

import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.interfaces.TreeItem;

public class EventType implements TreeItem {

    /**
     * 事件ID.
     */
    String typeId;

    /**
     * 类型名称.
     */
    String typeName;

    /**
     * 类型编号.
     */
    String typeCode;

    /**
     * 是否有权限
     */
    String isPriority;

    /**
     * 子集.
     */
    List<EventType> childs;

    @Override
    public int itemLayoutId() {
        return R.layout.item_event_type_tree;
    }

    @Override
    public boolean withChild(int parentLevel) {
        return parentLevel <= 2;
    }

    @Override
    public List<?> itemList() throws Exception {
        List<EventType> newChilds = childs;
        childs = new ArrayList<>();
        return newChilds;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(String isPriority) {
        this.isPriority = isPriority;
    }

    public List<EventType> getChilds() {
        return childs;
    }

    public void setChilds(List<EventType> childs) {
        this.childs = childs;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
