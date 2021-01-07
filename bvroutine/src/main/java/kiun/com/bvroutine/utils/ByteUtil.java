package kiun.com.bvroutine.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
     * 短整型转化为字节数组.
     * @param s
     * @return
     */
    public static byte[] shortToBytes(short s) {
        byte[] targets = new byte[2];
        targets[0] = (byte) (s >> 8 & 0xFF);
        targets[1] = (byte) (s & 0xFF);
        return targets;
    }

    public static byte[] compress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {

        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }


    /**
     * 字节数组转化为短整型。
     * @param bytes
     * @param offset
     * @return
     */
    public static short bytesToShort(byte[] bytes, int offset) {
        int high = bytes[offset];
        int low = bytes[offset + 1];
        return (short) ((high << 8 & 0xFF00) | (low & 0xFF));
    }

    /**
     * double类型转字节数组.
     * @param d
     * @return
     */
    public static byte[] doubleToBytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    /**
     * 字节数组转double类型.
     * @param arr
     * @param offset
     * @return
     */
    public static double bytesToDouble(byte[] arr, int offset) {
        long value = 0;
        for (int i = offset; i < offset + 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
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