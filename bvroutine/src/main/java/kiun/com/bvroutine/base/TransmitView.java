package kiun.com.bvroutine.base;

import android.view.View;

/**
 * 事件传递View
 */
public interface TransmitView {

    /**
     * 代理类型
     * @return
     */
    default TransmitAgentType agentType() {
        return TransmitAgentType.ALL;
    }

    /**
     * 抓取错误
     * @param message
     */
    default void catchError(String message){
    }

    /**
     * 获取成功
     * @param value
     */
    default void onSuccess(Object value){
    }

    /**
     * 实际使用事件
     * @return
     */
    View child();
}
