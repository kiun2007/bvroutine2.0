package kiun.com.bvroutine.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kiun.com.bvroutine.interfaces.Scheduled;
import kiun.com.bvroutine.utils.MCString;

public class ScheduledMethod {

    private long duration;

    private Method method;

    private Context context;

    private Handler handler;

    private boolean isAuto;

    public ScheduledMethod(long duration, Method method, Context context, boolean isAsync) {

        this.duration = duration;
        this.method = method;
        this.context = context;

        if (isAsync){
            new Thread(()->{
                Looper.prepare();
                createHandler(Looper.myLooper());
                if (isAuto){
                    handler.sendEmptyMessageDelayed(0, duration);
                }
                Looper.loop();
            }).start();
        }else {
            createHandler(Looper.getMainLooper());
        }
    }

    private void createHandler(Looper looper){

        handler = new Handler(looper){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                handler.sendEmptyMessageDelayed(0, duration);
                call();
            }
        };
    }

    public long getDuration() {
        return duration;
    }

    private void call() {
        if (method != null){
            try {
                method.invoke(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        if (!isAuto){
            handler.sendEmptyMessageDelayed(0, duration);
        }
    }

    public void stop(){
        handler.removeMessages(0);
        isAuto = false;
    }

    public static List<ScheduledMethod> getScheduled(Context context){

        List<ScheduledMethod> methods = new LinkedList<>();
        methods.addAll(getScheduledMap(context).values());
        return methods;
    }

    public static Map<String, ScheduledMethod> getScheduledMap(Context context){

        Map<String, ScheduledMethod> scheduledMethodMap = new HashMap<>();
        for (Method method : context.getClass().getDeclaredMethods()){
            Scheduled scheduled = method.getAnnotation(Scheduled.class);

            if (scheduled != null && scheduled.value() > 0){
                ScheduledMethod scheduledMethod = new ScheduledMethod(scheduled.value(), method, context, scheduled.async());
                if (scheduledMethod.isAuto = scheduled.auto()){
                    if (scheduledMethod.handler != null){
                        scheduledMethod.start();
                    }
                }

                String key = scheduled.key();
                if (key.isEmpty()){
                    key = MCString.randUUID();
                }
                scheduledMethodMap.put(key, scheduledMethod);
            }
        }
        return scheduledMethodMap;
    }
}
