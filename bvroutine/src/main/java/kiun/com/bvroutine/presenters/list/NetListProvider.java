package kiun.com.bvroutine.presenters.list;

import android.content.Context;

import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.PagerCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.utils.RetrofitUtil;
import retrofit2.Call;

public class NetListProvider extends ListProvider<PagerBean>{

    private static int itemBr = BR.item;

    private int layoutId;

    private PagerCaller caller;

    private ListHandler<?> listHandler;

    protected NetListProvider(RequestBVActivity<?> context, int layoutId, ListHandler<?> listHandler, int errorLayoutId) {
        super(context);
        this.layoutId = layoutId;
        if (listHandler == null){
            listHandler = new ListHandler<>(BR.handler, errorLayoutId);
        }
        this.listHandler = listHandler;
        listHandler.setTag(this);
    }

    @Override
    public ListHandler<?> getHandler() {
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
        if (presenter != null){
            presenter.reload();
        }
    }

    /**
     * 设置网络请求接口.
     * @param caller
     * @return
     */
    public NetListProvider setCaller(PagerCaller caller) {
        this.caller = caller;
        return this;
    }

    @Override
    public List<?> requestPager(RequestBindingPresenter p, PagerBean bean) throws Exception {
        if (caller != null) {
            Call<?> call = caller.get(bean);
            if (call != null) {
                return RetrofitUtil.unpackWrap(bean, call.execute());
            }
        }
        return null;
    }

    /**
     * 创建常规的网络请求列表.
     * @param context 上下文.
     * @param listHandler 列表响应.
     * @param layoutId 列表每一项布局ID.
     * @return 列表驱动
     */
    public static NetListProvider create(Context context, ListHandler<?> listHandler, int layoutId){

        if (context instanceof RequestBVActivity){
            return new NetListProvider((RequestBVActivity<?>) context, layoutId, listHandler, 0);
        }
        return null;
    }

    public static NetListProvider create(Context context, int layoutId, int errorLayoutId){

        if (context instanceof RequestBVActivity){
            return new NetListProvider((RequestBVActivity<?>) context, layoutId, null, errorLayoutId);
        }
        return null;
    }
}
