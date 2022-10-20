package kiun.com.bvroutine.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kiun.com.bvroutine.text.Html;
import kiun.com.bvroutine.views.text.HtmlTag;

/**
 * Created by kiun_2007 on 2018/8/9.
 */

public class MCString {

    public static Date dateByFormat(String value, String format){
        if (value == null) return null;

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
        }
        return null;
    }

    public static String[] patternValues(String[] patternStrs, String rootStr) {

        String[] values = new String[patternStrs.length];
        for (int i = 0; i < patternStrs.length; i++) {
            Matcher matcher = Pattern.compile(patternStrs[i]).matcher(rootStr);
            values[i] = matcher.find() ? matcher.group(1) : null;
        }
        return values;
    }

    public static String[] patternValues(String pattern, String rootStr){

        Matcher matcher = Pattern.compile(pattern).matcher(rootStr);
        List<String> strings = new ArrayList<>();

        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                strings.add(matcher.group(i+1));
            }
        }
        String[] newStrings = new String[strings.size()];
        strings.toArray(newStrings);
        return newStrings;
    }

    public static Integer[] asByInt(Integer ... ints){
        return ints;
    }

    public static Object[] asArray(Object... objects){
        return objects;
    }

    public static String[] asArray(String... strings){
        return strings;
    }

    public static int asInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int hex = str.startsWith("0x") ? 16 : 10;
        return Integer.parseInt(str.replace("0x", ""), hex);
    }

    public static String[] stringsSort(String[] inStrings) {

        ArrayList<String> newStrings = new ArrayList<String>();
        for (String itemStr : inStrings) {
            if (itemStr.equals("")) {
                continue;
            }
            boolean isWith = false;
            for (int i = 0; i < newStrings.size(); i++) {
                if (itemStr.equals(newStrings.get(i))) {
                    isWith = true;
                    break;
                }
            }
            if (!isWith) {
                newStrings.add(itemStr);
            }
        }
        String[] outString = new String[newStrings.size()];
        for (int i = 0; i < newStrings.size(); i++) {
            outString[i] = newStrings.get(i);
        }
        return outString;
    }

    public static String formatDate(String format, Date date, String def) {
        if (date == null){
            return def;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    public static String formatDate(String format, Date date) {
        return formatDate(format, date, "无");
    }

    @SuppressLint("DefaultLocale")
    public static String byteFormat(Long byteCount){

        int GB = 1024 * 1024 * 1024;
        int MB = 1024 * 1024;
        int KB = 1024;
        if (byteCount == null){
            return "";
        }

        if (byteCount >= GB){
            return String.format("%.2fGB", (double)byteCount/GB);
        }else if (byteCount >= MB){
            return String.format("%.2fMB", (double)byteCount/MB);
        }else if (byteCount >= KB){
            return String.format("%.2fKB", (double)byteCount/KB);
        }
        return String.format("%dB", byteCount);
    }

    public static String maskPhone(String phone){
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static Number toNumber(String str) {

        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try{
            if (str.indexOf(".") > -1) {
                return Float.parseFloat(str);
            } else {
                return Integer.parseInt(str);
            }
        }catch (Exception ex){
        }
        return 0;
    }

    public static String arrayToString(List<String> array, String separate) {

        StringBuilder stringBuilder = new StringBuilder(2000);
        for (int i = 0; i < array.size(); i++) {
            stringBuilder.append(array.get(i));
            if (i < array.size() - 1) {
                stringBuilder.append(separate);
            }
        }
        return stringBuilder.toString();
    }

    public static String trim(String source, char trimValue) {
        int len = source.length();
        int st = 0;
        while ((st < len) && (source.charAt(len - 1) <= trimValue)) {
            len--;
        }
        return ((st > 0) || (len < source.length())) ? source.substring(st, len) : source;
    }

    public static String dateConvert(String value, String inFormat, String outFormat){
        SimpleDateFormat inDateFormat = new SimpleDateFormat(inFormat);
        SimpleDateFormat outDateFormat = new SimpleDateFormat(outFormat, Locale.CHINA);
        try {
            Date date = inDateFormat.parse(value);
            return outDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    public static String[] decimalToArray(Number value, int num){

        if (value == null){
            return null;
        }

        String formatValue = String.format("%%.%df", num);
        String decimalStr = String.format(formatValue, value.doubleValue());
        String[] numbers = decimalStr.split("\\.");
        String intValue = numbers[0];
        String decimalValue = numbers[numbers.length - 1];
        decimalValue = trim(decimalValue, '0');
        if (!decimalValue.isEmpty()){
            decimalValue = "." + decimalValue;
        }
        return new String[]{intValue, decimalValue};
    }

    @SuppressLint("DefaultLocale")
    public static CharSequence decimalFormat(Number value, int num){

        String[] strings = decimalToArray(value, num);
        if (strings == null){
            return Html.fromHtml("<f color='#999999'>无<f>");
        }

        String html;
        if (TextUtils.isEmpty(strings[1])){
            html = String.format("<f c=\"#333333\" s=\"15sp\">%s</f>", strings[0]);
        }else{
            html = String.format("<f c=\"#333333\" s=\"15sp\">%s</f><f c=\"#7F7F7F\" s=\"11sp\">%s</f>", strings[0], strings[1]);
        }
        return Html.fromHtml(html, null, new HtmlTag());
    }

    /**
     * 是否全都不为空
     * @param strings
     * @return
     */
    public static boolean isNonEmpty(String... strings){
        for (int i = 0; strings != null && i < strings.length; i++) {
            if (TextUtils.isEmpty(strings[i])){
                return false;
            }
        }
        return true;
    }

    public static<T> T item(Object index, int start, T... items){

        int idx = 0;
        if (index != null){
            if (index instanceof String){
                idx = asInt((String) index);
            }

            if (index instanceof Integer){
                idx = (int) index;
            }
            idx -= start;
        }

        if (idx < 0 || idx >= items.length){
            return null;
        }
        return items[idx];
    }

    /**
     * 判断数组是否全部等于某一个值, 跳过空值.
     * @param value 对比的值.
     * @param items 输入的数组.
     * @return 是否全部相等.
     */
    public static boolean isAllEq(@NonNull String value, String... items){

        for (String item : items) {
            if (!TextUtils.isEmpty(item) && !value.equals(item)){
                return false;
            }
        }
        return true;
    }

    public static String itemOfStringArray(Object index, int start, Context context, int arrayId){
        String[] arrayStrings = context.getResources().getStringArray(arrayId);
        return item(index, start, arrayStrings);
    }

    public static int itemInt(Object index, int start, Integer... items){
        Integer integer = item(index, start, items);

        if (integer == null){
            return 0;
        }
        return integer;
    }

    public static boolean isWith(String value, String[] array){
        if(array == null) return false;
        for (String item : array) {
            if (item.equals(value)) return true;
        }
        return false;
    }

    public static boolean isWith(Object value, Object... array){
        if(array == null) return false;
        for (Object item : array) {
            if (value.equals(item)) return true;
        }
        return false;
    }

    public static String randUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String randNum(int numCount){
        StringBuffer stringBuffer = new StringBuffer(numCount+5);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (int i = 0; i < numCount; i++) {
            stringBuffer.append(String.format("%d", (Math.abs(random.nextInt())%10)));
        }
        return stringBuffer.toString();
    }

    private static final char SEPARATOR = '_';

    public static String toUnderlineName(String s, boolean isUpperCase){
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(isUpperCase ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String getFieldName(String methodName){
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }

    public static float getSimilarityRatio(String str, String target) {

        if (str == null || target == null) {
            return 0;
        }

        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }

        return (1 - (float) d[n][m] / Math.max(str.length(), target.length())) * 100F;
    }
}
