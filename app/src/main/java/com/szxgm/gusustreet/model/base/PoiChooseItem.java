package com.szxgm.gusustreet.model.base;

import com.amap.api.services.core.PoiItem;

import kiun.com.bvroutine.base.EventBean;

public class PoiChooseItem extends EventBean {

    private boolean chose;

    private PoiItem poiItem;


    public PoiChooseItem(PoiItem poiItem) {
        this.chose = false;
        this.poiItem = poiItem;
    }

    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
        onChanged(false);
    }
}
