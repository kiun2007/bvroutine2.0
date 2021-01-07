package kiun.com.bvroutine.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public abstract class BaseRecyclerAdapter<T, L extends ListViewPresenter> extends RecyclerView.Adapter<BindingHolder> implements LoadAdapter<T> {

    protected static final int LOADING_VIEW = -100;
    protected static final int ERROR_VIEW = -101;
    protected static final int EMPTY_VIEW = -102;

    protected int errLayout;
    protected int mLayoutId;
    protected int mDataBr;
    protected boolean isError = false;
    protected boolean isEmpty = false;
    protected String errorContent = null;
    protected ListHandler mHandler;
    protected List<T> listData = new LinkedList();
    protected L listViewPresenter;

    public BaseRecyclerAdapter(L presenter, int layoutId, int errLayout, int dataBr, ListHandler handler) {
        mLayoutId = layoutId;
        mDataBr = dataBr;
        mHandler = handler;
        listViewPresenter = presenter;
        this.errLayout = errLayout;
    }

    public abstract List showList();
    public void addFooterView(View footerView) {}
    public void removeFooter(){}

    @Override
    public void add(List<T> list) {
        isError = false;
        if(list != null){
            isEmpty = list.size() == 0;
            listData.addAll(list);
            notifySet();
        }
    }

    @Override
    public void error(String err) {
        errorContent = err;
        isError = true;
        isEmpty = false;
        notifySet();
    }

    @Override
    public int getItemViewType(int position) {

        if (isError) return ERROR_VIEW;

        if(showList() == null || showList().size() == 0) {
            return isEmpty ? EMPTY_VIEW : LOADING_VIEW;
        }

        if (position < listData.size()){
            int layoutId = mHandler.getItemLayout(listData.get(position));
            if (mHandler != null && layoutId > 0){
                return layoutId;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (isError || showList() == null || showList().size() == 0){
            return 1;
        }
        return showList().size();
    }

    //父类只处理异常的信息.
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType < 0){
            assert errLayout != 0;
            binding = ViewBindingUtil.inflate(LayoutInflater.from(parent.getContext()), errLayout, parent, false);

            BindingHolder holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }
        return null;
    }

    //父类只处理异常的信息.
    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        int viewType = getItemViewType(position);

        if (viewType < 0) {
            if (mHandler != null && mHandler.getBR() > 0) {
                mHandler.setType(Math.abs(viewType) - 100);
                if (mHandler.isError()){
                    mHandler.error(errorContent);
                    errorContent = null;
                }
                holder.getBinding().setVariable(mHandler.getBR(), mHandler);
            }
        }
    }

    @Override
    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }

    @Override
    public List<T> getAll() {
        return listData;
    }

    @Override
    public void notifySet() {
        new Handler(Looper.getMainLooper()){
            @Override
            public void dispatchMessage(Message msg) {
                notifyDataSetChanged();
            }
        }.sendEmptyMessage(0);
    }
}
