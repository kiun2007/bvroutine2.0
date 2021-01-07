package com.szxgm.gusustreet.base;

public class LocalUser {

    private static String userId;
    public static String getUserId(){

        return "1";
//        if (userId != null){
//            return userId;
//        }
//        return userId = SharedUtil.getValue(User.TAG, new User()).getUserId().toString();
    }
}
