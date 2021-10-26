package kiun.com.bvroutine.data.verify;

import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;

/**
 * 字符串长度检测.
 */
public class LengthLimit extends VerifyBase<String, int[]> {

    public LengthLimit(Object object, Verify verify) {
        super(object, verify, 1000);
    }

    @Override
    protected boolean verifyValue(String value) {

        int[] limits = extra(new int[]{-1, Integer.MAX_VALUE});
        if (value != null){
            return value.length() >= limits[0] && (limits.length != 2 || value.length() <= limits[1]);
        }
        return false;
    }
}
