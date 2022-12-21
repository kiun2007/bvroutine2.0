package kiun.com.bvroutine.utils;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kiun.com.bvroutine.base.jexl.RuntimeContext;
import kiun.com.bvroutine.interfaces.callers.GetCaller;

public class JexlUtil {

    private static JexlEngine engine = new JexlEngine();

    /**
     * 重组字符串
     * 查找符合表达式的项并与不符合表达式的项合并
     * @param regex 正则表达式
     * @param source 源字符串
     * @param join 合并时添加的字符
     * @param with 是否需要双引号
     * @param convert 查找的字符转换方法
     * @return 重组后的字符串
     */
    public static String recombination(String regex, String source, String join, boolean with, GetCaller<String, String> convert){

        Matcher matcher = Pattern.compile(regex).matcher(source);
        List<String> paragraphs = new LinkedList<>();

        int lastEnd = 0;
        while (matcher.find()){
            String formula = matcher.group(1);

            if (matcher.start() > lastEnd){
                String plugString = source.substring(lastEnd, matcher.start());
                paragraphs.add(with?("\""+plugString+"\""):plugString);
            }
            paragraphs.add(convert==null?formula:convert.call(formula));
            lastEnd = matcher.end();
        }

        if (!paragraphs.isEmpty()){
            if (lastEnd < source.length() - 1){
                String plugString = source.substring(lastEnd);
                paragraphs.add(with?("\""+plugString+"\""):plugString);
            }
            return ListUtil.join(paragraphs, join);
        }else{
            return with?("\""+source+"\""):source;
        }
    }

    public static String translateJs(String js){
        String result = recombination("`([^`]*)`", js, "", false,
                source-> recombination("\\$\\{(.+?)\\}", source, "+", true, null)
        );
        return result;
    }

    public static <T> T runArray(String exeStr, List<Object> argument){

        JexlContext jc = RuntimeContext.runTime().clone();
        for (int i = 0; i < argument.size(); i += 2) {
            String name = argument.get(i) instanceof String ? (String) argument.get(i) : null;
            Object value = (i + 1) < argument.size() ? argument.get(i + 1): null;

            if(name == null){
                break;
            }
            jc.set(name, value);
        }

        Expression expression = engine.createExpression(translateJs(exeStr));
        Object ret = null;

        try{
            ret = expression.evaluate(jc);
        }catch (Exception ex){
            System.out.println("执行->" + exeStr + "<- 发生故障!");
            ex.printStackTrace();
        }
        return (T) ret;
    }

    public static<T> T run(String exeStr, Object... argument){
        return runArray(exeStr, Arrays.asList(argument));
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
