package com.szxgm.gusustreet.model.dto;

import com.szxgm.gusustreet.model.query.DictReq;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.GetCaller;

public class Dict {

    private String id;

    private String key;

    private String dictLabel;

    private String dictValue;

    private Date createTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public static GetCaller<Dict, String> labelGet(){
        return Dict::getDictLabel;
    }

    public static GetCaller<Dict, String> valueGet(){
        return Dict::getDictValue;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public static DictReq create(String type){
        DictReq req = new DictReq();
        req.setDictType(type);
        return req;
    }

    public static Map<String, String> toMap(List<Dict> dicts){

        Map<String, String> maps = new LinkedHashMap<>();
        for (Dict item : dicts){
            maps.put(item.dictValue, item.dictLabel);
        }

        return maps;
    }
}