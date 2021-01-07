package kiun.com.bvroutine.utils;

import android.app.Activity;
import android.content.Context;

public class ContextUtil {

    public static String getString(Context context, String name){
        if (context instanceof Activity){
            Activity activity = (Activity) context;
            return activity.getIntent().getStringExtra(name);
        }
        return null;
    }
}
