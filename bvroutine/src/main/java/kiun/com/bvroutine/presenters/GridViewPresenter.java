package kiun.com.bvroutine.presenters;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.GridView;

import java.util.List;
import kiun.com.bvroutine.handlers.GridHandler;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.presenter.GridPresenter;
import kiun.com.bvroutine.interfaces.view.BaseView;
import kiun.com.bvroutine.views.adapter.BaseListAdapter;

public class GridViewPresenter<T> implements GridPresenter<T> {

    private BaseListAdapter baseListAdapter;
    private CallBack callBack;

    public GridViewPresenter(GridView gridView, BaseView view, int itemLayout, int itemBR, GridHandler<T> handler){
        gridView.setAdapter(baseListAdapter = new BaseListAdapter<T>(view.getContext(), itemLayout, itemBR, handler));
        handler.setPresenter(this);
    }

    @Override
    public void setList(List<T> list) {
        baseListAdapter.setList(list);
        if (callBack != null){
            callBack.call();
        }
    }

    @Override
    public void add(T item) {
        baseListAdapter.add(item);
        if (callBack != null){
            callBack.call();
        }
    }

    @Override
    public void remove(T item) {
        baseListAdapter.remove(item);
        if (callBack != null){
            callBack.call();
        }
    }

    @Override
    public void removeAt(int index) {
        baseListAdapter.removeAt(index);
        if (callBack != null){
            callBack.call();
        }
    }

    @Override
    public void clear() {
        baseListAdapter.clear();
    }

    @Override
    public void notifySet() {
        new Handler(Looper.getMainLooper()){
            @Override
            public void dispatchMessage(Message msg) {
                baseListAdapter.notifyDataSetChanged();
            }
        }.sendEmptyMessage(0);
    }

    @Override
    public void addList(List<T> items) {
        baseListAdapter.addList(items);
        if (callBack != null){
            callBack.call();
        }
    }

    @Override
    public List allList() {
        return baseListAdapter.allList();
    }

    @Override
    public void setOnChanged(CallBack callBack) {
        this.callBack = callBack;
    }
}
