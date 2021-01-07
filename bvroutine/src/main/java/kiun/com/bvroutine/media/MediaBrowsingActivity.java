package kiun.com.bvroutine.media;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import java.io.IOException;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.databinding.ActivityMediaBrowsingBinding;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.file.MediaUtil;
import okhttp3.MediaType;

/**
 * 文 件 名: MediaBrowsing
 * 作 者: 刘春杰
 * 创建日期: 2020/9/1 17:50
 * 说明: 媒体查看
 */
public class MediaBrowsingActivity extends RequestBVActivity<ActivityMediaBrowsingBinding> implements MediaPlayer.OnPreparedListener {

    String url;

    public static final Class clz = MediaBrowsingActivity.class;

    MediaPlayer mediaPlayer;

    @Override
    public int getViewId() {
        return R.layout.activity_media_browsing;
    }

    @Override
    public void initView() {
        binding.setUrl(url);
        MediaType type = MediaUtil.mediaType(this, url);
        binding.setType(type.type());
        binding.setReady(false);
        binding.setPlaying(false);

        Uri uri = Uri.parse(ServiceGenerator.getUrl(url));
        if (type.type().equals("video")){

            binding.videoView.setVideoURI(uri);
            binding.videoView.setMediaController(new MediaController(this));
            binding.videoView.setOnPreparedListener(this);
            binding.videoView.requestFocus();
            binding.videoView.start();
        }else if (type.type().equals("audio")){
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this::onCompletion);
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClose(View view){
        finish();
    }

    public void onPlay(View view){
        if (mediaPlayer != null){
            mediaPlayer.start();
            binding.setPlaying(true);
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer){
        binding.setPlaying(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        binding.setReady(true);
        binding.setPlaying(true);
    }
}