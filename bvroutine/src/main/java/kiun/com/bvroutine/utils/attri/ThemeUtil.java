package kiun.com.bvroutine.utils.attri;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ThemeUtil {

    public static TypedValue getValue(Context context, int attrId){

        Resources.Theme theme = context.getTheme();

        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(attrId, typedValue, true);
        return typedValue;
    }
}
