package com.szxgm.gusustreet.utils;

import java.util.Date;

import kiun.com.bvroutine.utils.MCString;

public class OrderUtil {

    public static String dateColor(Date date){

        if (date != null){
            double days = (double) (new Date().getTime() - date.getTime()) / (24 * 3600 * 1000);
            if (days <= 0){
                return "#FF0000";
            }

            if (days > 0 && days < 2){
                return "#FF6600";
            }
        }

        return "#7F7F7F";
    }

    public static String[] dateToSingle(String title, Date date){

        return MCString.asArray(title,
                MCString.formatDate("yyyy-MM-dd HH:mm", date, "--"),
                dateColor(date));
    }
}
