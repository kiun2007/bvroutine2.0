package kiun.com.bvroutine.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import java.util.Map;

public abstract class BaseService extends Service{

    private Map<String, ScheduledMethod> scheduledMethodMap;

    public BaseService(){
        scheduledMethodMap = ScheduledMethod.getScheduledMap(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BaseServiceBinder(this);
    }

    /**
     * 开始任务
     * @param key
     */
    public void startScheduled(String key){
        ScheduledMethod method = scheduledMethodMap.get(key);
        if (method != null){
            method.start();
        }
    }

    /**
     * 停止任务
     * @param key
     */
    public void stopScheduled(String key){
        ScheduledMethod method = scheduledMethodMap.get(key);
        if (method != null){
            method.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scheduledMethodMap != null){
            for (ScheduledMethod method : scheduledMethodMap.values()){
                method.stop();
            }
        }
    }
}
