package kiun.com.bvroutine.utils;

import android.os.Debug;
import android.util.Log;

public class LogUtil {

    public static final String TAG = "LogUtil";

    public static void Log(String tag, String content){
        if (tag == null){
            tag = TAG;
        }

        if(Debug.isDebuggerConnected()){
            if(content != null){
                Log.d(tag, content);
            }
        }
    }

    public static void Log(String content) {
        Log(null, content);
    }
}
