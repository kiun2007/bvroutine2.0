package kiun.com.bvroutine.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.interfaces.callers.CompareCaller;
import kiun.com.bvroutine.interfaces.callers.GetCaller;
import kiun.com.bvroutine.interfaces.callers.GetObjectCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class ListUtil {

    public static<T> List<T>[] filters(List<T> list, boolean repeat, CompareCaller<T>... callers) {
        List<T> allItems = list;
        List<T>[] selectedItems = new List[callers.length];
        for (int i = 0; i < selectedItems.length; i++) {
            selectedItems[i] = new LinkedList<>();
        }

        for (T item : allItems) {
            for (int i = 0; i < callers.length; i++) {
                if (callers[i].call(item)){
                    selectedItems[i].add(item);
                    if (!repeat){
                        break;
                    }
                }
            }
        }
        return selectedItems;
    }

    public static<T> List<T> filter(List<T> list, CompareCaller<T> caller) {
        return filters(list, false, caller)[0];
    }

    public static<T> List<T> filter(List<T> list, boolean repeat, CompareCaller<T> caller) {
        return filters(list, repeat,caller)[0];
    }

    public static<IN,OUT> List<OUT> toList(List<IN> inList, GetCaller<IN, OUT> getCaller){
        List<OUT> outList = new LinkedList<>();

        for (IN item : inList){
            outList.add(getCaller.call(item));
        }
        return outList;
    }

    public static Map<String, String> keyValues(String ...arguments){

        if (arguments.length % 2 != 0){
            return null;
        }

        Map<String, String> maps = new HashMap<>();
        for (int i = 0; i < arguments.length; i+= 2) {
            maps.put(arguments[i], arguments[i+1]);
        }
        return maps;
    }

    public static<T> void map(List<T> list, SetCaller<T> caller){
        if (list == null) return;

        for (T item : list){
            caller.call(item);
        }
    }

    public static boolean isEmpty(List list){
        return list == null || list.isEmpty();
    }

    public static<T> T first(List<T> list){
        if (!isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    public static boolean contains(String value, String... array){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int value, int... array){

        for (int i = 0; i < array.length; i++) {
            if (array[i] == value){
                return true;
            }
        }
        return false;
    }

    public static String join(List list, String join){
        return join(list, join, Object::toString);
    }

    public static<Item> String join(List<Item> list, String join, GetCaller<Item, String> getCaller){

        if (list.isEmpty()){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (Item item : list){
            if (item != null){
                buffer.append(getCaller.call(item)).append(join);
            }
        }

        return buffer.length() == 0 ? buffer.toString() : buffer.substring(0, buffer.length() - 1);
    }

    public static<T,L extends List> L copyList(List src, Class<T> clz){
        return copyList(src, clz, null);
    }

    /**
     * 将一个列表内容复制另外一个不同类型的列表.
     * @param src 列表数据.
     * @param clz 新列表每一项的类型.
     * @param lsClz 装载数据的列表类型.
     * @return 全新的列表.
     */
    public static<T,L extends List> L copyList(List src, Class<T> clz, Class<L> lsClz){

        L newList = null;
        try {
            if (lsClz == null){
                lsClz = (Class<L>) LinkedList.class;
            }
            newList = lsClz.newInstance();
            for (Object item : src){
                T newItem = clz.newInstance();
                ObjectUtil.copyByMapping(newItem, item);
                newList.add(newItem);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return newList;
    }

    /**
     * 查找元素
     * @param src
     * @param caller
     * @param <T>
     * @return
     */
    public static <T> T find(List<T> src, CompareCaller<T> caller){
        return ListUtil.first(ListUtil.filter(src, caller));
    }

    /**
     * 复制列表.
     * @param src
     * @param <T>
     * @return
     */
    public static <T> List<T> clone(List<T> src){
        List<T> list = new LinkedList<>();
        list.addAll(src);
        return list;
    }
}
