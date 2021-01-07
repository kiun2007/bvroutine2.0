package com.szxgm.gusustreet.model.base;

import com.szxgm.gusustreet.model.dto.Dict;
import com.szxgm.gusustreet.net.services.SysService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.RetrofitUtil;

public class DictStatic {

    public static Map<String, String> LeaveType = null;

    public static Map<String, String> ApplyType = null;

    public static Map<String, String> SeverityType = null;

    public static Map<String, String> EventType = null;

    public static Map<String, String> mapFromKey(String key) throws Exception{

        List<Dict> types = RetrofitUtil.callServiceData(SysService.class, s -> s.getAppDict(key));
        Map<String, String> map = new HashMap<>();
        ListUtil.map(types, item->map.put(item.getDictValue(),item.getDictLabel()));

        return map;
    }
}
