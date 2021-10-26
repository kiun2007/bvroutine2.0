package com.szxgm.gusustreet.base.list.handler;

import android.content.Context;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;

import java.util.HashMap;
import java.util.Map;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.PutVoidCaller;

public class NormalHandler<T> extends ListHandler<T> {

    Map<Integer, PutVoidCaller<Context, T>> tagCaller = new HashMap<>();

    public NormalHandler(Context context) {
        this();
    }

    public NormalHandler(){
        super(BR.handler, R.layout.list_error_normal);
    }

    public NormalHandler addTag(int tag, PutVoidCaller<Context, T> caller){
        tagCaller.put(tag, caller);
        return this;
    }

    @Override
    public void onClick(Context context, int tag, T data) {

        PutVoidCaller<Context, T> caller = tagCaller.get(tag);
        if (caller != null){
            try {
                caller.call(context, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
