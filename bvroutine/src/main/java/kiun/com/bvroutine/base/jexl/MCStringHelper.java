package kiun.com.bvroutine.base.jexl;
import android.content.Context;

import androidx.annotation.NonNull;
import java.util.Date;
import java.util.List;

import kiun.com.bvroutine.utils.MCString;

public class MCStringHelper {

    public Date dateByFormat(String value, String format){
        return MCString.dateByFormat(value, format);
    }

    public String[] patternValues(String[] patternStrs, String rootStr) {
        return MCString.patternValues(patternStrs, rootStr);
    }

    public String[] patternValues(String pattern, String rootStr){
        return MCString.patternValues(pattern, rootStr);
    }

    public Object[] asArray(Object... objects){
        return objects;
    }

    public String[] asArray(String... strings){
        return strings;
    }

    public int asInt(String str) {
        return MCString.asInt(str);
    }

    public String[] stringsSort(String[] inStrings) {
        return MCString.stringsSort(inStrings);
    }

    public String formatDate(String format, Date date, String def) {
        return MCString.formatDate(format, date, def);
    }

    public String formatDate(String format, Date date) {
        return formatDate(format, date, "无");
    }

    public Number toNumber(String str) {
        return MCString.toNumber(str);
    }

    public String arrayToString(List<String> array, String separate) {
        return MCString.arrayToString(array, separate);
    }

    public String trim(String source, char trimValue) {
        return MCString.trim(source, trimValue);
    }

    public static<T> T item(Object index, int start, T... items){
        return MCString.item(index, start, items);
    }

    /**
     * 判断数组是否全部等于某一个值, 跳过空值.
     * @param value 对比的值.
     * @param items 输入的数组.
     * @return 是否全部相等.
     */
    public boolean isAllEq(@NonNull String value, String... items){
        return MCString.isAllEq(value, items);
    }

    public String itemOfStringArray(Object index, int start, Context context, int arrayId){
        String[] arrayStrings = context.getResources().getStringArray(arrayId);
        return item(index, start, arrayStrings);
    }

    public int itemInt(Object index, int start, Integer... items){
        return MCString.itemInt(index, start, items);
    }

    public boolean isWith(String value, String... array){
        return MCString.isWith(value, array);
    }

    public String toUnderlineName(String s, boolean isUpperCase){
        return MCString.toUnderlineName(s, isUpperCase);
    }

    public String toCamelCase(String s) {
        return MCString.toCamelCase(s);
    }

    public String toCapitalizeCamelCase(String s) {
        return MCString.toCapitalizeCamelCase(s);
    }

    public String getFieldName(String methodName){
        return MCString.getFieldName(methodName);
    }

    public float getSimilarityRatio(String str, String target) {
        return MCString.getSimilarityRatio(str, target);
    }
}
