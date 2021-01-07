package kiun.com.bvroutine.interfaces.wrap;

import java.util.List;

public interface ListWrap<T> extends WrapBase {

    /**
     * 列表数据.
     * @return
     */
    List<T> wrapList();

    /**
     * 获取总页码数.
     * @return 总共有多少页.
     */
    int pages();

    /**
     * 获取一共有多少条数据.
     * @return 总共有多少条数据.
     */
    int total();
}