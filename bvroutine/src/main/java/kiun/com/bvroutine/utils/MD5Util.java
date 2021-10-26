package kiun.com.bvroutine.utils;

import java.nio.charset.StandardCharsets;
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
        return MD5(s.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] MD5(byte[] buff){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(buff);
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}