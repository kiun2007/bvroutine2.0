package com.szxgm.gusustreet.ui.fragment.other;

import android.content.Context;
import android.content.Intent;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.FragmentGridPatrolBinding;
import com.szxgm.gusustreet.model.other.GridPatrolItem;
import com.szxgm.gusustreet.net.requests.linkage.PatrolListReq;
import com.szxgm.gusustreet.ui.activity.other.SpecialPatrolDetailsActivity;

import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.handlers.ListHandler;

public class GridPatrolFragment extends RequestBVFragment<FragmentGridPatrolBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_grid_patrol;
    }

    @Override
    public void initView() {
        mViewBinding.setHandler(listHandler);
        mViewBinding.setReq(new PatrolListReq());
    }

    ListHandler<GridPatrolItem> listHandler = new ListHandler<GridPatrolItem>(BR.handler, R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, GridPatrolItem data) {
            if (tag == 0){
                Intent intent = new Intent(context, SpecialPatrolDetailsActivity.class);
                startActivity(intent.putExtra("item", data));
            }
        }
    };
}
