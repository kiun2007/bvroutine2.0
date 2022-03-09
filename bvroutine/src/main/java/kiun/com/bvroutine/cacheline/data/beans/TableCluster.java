package kiun.com.bvroutine.cacheline.data.beans;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.cacheline.utils.JexlUtil;
import kiun.com.bvroutine.utils.MCString;

/**
 * 表群关系描述.
 */
public class TableCluster {

    private String rootTableName;
    private String rootKeyName;

    //主键别称.
    private String rootKeyNick;

    private Map<String, String> pathTableMap;
    private Map<String, String> pathKeyMap;

    private String extra;

    private Map<String, Object> params;

    /**
     * 初始化表群关系表述.
     * @param tableExpress 表名表达式.
     * @param keyExpress 主键表达式.
     */
    public TableCluster(String tableExpress, String keyExpress, String extra) throws Exception {

        this.extra = extra;

        if (tableExpress == null || keyExpress == null){
            throw new Exception("TableCluster init error: tableExpress or keyExpress is Null!");
        }
        int tableExpIndex = tableExpress.indexOf("=");
        int keyExpresIndex = keyExpress.indexOf("=");
        if(keyExpresIndex < 0 || tableExpIndex < 0){
            throw new Exception("TableCluster express error: unfound character '=' !");
        }

        rootTableName = tableExpress.substring(0, tableExpIndex);
        rootKeyName = keyExpress.substring(0, keyExpresIndex);

        if (rootKeyName.indexOf("(") >= 0 && rootKeyName.indexOf(")") >= 0){
            String[] matchs = MCString.patternValues("(.+?)\\((.+?)\\)", rootKeyName);
            if (matchs.length != 2){
                throw new Exception("TableCluster express error: root key name analysis error, must be 'keyName(nickName)' !");
            }
            rootKeyName = matchs[0];
            rootKeyNick = matchs[1];
        }

        pathTableMap = getExpressMap(tableExpress.substring(tableExpIndex+1));
        pathKeyMap = getExpressMap(keyExpress.substring(keyExpresIndex+1));
    }

    private Map<String, String> getExpressMap(String express){
        String rets[] = MCString.patternValues("\\{(.+?)=(.+?)\\}", express);
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < rets.length && rets.length > 1; i+=2) {
            map.put(rets[i], rets[i+1]);
        }
        return map;
    }

    /**
     * 获取最顶层表名.
     * @return 数据库表名.
     */
    public String getTableName() {
        return rootTableName;
    }

    public String getTableName(String dataPath){
        if(pathTableMap != null){
            return pathTableMap.get(dataPath);
        }
        return null;
    }

    public String getKeyName(){
        return rootKeyName;
    }

    public String getKeyName(String dataPath){
        if(pathKeyMap != null){
            return pathKeyMap.get(dataPath);
        }
        return null;
    }

    public String[] pathKeys(){
        String keys[] = new String[pathTableMap.keySet().size()];
        pathTableMap.keySet().toArray(keys);
        return keys;
    }

    public String getRootKeyNick() {
        return rootKeyNick;
    }

    public String getNickOrName(){
        if (rootKeyNick != null){
            return rootKeyNick;
        }
        return rootKeyName;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getExtraJSON() {
        if (params != null && extra != null){
            return JexlUtil.exeCodeByParams(extra, params);
        }
        return null;
    }
}
