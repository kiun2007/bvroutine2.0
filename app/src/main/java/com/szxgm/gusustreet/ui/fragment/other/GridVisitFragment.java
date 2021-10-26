package com.szxgm.gusustreet.ui.fragment.other;

import android.content.Context;
import android.content.Intent;

import com.szxgm.gusustreet.BR;
import com.szxgm.gusustreet.R;
import com.szxgm.gusustreet.databinding.FragmentGridVisitBinding;
import com.szxgm.gusustreet.model.other.GridPatrolItem;
import com.szxgm.gusustreet.model.other.GridVisitItem;
import com.szxgm.gusustreet.net.requests.linkage.PatrolListReq;
import com.szxgm.gusustreet.net.requests.linkage.VisitListReq;
import com.szxgm.gusustreet.ui.activity.other.SpecialPatrolDetailsActivity;
import com.szxgm.gusustreet.ui.activity.other.SpecialVisitDetailsActivity;

import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.handlers.ListHandler;

public class GridVisitFragment extends BVBaseFragment<FragmentGridVisitBinding> {

    @Override
    public int getViewId() {
        return R.layout.fragment_grid_visit;
    }

    @Override
    public void initView() {
        mViewBinding.setHandler(listHandler);
        mViewBinding.setReq(new VisitListReq());
    }

    ListHandler<GridVisitItem> listHandler = new ListHandler<GridVisitItem>(BR.handler, R.layout.list_error_normal){
        @Override
        public void onClick(Context context, int tag, GridVisitItem data) {
            if (tag == 0){
                Intent intent = new Intent(context, SpecialVisitDetailsActivity.class);
                startActivity(intent.putExtra("item", data));
            }
        }
    };
}
