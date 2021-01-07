package kiun.com.bvroutine.views.text.tags;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.utils.ObjectUtil;

public class TagFactory {

    private static Map<String, Class<? extends TagBase>> tagBaseClzMap = new HashMap<>();
    private static Map<String, TagBase> tagBaseMap = new HashMap<>();
    static {
        tagBaseClzMap.put("f", FTag.class);
        tagBaseClzMap.put("span", SpanTag.class);
    }

    public static TagBase create(String tag){

        if (tagBaseMap.containsKey(tag)){
            return tagBaseMap.get(tag);
        }

        Class<? extends TagBase> tagBaseClz = tagBaseClzMap.get(tag.toLowerCase());
        if (tagBaseClz == null){
            return null;
        }
        tagBaseMap.put(tag, ObjectUtil.newObject(tagBaseClz));
        return tagBaseMap.get(tag);
    }
}
