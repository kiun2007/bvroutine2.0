package kiun.com.bvroutine.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;

import kiun.com.bvroutine.interfaces.callers.CallBack;

public class CallHandler extends Handler {

    CallBack callBack;

    public CallHandler(Context context, @NonNull CallBack callBack){
        super(context.getMainLooper());
        this.callBack = callBack;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        callBack.call();
    }
}
