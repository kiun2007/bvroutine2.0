package kiun.com.bvroutine.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.interfaces.BeginLoading;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.ServiceCaller;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.interfaces.wrap.ListWrap;
import kiun.com.bvroutine.interfaces.wrap.WrapBase;
import kiun.com.bvroutine.net.AesRequestBody;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.presenters.loader.GetAsyncLoader;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Invocation;
import retrofit2.Response;

public class RetrofitUtil {

    private static void dataIsSuccess(WrapBase wrap, Class serviceClz) throws Exception{

        if (!wrap.isSuccess()){
            if(wrap.isLogout()){
                if (serviceClz == null){
                    ServiceGenerator.clearAuthorize();
                }else{
                    ServiceGenerator.clearAuthorize(serviceClz);
                }
            }
            throw new Exception(wrap.getMsg());
        }
    }

    private static void triggerLoading(Call call){
        Invocation invocation = call.request().tag(Invocation.class);
        if (invocation != null){
            BeginLoading beginLoading = invocation.method().getAnnotation(BeginLoading.class);
            if (beginLoading != null){
                int layoutId = R.layout.layout_loading_page;
                if (beginLoading.value() != -1){
                    layoutId = beginLoading.value();
                }

                GetAsyncLoader.startLoading(layoutId, beginLoading.title());
            }
        }
    }

    public static <T, E> E callServiceData(Class<T> serviceClass, ServiceCaller<T> callback, GetCaller<Class<T>,T> serverGetCaller) throws Exception {

        T services = serverGetCaller.call(serviceClass);

        Call<E> call = callback.requestCall(services);
        triggerLoading(call);

        Response<E> response = call.execute();
        E warp = response.body();

        if(warp instanceof DataWrap){
            DataWrap dataWarp = (DataWrap) warp;
            dataIsSuccess(dataWarp, serviceClass);
            return (E) dataWarp.getData();
        }
        return warp;
    }

    /**
     * 拆包列表数据
     * @param pagerBean 分页元数据.
     * @param response 请求响应类.
     * @return 列表数据.
     * @throws Exception 数据发生错误异常.
     */
    public static<T> List<T> unpackWrap(PagerBean pagerBean, Response<?> response) throws Exception {

        Object wrap = response.body();
        if(wrap instanceof ListWrap){
            ListWrap<T> listWrap = (ListWrap<T>) wrap;
            dataIsSuccess(listWrap, null);
            if(pagerBean != null){
                pagerBean.setPages(listWrap.pages());
                pagerBean.setTotal(listWrap.total());
            }

            RequestBody requestBody = response.raw().request().body();
            String itemClzName = response.raw().request().header("itemClz");
            Class<T> itemClz = null;

            if (itemClzName != null){
                itemClz = (Class<T>) Class.forName(itemClzName);
            }

            if (itemClz == null){
                if (requestBody instanceof AesRequestBody){
                    AesRequestBody aesRequestBody = (AesRequestBody) requestBody;
                    itemClz = aesRequestBody.getItemClz();
                }
            }

            if (itemClz != null){
                Class<T> finalItemClz = itemClz;
                return ListUtil.toList(listWrap.wrapList(), item->{
                    if (item instanceof JSONObject){
                        return ((JSONObject) item).toJavaObject(finalItemClz);
                    }
                    return item;
                });
            }
            return listWrap.wrapList();
        }else if (wrap instanceof DataWrap){
            DataWrap<List<?>> dataWrap = (DataWrap<List<?>>) wrap;
            dataIsSuccess(dataWrap, null);
            return (List<T>) dataWrap.getData();
        }
        return null;
    }

    public static<T, E> List<E> callServiceList(Class<T> serviceClass, ServiceCaller<T> callback, PagerBean pagerBean, GetCaller<Class<T>,T> serverGetCaller) throws Exception {

        T services = serverGetCaller.call(serviceClass);
        Call<?> call = callback.requestCall(services);
        triggerLoading(call);

        return (List<E>) unpackWrap(pagerBean, call.execute());
    }

    public static <T, E> E callServiceData(Class<T> serviceClass, ServiceCaller<T> callback) throws Exception {
        return callServiceData(serviceClass, callback, ServiceGenerator::createService);
    }

    public static  <T, E> List<E> callServiceList(Class<T> serviceClass, ServiceCaller<T> callback) throws Exception{
        return callServiceList(serviceClass, callback, null, ServiceGenerator::createService);
    }
}
