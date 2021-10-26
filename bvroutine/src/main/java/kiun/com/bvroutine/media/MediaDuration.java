package kiun.com.bvroutine.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public final class MediaDuration {

    int duration;

    public MediaDuration(Context context, Uri uri) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepare();
        duration = mediaPlayer.getDuration() / 1000;
        mediaPlayer.release();
    }

    public String getFormat(){

        int min = duration / 60;
        int second = duration % 60;

        return String.format("%02d:%02d", min, second);
    }
}
