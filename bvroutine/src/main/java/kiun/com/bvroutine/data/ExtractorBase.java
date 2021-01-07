package kiun.com.bvroutine.data;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.utils.JexlUtil;
import kiun.com.bvroutine.utils.MCString;

/**
 * Created by kiun_2007 on 2018/4/14.
 * 数据提取器
 */
public abstract class ExtractorBase<T> {

    protected T mData = null;
    public ExtractorBase(T dataSource) {
        mData = dataSource;
    }

    /**
     * 根据路径提取数据.
     * @param path 数据路径.
     * @param express 虚拟数据节点生成表达式.
     * @return 数据.
     */
    public final <E>E extract(String path, String express){

        if (path.equals(".")){
            return (E) mData;
        }
        String[] expString = path.split("[+,\\-,*,/,\\(,\\)]");
        if(expString.length == 1){
            try {
                return (E) extractNoExpression(path, express);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            expString = MCString.stringsSort(expString);
            Map<String, Object> map = new HashMap<String, Object>();
            for (String expPath : expString){
                try {
                    map.put(expPath, extractNoExpression(expPath));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return (E) JexlUtil.convertToCode(path, map);
        }
    }

    public final <E>E extract(String path){
        return extract(path, null);
    }

    protected abstract Object extractNoExpression(String path) throws JSONException;

    protected abstract Object extractNoExpression(String path, String express) throws JSONException;
}