package kiun.com.bvroutine.presenters.list;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;

public class ArrayListProvider extends ListProvider{

    private static int itemBr = BR.item;

    private int layoutId;

    private ListHandler listHandler;

    private List list;

    public ArrayListProvider(RequestBVActivity context, int layoutId, ListHandler listHandler, int arrayId) {
        this(context, layoutId, listHandler, Arrays.asList(context.getResources().getStringArray(arrayId)));
    }

    public ArrayListProvider(RequestBVActivity context, int layoutId, ListHandler listHandler, List list){
        super(context);
        this.layoutId = layoutId;
        if (listHandler == null){
            listHandler = new ListHandler(BR.handler, 0);
        }
        this.listHandler = listHandler;
        this.list = list;
    }

    @Override
    public ListHandler getHandler() {
        return listHandler;
    }

    @Override
    public int getDataBind() {
        return itemBr;
    }

    @Override
    public int getItemLayoutId() {
        return layoutId;
    }

    @Override
    public void refresh() {
    }

    @Override
    public List requestPager(RequestBindingPresenter p, Object bean) throws Exception {
        return list;
    }

    public static ArrayListProvider create(Context context, ListHandler listHandler, int layoutId, int arrayId){
        if (context instanceof RequestBVActivity){
            return new ArrayListProvider((RequestBVActivity) context, layoutId, listHandler, arrayId);
        }
        return null;
    }

    public static ArrayListProvider create(Context context, ListHandler listHandler, int layoutId, List list){
        if (context instanceof RequestBVActivity){
            return new ArrayListProvider((RequestBVActivity) context, layoutId, listHandler, list);
        }
        return null;
    }

}
