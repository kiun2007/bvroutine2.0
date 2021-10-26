package com.szxgm.gusustreet.base.list.handler;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.base.presenter.imp.GeneralListPresenter;

import java.util.HashMap;
import java.util.Map;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.DefGet;

public class RefreshHandler<T> extends ListHandler<T> {

    private Map<Integer, DefGet<Intent, Context, T>> tagCaller = new HashMap<>();

    GeneralListPresenter presenter;
    BVBaseActivity activity;

    public RefreshHandler(@NonNull GeneralListPresenter presenter, BVBaseActivity activity){
        super(BR.handler, R.layout.list_error_normal);
        this.presenter = presenter;
        this.activity = activity;
    }

    public RefreshHandler addTag(int tag, DefGet<Intent, Context, T> caller) {
        tagCaller.put(tag, caller);
        return this;
    }

    @Override
    public void onClick(Context context, int tag, T data) {

        DefGet<Intent, Context, T> caller = tagCaller.get(tag);
        if (caller != null){
            Intent intent = caller.get(context, data);
            if (intent != null){
                activity.startForResult(intent, ret->{
                    presenter.refresh();
                });
            }
        }
    }
}
