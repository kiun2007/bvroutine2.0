package com.szxgm.gusustreet.model.base;


import com.szxgm.gusustreet.R;

import java.util.List;
import kiun.com.bvroutine.data.viewmodel.SpinnerDataWrap;
import kiun.com.bvroutine.data.viewmodel.SpinnerItem;

public class StaticSpinnerDataWrap<T extends SpinnerItem> extends SpinnerDataWrap<T> {

    public StaticSpinnerDataWrap(List<T> list) {
        super(list);
    }

    @Override
    public int itemLayoutId() {
        return R.layout.item_spinner_text;
    }


    @Override
    public int dropDownViewId() {
        return R.layout.spinner_dropdown_item;
    }
}
