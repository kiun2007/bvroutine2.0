package kiun.com.bvroutine.interfaces.view;

import android.view.View;

public interface LoadStartAdapter<T,VIEW extends View> extends LoadAdapter<T>{

    /**
     * 初始化View
     * @param view
     */
    void start(VIEW view);
}
