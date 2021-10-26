package kiun.com.bvroutine.data.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kiun.com.bvroutine.data.VerifyBase;
import kiun.com.bvroutine.interfaces.verify.Verify;

/**
 * 正则验证.
 */
public class Regex extends VerifyBase<String, String> {

    public Regex(Object object, Verify verify) {
        super(object, verify, 1000);
    }

    @Override
    protected boolean verifyValue(String value) {

        String regex = extra("");
        if (value != null){
            if (!regex.isEmpty()){
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);

                return matcher.matches();
            }
        }
        return false;
    }
}
