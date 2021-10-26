package kiun.com.bvroutine.media.av;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.ObservableField;
import android.net.Uri;

import java.io.File;

import kiun.com.bvroutine.interfaces.callers.IntentSetCaller;
import kiun.com.bvroutine.media.Recorder;
import kiun.com.bvroutine.media.MediaState;
import kiun.com.bvroutine.utils.FileUtil;
import kiun.com.bvroutine.utils.ObjectUtil;

public abstract class BaseRecorder implements Recorder {

    protected Context context;
    private int maxDuration;
    protected IntentSetCaller caller;
    protected File mFilePath;

    @Override
    public File getCurrent(){
        return mFilePath;
    }

    public BaseRecorder(Context context, int maxDuration){
        this.context = context;
        this.maxDuration = maxDuration;
    }

    @SuppressLint("DefaultLocale")
    protected void createFile(String type) {

        if (mFilePath != null){
            mFilePath.delete();
        }
        mFilePath = FileUtil.createFile(context, type);
    }

    protected ObservableField<String> timeField = new ObservableField<>();

    protected ObservableField<MediaState> stateField = new ObservableField<>(MediaState.Nothing);

    @Override
    public ObservableField<String> getTimeField() {
        return timeField;
    }

    @Override
    public ObservableField<MediaState> getStateField() {
        return stateField;
    }

    protected void changState(MediaState state){
        stateField.set(state);
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    @Override
    public void setCallBack(IntentSetCaller caller) {
        this.caller = caller;
    }

    @Override
    public void upload(DialogInterface dialog) {

        if (caller != null && getCurrent() != null){
            Intent intent = new Intent();
            intent.setData(Uri.fromFile(getCurrent()));
            caller.call(intent);
        }

        if (dialog != null){
            dialog.dismiss();
        }
    }

    public static<T extends Recorder> T getRecorder(Context context, Class<T> recorderClz, int duration){

        if (duration == -1){
            duration = Integer.MAX_VALUE;
        }

        T recorder = ObjectUtil.newObject(recorderClz, context, duration);
        if (recorder == null){
            recorder = ObjectUtil.newObject(recorderClz);
        }
        return recorder;
    }

    public static<T extends Recorder> T getRecorder(Context context, String className, int duration){

        if (!"null".equals(className)){
            try {
                Class<T> clz = (Class<T>) Class.forName(className);
                if (Recorder.class.isAssignableFrom(clz)){
                    return getRecorder(context, clz, duration);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
