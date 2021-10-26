package kiun.com.bvroutine.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.ViewDataBinding;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.presenter.GridPresenter;
import kiun.com.bvroutine.utils.ViewBindingUtil;

public class BaseListAdapter<T> extends BaseAdapter implements GridPresenter<T> {

    protected List<T> listData = new LinkedList();
    Context context;
    int itemLayout;
    int itemBR;
    BaseHandler itemHandler;

    public BaseListAdapter(Context context, int itemLayout, int itemBR, BaseHandler itemHandler) {
        super();
        this.context = context;
        this.itemLayout = itemLayout;
        this.itemHandler = itemHandler;
        this.itemBR = itemBR;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public void setList(List<T> list) {
        listData = list;
        notifyDataSetChanged();
    }

    @Override
    public void add(T item) {
        listData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T item) {
        listData.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        listData.remove(index);
        notifyDataSetChanged();
    }

    public void clear(){
        listData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void notifySet() {
        notifyDataSetChanged();
    }

    @Override
    public void addList(List<T> items) {
        listData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public List<T> allList() {
        return listData;
    }

    @Override
    public void setOnChanged(CallBack callBack) {

    }

    @Override
    public T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BindingHolder holder;
        if (convertView == null){

            ViewDataBinding binding = ViewBindingUtil.inflate(LayoutInflater.from(context), itemLayout, null, false);
            holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            holder.itemView.setTag(holder);
        }else{
            holder = (BindingHolder) convertView.getTag();
        }
        holder.getBinding().setVariable(itemBR, getItem(position));
        holder.getBinding().setVariable(BR.index, new Indexer(position, listData.size()));
        holder.getBinding().setVariable(itemHandler.getBR(), itemHandler);
        return holder.itemView;
    }
}
