package kiun.com.bvroutine.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.databinding.ActivityMediaPlayerBinding;

import static android.media.MediaPlayer.MEDIA_INFO_BUFFERING_END;
import static android.media.MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START;

/**
 * 文 件 名: MediaPlayActivity
 * 作 者: 刘春杰
 * 创建日期: 2020/7/26 16:46
 * 说明: 媒体播放器
 */
public class MediaPlayActivity extends RequestBVActivity<ActivityMediaPlayerBinding> implements MediaPlayer.OnCompletionListener {

    public static final Class clz = MediaPlayActivity.class;

    String url;

    @Override
    public int getViewId() {
        return R.layout.activity_media_player;
    }

    @Override
    public void initView() {

        if (url != null){
            binding.videoView.setVideoPath(url);
            binding.videoView.start();
            binding.videoView.setOnCompletionListener(this);
            binding.setState(MediaState.Playing);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void onPlay(View view){
        binding.videoView.start();
        binding.setState(MediaState.Playing);
    }

    public void onCancel(View view){
        finish();
    }

    public void onSelect(View view){
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        binding.setState(MediaState.Stop);
    }
}