package kiun.com.bvroutine.cacheline.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by kiun_2007 on 2018/3/8.
 * 字节帮助类.
 */

public class ByteUtil {

    /**
     * 字节转化为HEX字符串(使用公钥加密).
     * @param src 需要转化的字节数组.
     * @return 加密后的字节HEX字符串.
     */
    public static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder(src.length * 3);
        stringBuilder.append("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toUpperCase());
        }
        return stringBuilder.toString();
    }

    /**
     * 十六进制字符串转化为byte字节数组(私钥解密).
     * @param hexStr 十六进制字符串.
     * @return 字节数组.
     */
    public static byte[] hexToBytes(String hexStr){

        if(hexStr == null || hexStr.length() % 2 != 0){
            return null;
        }

        int byteLength = hexStr.length() / 2;
        byte bytes[] = new byte[byteLength];

        for(int i = 0; i < byteLength; i++){
            String hex = hexStr.substring(i * 2, (i + 1) * 2);
            bytes[i] = (byte) Integer.parseInt(hex, 16);
        }

        return bytes;
    }

    /**
     * 往一个字节数组均匀的插入一些字节.
     * @param bytes 原始字节数组.
     * @param inBytes 要插入的内容.
     * @return 返回一个全新的字节数组.
     */
    public static byte[] insertToBytes(byte[] bytes, byte[] inBytes){

        if(bytes == null || inBytes == null || bytes.length < inBytes.length * 2){
            return null;
        }

        int stepNum = bytes.length / inBytes.length;
        int nowStep = 0;
        byte[] newBytes = new byte[bytes.length + inBytes.length];
        for(int i = 0, si = 0, ini = 0; i < newBytes.length; i++){

            if(nowStep == stepNum){
                newBytes[i] = inBytes[ini ++];
                nowStep = 0;
            }else{
                newBytes[i] = bytes[si ++];
                nowStep ++;
            }
        }
        return newBytes;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] datas = baos.toByteArray();
        return datas;
    }
}