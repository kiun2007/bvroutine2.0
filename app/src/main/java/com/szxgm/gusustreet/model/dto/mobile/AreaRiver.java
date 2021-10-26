package com.szxgm.gusustreet.model.dto.mobile;

import com.szxgm.gusustreet.model.base.GeneralItem;

/**
 * 区河长河段.
 */
public class AreaRiver implements GeneralItem {

    /**
     * 河段ID.
     */
    private String id;

    /**
     * 父级ID
     */
    private String pId;

    /**
     * 河段名称
     */
    private String name;

    /**
     * 显示标题.
     */
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
