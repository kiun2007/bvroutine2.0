package kiun.com.bvroutine.views.text.tags;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.Map;

public abstract class TextTagBase extends TagBase {

    protected abstract String color(Map<String, String> attributes);

    protected abstract String size(Map<String, String> attributes);

    @Override
    public boolean endHandleTag(Editable output, Map<String, String> attributes, int start, int end) {

        boolean isOver = super.endHandleTag(output, attributes, start, end);
        if (!isOver){
            String color = color(attributes);
            String size = size(attributes);

            // 设置颜色
            if (!TextUtils.isEmpty(color)) {
                output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (!TextUtils.isEmpty(size)) {
                boolean isDip = false;
                if (size.endsWith("sp") || size.endsWith("dp")){
                    isDip = true;
                }
                size = size.replace("sp", "").replace("dp","");
                output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size), isDip), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return false;
    }
}
