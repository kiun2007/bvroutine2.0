package kiun.com.bvroutine.media;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.databinding.ActivityVideoRecordBinding;

import static kiun.com.bvroutine.media.av.BaseRecorder.getRecorder;

/**
 * 文 件 名: VideoRecorder
 * 作 者: 刘春杰
 * 创建日期: 2020/7/24 09:08
 * 说明:
 */
public class VideoRecorderActivity extends RequestBVActivity<ActivityVideoRecordBinding> implements DialogInterface {

    public static final Class clz = VideoRecorderActivity.class;

    String className;

    Integer maxDuration = 30;

    Integer layoutId = 0;

    @Override
    public int getViewId() {
        return layoutId == 0 ? R.layout.activity_video_record : layoutId;
    }

    @Override
    public void initView() {

        if (!TextUtils.isEmpty(className)){
            try {
                Class clz = Class.forName(className);
                if (VideoRecorder.class.isAssignableFrom(clz)){
                    binding.setRecorder(getRecorder(this, (Class<VideoRecorder>)clz, maxDuration));
                    binding.setDialog(this);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        binding.setDuration(maxDuration);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void cancel() {
        finish();
    }

    @Override
    public void dismiss() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(binding.getRecorder() != null){
            binding.getRecorder().onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(binding.getRecorder() != null){
            binding.getRecorder().onStop();
        }
    }
}