package kiun.com.bvroutine.cacheline.data;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.utils.ListUtil;

public class WhereBuilder {

    private WhereBuilder(){

    }

    private List<String> whereList = new LinkedList<>();

    public static WhereBuilder create(){
        return new WhereBuilder();
    }

    public WhereBuilder addParam(String key, Object value){
        if (value instanceof String){
            whereList.add(String.format("%s = '%s'", key, value));
        }else if (value instanceof List){
            List list = (List) value;
            List<String> inList = ListUtil.toList(list, item-> String.format("'%s'", item.toString()));
            String inValue = ListUtil.join(inList, ",");
            whereList.add(String.format("%s IN (%s)", key, inValue));
        }
        return this;
    }

    public String build(String split){
        if (whereList.isEmpty()) return "";
        return ListUtil.join(whereList, split);
    }
}
