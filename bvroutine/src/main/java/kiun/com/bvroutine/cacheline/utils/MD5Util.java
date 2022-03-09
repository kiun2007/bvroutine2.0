package kiun.com.bvroutine.cacheline.utils;

import java.security.MessageDigest;

/**
 * Created by kiun_2007 on 2018/3/8.
 * MD5工具类.
 */

public class MD5Util {

    /**
     * 生成一个字符串的MD5.
     * @param s
     * @return
     */
    public static byte[] MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}