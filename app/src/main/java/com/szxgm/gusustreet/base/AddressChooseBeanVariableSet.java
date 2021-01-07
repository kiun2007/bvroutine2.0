package com.szxgm.gusustreet.base;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.szxgm.gusustreet.model.base.AddressChooseBean;
import com.szxgm.gusustreet.utils.AMapLocationUtil;

import java.lang.reflect.Method;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.binding.variable.ObjectVariableSet;
import kiun.com.bvroutine.utils.LocationUtils;

public class AddressChooseBeanVariableSet extends ObjectVariableSet<AddressChooseBean> {

    public AddressChooseBeanVariableSet(Context context, ViewDataBinding viewDataBinding, Class<? extends AddressChooseBean> clz, Method method) {
        super(context, viewDataBinding, clz, method);
    }

    @Override
    public void start() {
        super.start();

        if (context instanceof BVBaseActivity){
            ((BVBaseActivity) context).startPermission(()->{
                AMapLocationUtil.startLocation(context, 1000, value::setChooseAddress, true);
            }, LocationUtils.PERMISSION);
        }
    }

    @Override
    public void end() {
    }
}
