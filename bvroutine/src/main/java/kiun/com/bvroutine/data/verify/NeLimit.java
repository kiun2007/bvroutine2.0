package kiun.com.bvroutine.data.verify;


import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;

/**
 * 不同值限制.
 * 字段值与目标相同通过,不同报错.
 */
public class NeLimit extends VerifyBase<Object, Object> {

    public NeLimit(Object object, Verify verify) {
        super(object, verify, 100);
    }

    @Override
    protected boolean verifyValue(Object value) {
        Object extra = extra(null);
        if (extra == null){
            return value == null;
        }
        return extra.equals(value);
    }
}
