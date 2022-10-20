package com.szxgm.gusustreet.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {

    public static String getSHA(String info) {
        byte[] bytesSHA = null;
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

            messageDigest.update(info.getBytes());

            bytesSHA = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String strSHA = byteToHex(bytesSHA);
        return strSHA;
    }


    private static String byteToHex(byte[] bytes) {
        String hs = "";
        String temp;
        for (byte b : bytes) {
            temp = (Integer.toHexString(b & 0XFF));
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
        }
        return hs;
    }
}
