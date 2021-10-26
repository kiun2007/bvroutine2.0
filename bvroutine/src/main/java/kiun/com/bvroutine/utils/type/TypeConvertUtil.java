package kiun.com.bvroutine.utils.type;

import com.alibaba.fastjson.JSON;

public class TypeConvertUtil {

    public static<T> T convert(Object source, Class<T> destClass){
        if (source == null){
            return null;
        }

        String json = JSON.toJSONString(source);
        return JSON.parseObject(json, destClass);
    }
}
