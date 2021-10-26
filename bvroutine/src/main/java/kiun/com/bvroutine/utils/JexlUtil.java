package kiun.com.bvroutine.utils;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.Map;

import kiun.com.bvroutine.base.jexl.RuntimeContext;

public class JexlUtil {

    private static JexlEngine engine = new JexlEngine();

    public static<T> T run(String exeStr, Object... argument){

        JexlContext jc = RuntimeContext.runTime().clone();

        for (int i = 0; i < argument.length; i += 2) {
            String name = argument[i] instanceof String ? (String) argument[i] : null;
            Object value = (i + 1) < argument.length ? argument[i + 1]: null;

            if(name == null){
                break;
            }
            jc.set(name, value);
        }

        Expression expression = engine.createExpression(exeStr);
        Object ret = null;

        try{
            ret = expression.evaluate(jc);
        }catch (Exception ex){
            System.out.println("执行->" + exeStr + "<- 发生故障!");
            ex.printStackTrace();
        }
        return (T) ret;
    }

    public static Object convertToCode(String jexlExp, Map<String, Object> map) {
        JexlEngine jexlEngine = new JexlEngine();
        Expression e = jexlEngine.createExpression(jexlExp);
        JexlContext jc = new MapContext();
        for (String key : map.keySet()) {
            jc.set(key, map.get(key));
        }
        Object retVal = e.evaluate(jc);
        if (null == retVal) {
            return "";
        }
        return retVal;
    }
}
