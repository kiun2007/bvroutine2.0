package kiun.com.bvroutine.data.verify;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;

/**
 * 运行时检查器.
 */
public class RunCheck extends VerifyBase<Object, Object> {

    public RunCheck(Object object, Verify verify) {
        super(object, verify, 1000);
    }

    @Override
    protected boolean descIsRuntime() {
        return true;
    }

    @Override
    protected boolean verifyValue(Object value) {
        Object res = extra(false);

        if (res instanceof Boolean){
            return (boolean) res;
        }

        if (res instanceof Number){
            if (((Number) res).intValue() != 0){
                extraRunValue = res;
                return false;
            }
        }
        return true;
    }
}
