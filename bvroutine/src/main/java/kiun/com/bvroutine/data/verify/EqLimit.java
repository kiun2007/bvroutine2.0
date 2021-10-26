package kiun.com.bvroutine.data.verify;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;

/**
 * 同值限制.
 */
public class EqLimit extends VerifyBase<Object, Object> {


    public EqLimit(Object object, Verify verify) {
        super(object, verify, 100);
    }

    @Override
    protected boolean verifyValue(Object value) {
        Object extra = extra(null);
        if (extra == null){
            return value != null;
        }
        return !extra.equals(value);
    }
}
