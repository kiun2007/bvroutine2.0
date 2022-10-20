package kiun.com.bvroutine.interfaces.presenter;

import java.util.List;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.ServiceCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;
import retrofit2.Call;

/**
 * 请求与绑定关系事务
 */
public interface RequestBindingPresenter {

    void addRequest(GetTNoParamCall<Void> serviceCaller);

    <T>void addRequest(GetTNoParamCall<T> serviceCaller, SetCaller<T> setCaller);

    <T>void addRequest(GetTNoParamCall<T> serviceCaller, SetCaller<T> setCaller, SetCaller<Exception> errCaller);

    boolean isConcurrent();

    <T,E>E callServiceData(Class<T> serviceClass, ServiceCaller<T> callback) throws Exception;

    <T,E>List<E> callServiceList(Class<T> serviceClass, ServiceCaller<T> callback, PagerBean pager) throws Exception;

    DataWrap execute(Call<?> call) throws Exception;

    ListWrap executeList(Call<?> call) throws Exception;
}
