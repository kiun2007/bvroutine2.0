package kiun.com.bvroutine.media;

import android.content.DialogInterface;
import androidx.databinding.ObservableField;

import java.io.File;

import kiun.com.bvroutine.interfaces.callers.IntentSetCaller;

/**
 * 录制对象.
 */
public interface Recorder {

    /**
     * 开始录制.
     */
    void startRecord();

    /**
     * 停止录制.
     */
    void stopRecord();

    /**
     * 取消.
     * @param dialog
     */
    void cancel(DialogInterface dialog);

    /**
     * 上传.
     * @param dialog
     */
    void upload(DialogInterface dialog);

    /**
     * 设置上传回调
     * @param caller
     */
    void setCallBack(IntentSetCaller caller);

    /**
     * 获取时间绑定字段.
     * @return
     */
    ObservableField<String> getTimeField();

    /**
     * 获取状态绑定字段.
     * @return
     */
    ObservableField<MediaState> getStateField();

    /**
     * 当前录制文件.
     * @return
     */
    File getCurrent();
}
