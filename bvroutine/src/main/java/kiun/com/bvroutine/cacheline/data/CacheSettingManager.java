package kiun.com.bvroutine.cacheline.data;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.CacheSettingInterface;
import kiun.com.bvroutine.cacheline.CacheType;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;

public class CacheSettingManager {

    List<SettingUnit> allSettingUnit = null;

    /**
     * 初始化缓存设置管理器.
     * @param settings 设置类.
     * @throws Exception 可能存在类书写不规范，目前只允许CacheType 作为设置类字段.
     */
    public CacheSettingManager(CacheSettingInterface[] settings) throws Exception {
        allSettingUnit = new LinkedList<>();
        for (CacheSettingInterface setting : settings){

            Field[] fields = setting.getClass().getFields();

            for (Field field : fields){

                SettingUnit settingUnit = null;
                if(field.getType() == CacheType.class){
                    settingUnit = new SettingUnit(field, setting.theDbPath(), (CacheType) field.get(setting));
                }else{
                    continue;
                }

                allSettingUnit.add(settingUnit);
            }
        }
    }

    public SettingUnit findUnitById(String id){
        for(SettingUnit settingUnit : allSettingUnit){
            if(settingUnit.Id().equals(id)){
                return settingUnit;
            }
        }
        return null;
    }

    public SettingUnit matchUnitByURL(String url, Map<String, Object> params){


        for(SettingUnit settingUnit : allSettingUnit){

            int qeryIndex = url.indexOf("?");
            int fIndex = url.indexOf(settingUnit.getUrlFeature());
            boolean find = false;
            if(fIndex > 0){
                if(qeryIndex < 0){ //没有Query 的地址.
                    find = settingUnit.matchRestful(url);
                    if (!find){
                        find = (url.length() == (fIndex + settingUnit.getUrlFeature().length() + 0));
                    }
                }else{
                    find = (qeryIndex <= (fIndex + settingUnit.getUrlFeature().length()));
                }
            }

            if (find){
                if (settingUnit.getFilterObject(params) != null){
                    boolean isFilter = false;
                    Map<String, Object> filterJSON = settingUnit.getFilterObject(params);
                    for (String filterKey : filterJSON.keySet()) {
                        if (!params.containsKey(filterKey)){
                            isFilter = true;
                            break;
                        }
                        if (!filterJSON.get(filterKey).equals(params.get(filterKey))){
                            isFilter = true;
                            break;
                        }
                    }
                    //不符合参数条件,继续查找.
                    if(isFilter){
                        continue;
                    }
                }
                return settingUnit;
            }
        }
        return null;
    }
}