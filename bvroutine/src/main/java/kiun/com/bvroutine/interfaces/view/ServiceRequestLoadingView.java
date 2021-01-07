package kiun.com.bvroutine.interfaces.view;

import kiun.com.bvroutine.interfaces.BeginLoading;
import kiun.com.bvroutine.presenters.loader.GetAsyncLoader;

public interface ServiceRequestLoadingView extends ServiceRequestView{

    /**
     * 开始进行网络请求, 但并不是所有网络请求都会进行此动作
     * 必须在函数定义中加入 {@link BeginLoading} 注解.
     * 此函数并不是线程安全的.
     * @param layoutId
     * @param title
     * @param loader
     */
    void startLoading(Integer layoutId, String title, GetAsyncLoader loader);

    /**
     * 成功完成加载事件.
     * @param loader
     */
    void loadComplete(GetAsyncLoader loader);

    /**
     * 当加载发生错误时.
     * @param loader
     * @param e
     */
    void loadError(GetAsyncLoader loader, Exception e);
}
