package com.szxgm.gusustreet.model.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 工单系统通用获取接口枚举
 */
public enum OrderActivityType {

    /**
     * 12345工单
     */
    ottff("getOttffTodoList"),

    /**
     * 12345工单核查
     */
    ottffCheck("getOttffCheckList"),

    /**
     * 寒山闻钟
     */
    hswz("getHswzTodoList"),

    /**
     * 寒山闻钟核查
     */
    hswzCheck("gethswzCheckList"),

    /**
     * 街道平台
     */
    street("getToDoOrderList"),

    /**
     * 街道平台核查
     */
    streetCheck("checkOrderList"),

    /**
     * 网格工单
     */
    grid("gridOrderTodoList");

    private String activity;

    OrderActivityType(String activity) {
        this.activity = activity;
    }

    public final String getActivity() {
        return activity;
    }

    private static Map<String, OrderActivityType> orderTypeMap;

    public static OrderActivityType getType(String activity, boolean check){
        if (orderTypeMap == null){
            orderTypeMap = new HashMap<>();
            for (OrderActivityType type : OrderActivityType.values()) {
                orderTypeMap.put(type.activity, type);
            }
        }

        OrderActivityType type = orderTypeMap.get(activity);
        if (check){
            if (!type.name().endsWith("Check")){
                type = valueOf(type.name() + "Check");
            }
        }else{
            if (type.name().endsWith("Check")){
                type = valueOf(type.name().replace("Check", ""));
            }
        }
        return type;
    }

    public static OrderActivityType getType(String activity){
        return getType(activity, false);
    }

    public static boolean isOther(String activity){
        OrderActivityType type = getType(activity);
        if (type == ottff || type == ottffCheck || type == hswz || type == hswzCheck){
            return true;
        }
        return false;
    }
}
