package com.szxgm.gusustreet.model.dto;

import com.szxgm.gusustreet.model.base.GeneralItem;

public class ClockTimeTree implements GeneralItem {

    private String id;

    private String pId;

    private String name;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
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
