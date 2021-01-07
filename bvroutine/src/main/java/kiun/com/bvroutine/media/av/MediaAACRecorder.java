package kiun.com.bvroutine.media.av;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.widget.Toast;

import java.io.IOException;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.media.MediaState;
import kiun.com.bvroutine.utils.Timer;

public class MediaAACRecorder extends BaseRecorder implements MediaRecorder.OnInfoListener {

    private MediaRecorder mRecorder;

    private Timer timer;

    private boolean isReady = false;

    public MediaAACRecorder(Context context, int maxDuration) {
        super(context, maxDuration);
        timer = new Timer(context).addListener(()->{

            int min = timer.getTally() / 60;
            int second = timer.getTally() % 60;
            getTimeField().set(String.format("%02d:%02d", min, second));
            return true;
        }).loop(true).maxTally(maxDuration, this::stopRecord);

        getTimeField().set("00:00");


    }

    @Override
    public void startRecord() {
        if (context instanceof BVBaseActivity){
            ((BVBaseActivity) context).startPermission(this::beginRecordAudio, ()->{
                Toast.makeText(context, "录音权限被拒绝，无法正常录音，请到设置修改权限", Toast.LENGTH_LONG).show();
            },Manifest.permission.RECORD_AUDIO);
        }
    }

    private void beginRecordAudio(){

        createFile("aac");

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS); //录音文件保存的格式，这里保存为 AAC
        mRecorder.setOutputFile(mFilePath.getAbsolutePath()); // 设置录音文件的保存路径
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);
        // 设置录音文件的清晰度
        mRecorder.setAudioSamplingRate(44100);
        mRecorder.setAudioEncodingBitRate(192000);
        mRecorder.setOnInfoListener(this);

        try {
            mRecorder.prepare();
            mRecorder.start();
            timer.start(1000);
            changState(MediaState.Recording);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopRecord() {

        timer.stop();
        if (mRecorder != null){
            mRecorder.stop();
        }
        mRecorder = null;
        changState(MediaState.Recorded);
    }

    @Override
    public void cancel(DialogInterface dialog) {
        dialog.dismiss();
        stopRecord();
        getTimeField().set("00:00");
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        int a = 0;
    }
}