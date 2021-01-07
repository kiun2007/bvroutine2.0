package kiun.com.bvroutine.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;

public class Timer extends Handler {

    List<GetTNoParamCall<Boolean>> callBackList = new LinkedList<>();
    boolean isLoop = false;
    boolean initLoop = false;
    int timeSpace = 0;
    int tally = 0;
    int maxTally = Integer.MAX_VALUE;
    CallBack stopCallBack = null;

    public Timer(Context context){
        super(context.getMainLooper());
    }

    public Timer addListener(GetTNoParamCall<Boolean> callBack){
        callBackList.add(callBack);
        return this;
    }

    public Timer loop(boolean isLoop){
        this.initLoop = isLoop;
        return this;
    }

    public Timer maxTally(int maxTally, CallBack callBack){
        this.maxTally = maxTally;
        this.stopCallBack = callBack;
        return this;
    }

    public void stop(){
        removeMessages(0);
        isLoop = false;
    }

    public Timer start(int timeSpace){
        this.timeSpace = timeSpace;
        this.tally = 0;
        this.isLoop = initLoop;
        sendEmptyMessageDelayed(0, timeSpace);
        return this;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {

        for (GetTNoParamCall<Boolean> callback : callBackList){
            try {
                if(!callback.call()){
                    isLoop = false;
                }
            } catch (Exception e) {
                isLoop = false;
                break;
            }
        }

        boolean isComplete = tally >= maxTally;

        if (isComplete){
            isLoop = false;
        }

        if (isLoop){
            sendEmptyMessageDelayed(0, timeSpace);
        }

        if (isComplete){
            if (stopCallBack != null){
                stopCallBack.call();
            }
        }

        tally ++;
    }

    public int getTally() {
        return tally;
    }
}
