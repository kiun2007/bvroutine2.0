package kiun.com.bvroutine.base.jexl;
import org.apache.commons.jexl2.MapContext;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ObjectUtil;

/**
 * Jexl运行时上下文.
 */
public class RuntimeContext{

    private static RuntimeContext runtimeContext = new RuntimeContext();
    private Map<String, Object> runtimeContexts = new HashMap<>();

    private RuntimeContext(){
        runtimeContexts.put("objectTell", new ObjectUtil());
        runtimeContexts.put("listUtil", new ListUtil());
        runtimeContexts.put("convertPackage", "kiun.com.bvroutine.base.binding.convert");
    }

    public static RuntimeContext runTime(){
        return runtimeContext;
    }

    public MapContext clone(){

        MapContext context = new MapContext();

        for (String key : runtimeContexts.keySet()){
            context.set(key, runtimeContexts.get(key));
        }

        return context;
    }

    public void set(String name, Object value) {
        runtimeContexts.put(name, value);
    }
}
