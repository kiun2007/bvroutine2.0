package kiun.com.bvroutine.media;
import android.view.TextureView;

/**
 * 录像对象.
 */
public interface VideoRecorder extends Recorder{

    /**
     * 播放录制好的视频.
     */
    void replay();

    /**
     * 设置Texture.
     * @param textureView
     */
    void setTextureView(TextureView textureView);

    /**
     * 切换摄像头.
     */
    void flip();

    /**
     * 生命周期恢复.
     */
    void onResume();

    /**
     * 生命周期结束.
     */
    void onStop();
}
