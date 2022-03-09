package kiun.com.bvroutine.cacheline.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.utils.MCString;

/**
 * Created by kiun_2007 on 2018/4/14.
 * JSON 数据提取器.
 */
public class JSONExtractor extends ExtractorBase<Object> {

    private boolean isMerge = false;
    private Map<String, String> inputValues;

    public JSONExtractor(Object jsonObject){
        super(jsonObject);
    }

    public JSONExtractor(Object jsonObject, boolean isMerge){
        this(jsonObject);
        this.isMerge = isMerge;
    }

    @Override
    protected Object extractNoExpression(String path) throws JSONException {
        return extractNoExpression(path, null);
    }

    @Override
    protected Object extractNoExpression(String path, String express) throws JSONException {
        String[] paths = path.split("\\.");
        JSONArray array = null;

        if(mData instanceof JSONArray){
            //最后一个提取的数组,表达式尾部.
            if (path.isEmpty()){
                if(inputValues != null){
                    for (int i = 0; i < ((JSONArray) mData).length(); i++) {
                        Object objectValue = ((JSONArray) mData).get(i);
                        for (String key : inputValues.keySet()){
                            ((JSONObject) objectValue).put(key, inputValues.get(key));
                        }
                    }
                }
                return mData;
            }
            array = new JSONArray();
            for (int i = 0; i < ((JSONArray) mData).length(); i++) {
                JSONExtractor extractorBase = new JSONExtractor(((JSONArray) mData).getJSONObject(i));
                Object object = extractorBase.extractNoExpression(path, express);
                if (object instanceof JSONArray && isMerge){
                    for (int j = 0; j < ((JSONArray) object).length(); j++) {
                        array.put(((JSONArray) object).get(j));
                    }
                }else{
                    array.put(object);
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
                root = dataName.equals("_root") ? root : ((JSONObject)root).get(dataName);

                if (indexStr.equals("i") && root instanceof JSONArray){
                    JSONExtractor extractorBase = new JSONExtractor(root);
                    StringBuffer stringBuffer = new StringBuffer(2048);
                    for (int indexPath = i + 1; indexPath < paths.length; indexPath ++){
                        stringBuffer.append(paths[indexPath]);
                        if (indexPath != paths.length - 1){
                            stringBuffer.append(".");
                        }
                    }
                    extractorBase.isMerge = isMerge;
                    if(express != null && mData instanceof JSONObject) {
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
                            maps.put(nickKey != null ? nickKey : lastKey, ((JSONObject) mData).optString(lastKey));
                            extractorBase.inputValues = maps;
                        }
                    }
                    return extractorBase.extractNoExpression(stringBuffer.toString(), express);
                }else if (root == JSONObject.NULL){
                    root = null;
                    continue;
                }

                int index = Integer.parseInt(indexStr);
                if((root instanceof JSONArray) && index < ((JSONArray)root).length()){
                    try {
                        root = ((JSONArray)root).getJSONObject(index);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    root = null;
                }
            }else {
                if (root != null && ((root instanceof JSONObject) && ((JSONObject)root).has(p))) {
                    if (i == paths.length - 1) {
                        return ((JSONObject)root).opt(p);
                    }
                    if (root instanceof JSONObject) {
                        root = ((JSONObject)root).opt(p);
                    }
                }
            }
        }
        return null;
    }

}
