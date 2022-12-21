package kiun.com.bvroutine.cacheline;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.cacheline.data.SqliteDBHelper;
import kiun.com.bvroutine.cacheline.data.UploadRelationManager;
import kiun.com.bvroutine.cacheline.data.beans.RelationObject;
import kiun.com.bvroutine.cacheline.data.beans.SettingUnit;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;
import kiun.com.bvroutine.cacheline.utils.JSONUtil;
import kiun.com.bvroutine.cacheline.utils.JexlUtil;
import kiun.com.bvroutine.utils.MCString;
import kiun.com.bvroutine.utils.ObjectUtil;

class CacheUtil {

    /**
     * 填充上传数据
     * @param data 主数据
     * @param settingUnit 配置
     * @return
     */
    static Boolean fillUpload(Object data, SettingUnit settingUnit, UploadRelationManager relationManager){

        SqliteDBHelper sqliteDBHelper = settingUnit.getDbHelper(), uploadDb = settingUnit.getUploadHelper();
        //数据库存在问题 返回存储失败.
        if(sqliteDBHelper == null || uploadDb == null) return false;

        Object uploadData = data;
        String dummyKey = null;
        Map<String, Object> newParams = null;
        Map<String, Object> whereJSON = null;

        if (data instanceof Map){
            whereJSON = settingUnit.getFilterObject((Map<String, Object>) data);
            Map<String, Object> upJSON = JSONUtil.clone(new HashMap<>(), (Map<String, Object>) data);
            Map<String, Object> param = settingUnit.getParam((Map<String, Object>) data);
            if (param != null) data = param;

            if (data != null && TextUtils.isEmpty((dummyKey = (String) ((Map<String, Object>) data).get(settingUnit.Key())))
                    && whereJSON == null){ //如果主键不存在先创建一个虚拟的主键,虚拟的主键不能用于上传参数.
                ((Map<String, Object>) data).put(settingUnit.Key(), dummyKey = MCString.randUUID());
            }

            newParams = (Map<String, Object>) data;
            if(!settingUnit.Intercept().isEmpty()){
                //拦截操作,可能存在重复.
                Object isIntercept = JexlUtil.exeCode(settingUnit.Intercept(), newParams, sqliteDBHelper);
                if ((isIntercept instanceof Boolean) && ((Boolean) isIntercept).booleanValue() == true){
                    return false;
                }
            }
            uploadData = settingUnit.IsDelete() ? newParams : upJSON;
        } else if (data instanceof List){
            sqliteDBHelper.createTableAndFill(settingUnit.Name(), settingUnit.Key(), data, null);
        }

        UploadObject uploadObject = new UploadObject(settingUnit, uploadData, dummyKey);

        if (newParams != null){
            RelationObject relationObject = relationManager.requestRelation(settingUnit.Update(), settingUnit.ReplaceKey(), JSONUtil.clone(new HashMap<>(), newParams));
            uploadObject.setRelation(relationObject);
        }

        if (!settingUnit.OnlyCache()){
            if (!UploadObject.putUpload(uploadObject, uploadDb)){
                return false;
            }
        }

        //插入关联表逻辑, 关联操作不做特别事务, 主要数据成功就成功, 主要数据失败也失败.
        for (String aboutExpress : settingUnit.About()){
            String[] express = aboutExpress.split("=");
            if(express.length == 2){
                //关联表格.
                String aboutTable = express[0];
                boolean isDel = aboutTable.indexOf("<DEL>") == 0;
                boolean isIns = aboutTable.indexOf("<INS>") == 0;
                if(isDel || isIns) aboutTable = aboutTable.substring(5);

                //关联参数表达式.
                String exp = express[1];
                //生成参数.
                Object retValue = JexlUtil.exeCode(exp, newParams, sqliteDBHelper);

                if(retValue != null){

                    if (retValue instanceof Map){
                        Map<String, Object> jsonValue = (Map<String, Object>)retValue;
                        if (isDel){
                            //对表格进行删除操作.
                            sqliteDBHelper.delByValue(aboutTable, jsonValue);
                        }else{
                            sqliteDBHelper.updateValue(aboutTable, jsonValue, isIns);
                        }
                    }else if (retValue instanceof List){
                        for (int i = 0; i < ((List) retValue).size(); i++) {
                            sqliteDBHelper.updateValue(aboutTable, (Map<String, Object>) ((List) retValue).get(i));
                        }
                    }
                }
            }
        }

        if (settingUnit.IsDelete()){

            if(newParams != null){
                if (!settingUnit.Params().isEmpty()){
                    newParams = JexlUtil.exeCodeByParams(settingUnit.Params(), newParams);
                }
                Map<String, Object> keyValues = new HashMap<>();
                keyValues.put("key_value", newParams.get(settingUnit.Key()));

                //删除本地上传表数据.
                int uploadDelSize = settingUnit.OnlyCache() ? 1 : uploadDb.delByValue(UploadObject.UP_LOAD_TABLE, keyValues);
                int dataDelSize = sqliteDBHelper.delByValue(settingUnit.Name(), newParams);
                return saveTransaction(uploadDelSize > 0 && dataDelSize > 0, sqliteDBHelper, uploadDb);
            }
            return true;
        }

        if (whereJSON != null){
            return saveTransaction(sqliteDBHelper.updateValueWhere(settingUnit.Name(), (Map<String, Object>) data, whereJSON), sqliteDBHelper, uploadDb);
        }

        return null;
    }

    static boolean saveTransaction(boolean isSuccess, SqliteDBHelper dataDb, SqliteDBHelper uploadDb){
        //保存成功确认事务.
        if (isSuccess) {
            ObjectUtil.batchCall(item -> item.setTransactionSuccessful(), dataDb, uploadDb);
        } else {
            System.out.println("事务失败");
        }
        return isSuccess;
    }
}
