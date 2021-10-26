package kiun.com.bvroutine.views.text.tags;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import java.util.Map;

public abstract class TextTagBase extends TagBase {

    protected abstract String color(Map<String, String> attributes);

    protected abstract String size(Map<String, String> attributes);

    protected abstract String backgroundColor(Map<String, String> attributes);

    @Override
    public boolean endHandleTag(Editable output, Map<String, String> attributes, int start, int end) {

        boolean isOver = super.endHandleTag(output, attributes, start, end);
        if (!isOver){
            String color = color(attributes);
            String size = size(attributes);
            String bgColor = backgroundColor(attributes);

            // 设置颜色
            if (!TextUtils.isEmpty(color)) {
                int orgColor = Color.BLACK;
                try{
                    orgColor = Color.parseColor(color);
                }catch (Exception ex){
                    Log.e("Color", "Error Color = " + color);
                }
                output.setSpan(new ForegroundColorSpan(orgColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (!TextUtils.isEmpty(bgColor)){
                output.setSpan(new BackgroundColorSpan(Color.parseColor(bgColor)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
