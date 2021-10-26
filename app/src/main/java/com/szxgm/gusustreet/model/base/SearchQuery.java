package com.szxgm.gusustreet.model.base;

public interface SearchQuery {

    /**
     * 填充搜索值
     * @param value
     */
    void setSearchValue(String value);

    /**
     * 获取搜索值
     * @return
     */
    String getSearchValue();

    /**
     * 搜索提示语
     * @return
     */
    String searchHint();

    /**
     * 过滤布局
     * @return
     */
    int filterLayout();

    /**
     * 过滤参数是否为空.
     * @return
     */
    boolean isEmpty();

    void onChanged();
}
