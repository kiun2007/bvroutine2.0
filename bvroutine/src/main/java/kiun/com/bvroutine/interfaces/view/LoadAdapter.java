package kiun.com.bvroutine.interfaces.view;

import android.view.View;

import java.util.List;

public interface LoadAdapter<T> {

    /**
     * 添加数据到尾部.
     * @param list 新增的数据.
     */
    void add(List<T> list);

    /**
     * 清除所有数据.
     */
    void clear();

    /**
     * 获取所有数据.
     * @return 获取数据列表.
     */
    List<T> getAll();

    /**
     * 通知数据更新.
     */
    void notifySet();

    /**
     * 发生错误.
     */
    void error(String err);

    /**
     * 添加底部.
     * @param footerView
     */
    void addFooterView(View footerView);

    /**
     * 移除底部.
     */
    void removeFooter();
}
