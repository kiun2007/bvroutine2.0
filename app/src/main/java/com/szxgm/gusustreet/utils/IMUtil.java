package com.szxgm.gusustreet.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.utils.JSONUtil;

public class IMUtil {

    public static List<String> getListByJSON(String json){

        List<String> strings = new ArrayList<>();
        if (!TextUtils.isEmpty(json)){
            if (!json.startsWith("[")){
                strings.add(json);
            }else{
                return JSONUtil.toStringList(json);
            }
        }
        return strings;
    }

    public static String getIMString(boolean isMobile, String imString){
        List<String> imStrings = getListByJSON(imString);
        if (imStrings.isEmpty() || (imStrings.size() == 1 && !isMobile)){
            return null;
        }

        imString = imStrings.get(isMobile ? 0 : 1);
        if (imString.isEmpty()){
            return null;
        }
        return imString;
    }
}
