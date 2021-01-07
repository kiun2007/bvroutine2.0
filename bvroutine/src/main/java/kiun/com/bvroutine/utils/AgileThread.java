package kiun.com.bvroutine.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.callers.SetThrowCaller;

/**
 * 敏捷线程类，灵活切换主次线程.
 */
public final class AgileThread extends Thread {

    Context context;
    SetThrowCaller<AgileThread> callBack;
    Object retValue = null;
    long maxWait = 4 * 60 * 60 * 1000;
    private boolean isKilling = false;

    private static List<AgileThread> threads = new ArrayList<>();

    public AgileThread(Context context, SetThrowCaller<AgileThread> callBack){
        super(()->{
            AgileThread thread = (AgileThread) Thread.currentThread();
            try {
                callBack.call(thread);
            } catch (Exception e) {
                e.printStackTrace();
            }
            threads.remove(thread);
        });
        this.context = context;
        this.callBack = callBack;

    }

    public final void notify(Object value){
        retValue = value;
        synchronized (this) {
            notify();
        }
    }

    public AgileThread setMaxWait(long maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    /**
     * 处理逻辑并入主线程，并等待结果.
     * @param callBack 函数参数.
     * @param isAuto 函数结束后立即切换到次线程.
     */
    public final<T> T intoWait(boolean isAuto, CallBack callBack){

        retValue = null;

        if (callBack != null){
            new CallHandler(context, ()->{
                callBack.call();
                if (isAuto){
                    synchronized (this) {
                        notify();
                    }
                }
            }).sendEmptyMessage(0);
        }

        try {
            synchronized (this){
                wait(maxWait);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (T) retValue;
    }

    public final void into(CallBack callBack){
        new CallHandler(context, ()->{
            callBack.call();
        }).sendEmptyMessage(0);
    }

    public final void intoWait(CallBack callBack){
        intoWait(true, callBack);
    }

    public final void kill(){
        isKilling = true;
    }

    public final boolean isKilling(){
        return isKilling;
    }

    @Override
    public synchronized void start() {
        threads.add(this);
        super.start();
    }
}
