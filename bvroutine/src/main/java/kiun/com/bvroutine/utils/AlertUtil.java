package kiun.com.bvroutine.utils;

import android.app.AlertDialog;
import android.content.Context;

public class AlertUtil {

    public static AlertDialog.Builder build(Context context, String format, Object ... arg){
        String message = String.format(format, arg);
        return new AlertDialog.Builder(context).setTitle("提示").setMessage(message);
    }
}
