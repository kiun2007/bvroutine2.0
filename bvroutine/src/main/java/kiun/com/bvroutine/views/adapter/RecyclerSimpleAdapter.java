package kiun.com.bvroutine.views.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public class RecyclerSimpleAdapter extends BaseRecyclerAdapter<Object, ListViewPresenter>{

    protected static final int FOOTER_VIEW = -1;
    private View footerView = null;

    public RecyclerSimpleAdapter(ListViewPresenter presenter, int layoutId, int errorLayout, int dataBr, ListHandler handler){
        super(presenter, layoutId, errorLayout, dataBr, handler);
    }

    @Override
    public void addFooterView(View footerView) {
        this.footerView = footerView;
        notifySet();
    }

    @Override
    public void removeFooter(){
        this.footerView = null;
    }

    @Override
    public List showList() {
        return listData;
    }

    @Override
    public int getItemCount() {
        if (isError || showList() == null || showList().size() == 0){
            return 1;
        }
        return showList().size() + (footerView == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        int baseType = super.getItemViewType(position);
        if (baseType < 0){
            return baseType;
        }
        if (position >= showList().size()){
            return FOOTER_VIEW;
        }
        return baseType;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType < 0){
            if (viewType == FOOTER_VIEW){
                return new BindingHolder(footerView);
            }
            return super.onCreateViewHolder(parent, viewType);
        }else{

            int layoutId = mLayoutId;
            if (viewType > 0){
                layoutId = viewType;
            }

            ViewDataBinding binding = ViewBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
            if(mHandler != null && mHandler.getBR() > 0){
                binding.setVariable(mHandler.getBR(), mHandler);
            }

            BindingHolder holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        if (holder != null){

            int viewType;
            if ((viewType = holder.getItemViewType()) < 0){
                if (viewType == FOOTER_VIEW){
                    return;
                }else{
                    super.onBindViewHolder(holder, position);
                }
            }else{

                holder.bind(mDataBr, listData.get(position));
                holder.getBinding().setVariable(BR.index, new Indexer(position, listData.size()));
                holder.getBinding().executePendingBindings();

                if(listViewPresenter != null && position >= listData.size() - 2){
                    if (footerView == null){
                        new Handler().post(()->listViewPresenter.loadMore());
                    }
                }
            }
        }
    }
}
