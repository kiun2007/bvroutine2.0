package kiun.com.bvroutine.views.text.tags;

import android.text.Editable;
import android.text.TextUtils;

import java.util.Map;

public abstract class TagBase {

    protected abstract String show(Map<String, String> attributes);

    /**
     *
     * @param output
     * @param attributes
     * @param start
     * @param end
     * @return 是否阻断子类解析.
     */
    public boolean endHandleTag(Editable output, Map<String, String> attributes, int start, int end){

        String showValue = show(attributes);
        if (!TextUtils.isEmpty(showValue)){
            String[] values = showValue.split("==");
            if (values.length == 2 && !values[0].equals(values[1])){
                output.delete(start, end);
            }
            return true;
        }
        return false;
    }
}
