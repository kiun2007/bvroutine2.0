package kiun.com.bvroutine.interfaces;

import java.io.IOException;
import java.util.List;

public interface TreeItem {

    /**
     * 子项使用的布局ID.
     * @return 布局ID.
     */
    int itemLayoutId();

    /**
     * 是否继续拥有子级别.
     * @param parentLevel
     * @return
     */
    boolean withChild(int parentLevel);

    List<?> itemList() throws Exception;
}
