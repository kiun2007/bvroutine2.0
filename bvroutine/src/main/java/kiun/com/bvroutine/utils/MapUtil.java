package kiun.com.bvroutine.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.MapGet;

public class MapUtil {

    public static<K,V> List toList(Map<K,V> maps, MapGet<K,V> mapGetCaller){
        List list = new LinkedList();
        for (K key : maps.keySet()){
            list.add(mapGetCaller.get(key, maps.get(key)));
        }
        return list;
    }

    public static Map<String, Object> as(Object ...array){

        Map<String, Object> newMap = new HashMap<>();
        for (int i = 0; i < array.length; i += 2) {
            if (array[i] instanceof String && array.length > i){
                newMap.put((String) array[i], array[i+1]);
            }
        }
        return newMap;
    }
}
