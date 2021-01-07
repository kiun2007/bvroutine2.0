package kiun.com.bvroutine.interfaces.presenter;

import java.util.List;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.CompareCaller;
import kiun.com.bvroutine.interfaces.view.ListRequestView;

public interface ListViewPresenter<T,Q,Req extends ListRequestView>{

    Q initRequest(Q rootRequest, Req requestView);

    void start(ListHandler<T> hanlder, int itemLayout, int dataBr, RequestBindingPresenter p);

    void loadMore();

    void reload();

    void notifySet();

    int[] filterCount(CompareCaller<T>... callers);

    int[] filterCount(boolean repeat, CompareCaller<T>... callers);

    List<T> filter(CompareCaller<T> caller);

    List<T>[] filters(CompareCaller<T>... callers);

    List<T>[] filters(boolean repeat, CompareCaller<T>... callers);

    List<T> list();
}