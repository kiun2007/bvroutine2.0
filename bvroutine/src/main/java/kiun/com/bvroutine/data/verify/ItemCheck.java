package kiun.com.bvroutine.data.verify;

import java.util.List;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;
import kiun.com.bvroutine.utils.JexlUtil;
import kiun.com.bvroutine.utils.ListUtil;

/**
 * 列表单项检查
 * 必须放在{@link List} 类型字段.
 * 任意一项检查出错整个列表检查失败.
 */
public class ItemCheck extends VerifyBase<List, Boolean> {

    public ItemCheck(Object object, Verify verify) {
        super(object, verify, 1000);
    }

    public Boolean extra(Boolean def, Object item) {
        if (verify.extra().isEmpty()){
            return def;
        }
        return JexlUtil.run(verify.extra(), "that", object, "fieldName", fieldName, "item", item);
    }

    @Override
    protected boolean verifyValue(List value) {

        if (!ListUtil.isEmpty(value)){
            for (Object item : value){
                if (!extra(true, item)){
                    return false;
                }
            }
        }
        return true;
    }
}
