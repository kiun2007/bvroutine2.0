package kiun.com.bvroutine.cacheline.data;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.utils.MCString;

/**
 * Created by kiun_2007 on 2018/4/14.
 * JSON 数据提取器.
 */
public class MapExtractor extends ExtractorBase<Object> {

    private boolean isMerge = false;
    private Map<String, String> inputValues;

    public MapExtractor(Object jsonObject){
        super(jsonObject);
    }

    public MapExtractor(Object jsonObject, boolean isMerge){
        this(jsonObject);
        this.isMerge = isMerge;
    }

    @Override
    protected Object extractNoExpression(String path) {
        return extractNoExpression(path, null);
    }

    @Override
    protected Object extractNoExpression(String path, String express){
        String[] paths = path.split("\\.");
        List<Map<String, Object>> array = null;

        if(mData instanceof List){
            //最后一个提取的数组,表达式尾部.
            if (path.isEmpty()){
                if(inputValues != null){
                    for (int i = 0; i < ((List) mData).size(); i++) {
                        Object objectValue = ((List) mData).get(i);
                        for (String key : inputValues.keySet()){
                            ((Map<String, Object>) objectValue).put(key, inputValues.get(key));
                        }
                    }
                }
                return mData;
            }
            array = new LinkedList<>();
            for (int i = 0; i < ((List) mData).size(); i++) {
                MapExtractor extractorBase = new MapExtractor(((List) mData).get(i));
                Object object = extractorBase.extractNoExpression(path, express);
                if (object instanceof List && isMerge){
                    for (int j = 0; j < ((List) object).size(); j++) {
                        array.add((Map<String, Object>) ((List) object).get(j));
                    }
                }else{
                    array.add((Map<String, Object>) object);
                }
            }
            return array;
        }
        Object root = mData;

        for (int i = 0; i < paths.length && root != null; i ++){
            String p = paths[i];
            if(p.indexOf('[') >= 0 && p.indexOf(']') >=0){
                String dataName = p.substring(0, p.indexOf('['));
                String indexStr = p.substring(p.indexOf('[') + 1,  p.indexOf(']'));
                root = dataName.equals("_root") ? root : ((Map<String, Object>)root).get(dataName);

                if (indexStr.equals("i") && root instanceof List){
                    MapExtractor extractorBase = new MapExtractor(root);
                    StringBuffer stringBuffer = new StringBuffer(2048);
                    for (int indexPath = i + 1; indexPath < paths.length; indexPath ++){
                        stringBuffer.append(paths[indexPath]);
                        if (indexPath != paths.length - 1){
                            stringBuffer.append(".");
                        }
                    }
                    extractorBase.isMerge = isMerge;
                    if(express != null && mData instanceof List) {
                        String exps[] = express.split("\\.");
                        if (exps.length > 1 && exps[0].indexOf(dataName) > -1){
                            String lastKey = exps[exps.length-1];
                            String nickKey = null;
                            Map<String, String> maps = new HashMap<>();

                            String matchs[] = MCString.patternValues("(.+?)\\((.+?)\\)", lastKey);
                            if(matchs.length == 2){
                                lastKey = matchs[0];
                                nickKey = matchs[1];
                            }
                            maps.put(nickKey != null ? nickKey : lastKey, (String) ((Map<String, Object>) mData).get(lastKey));
                            extractorBase.inputValues = maps;
                        }
                    }
                    return extractorBase.extractNoExpression(stringBuffer.toString(), express);
                }else if (root == null){
                    root = null;
                    continue;
                }

                int index = Integer.parseInt(indexStr);
                if((root instanceof List) && index < ((List)root).size()){
                    root = ((List)root).get(index);
                }else{
                    root = null;
                }
            }else {
                if (root != null && ((root instanceof Map) && ((Map)root).containsKey(p))) {
                    if (i == paths.length - 1) {
                        return ((Map)root).get(p);
                    }
                    if (root instanceof Map) {
                        root = ((Map)root).get(p);
                    }
                }
            }
        }
        return null;
    }

}
