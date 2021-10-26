package kiun.com.bvroutine.data.verify;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;

/**
 * 空值检测.
 */
public class NotNull extends VerifyBase<Object, Boolean> {

    public NotNull(Object object, Verify verify) {
        super(object, verify, 10000000);
    }

    @Override
    protected String getDesc(String field) {
        return String.format("\"%s\"不能为空!", field);
    }

    @Override
    protected boolean verifyValue(Object value) {
        Boolean isNull = extra(false);
        return isNull || value != null;
    }
}
