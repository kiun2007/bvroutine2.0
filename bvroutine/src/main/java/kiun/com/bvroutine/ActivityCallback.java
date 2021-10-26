package kiun.com.bvroutine;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.MCString;

public class ActivityCallback {

    public static final String ACTIVITY_GUID = "ACTIVITY_GUID";

    public static final String IS_REMOVE = "IS_REMOVE";

    static Callback callback;
    static Map<String, SetCaller<Activity>> activityCallerMap = new HashMap<>();
    static {
    }

    public static void addCallBack(String uuid, SetCaller<Activity> caller){
        activityCallerMap.put(uuid, caller);
    }

    public static String getGuid(){
        return MCString.randUUID();
    }

    public static class Callback implements Application.ActivityLifecycleCallbacks{

        private List<Activity> activityList = new LinkedList<>();

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            String guid = activity.getIntent().getStringExtra(ACTIVITY_GUID);

            if (guid != null){
                SetCaller<Activity> caller = activityCallerMap.get(guid);
                if (caller != null){
                    caller.call(activity);
                }
            }

            activityList.add(activity);
        }

        public void allFinish(Class notClz){
            for (Activity activity : activityList) {
                if (!activity.getClass().equals(notClz)){
                    activity.finish();
                }
            }
        }

        public final Activity getTop(){
            if (activityList.isEmpty()){
                return null;
            }

            return activityList.get(activityList.size() - 1);
        }

        @Override
        public void onActivityStarted(Activity activity) {}

        @Override
        public void onActivityResumed(Activity activity) {}

        @Override
        public void onActivityPaused(Activity activity) {}

        @Override
        public void onActivityStopped(Activity activity) {}

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

        @Override
        public void onActivityDestroyed(Activity activity) {
            String guid = activity.getIntent().getStringExtra(ACTIVITY_GUID);
            boolean isRemove = activity.getIntent().getBooleanExtra(IS_REMOVE, true);

            if (isRemove){
                activityCallerMap.remove(guid);
            }
            activityList.remove(activity);
        }
    }
}
