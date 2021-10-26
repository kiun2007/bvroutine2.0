package kiun.com.bvroutine.media;


/**
 * 媒体状态.
 */
public enum MediaState {

    /**
     * 什么都没有发生
     */
    Nothing("准备"),

    /**
     * 准备就绪.
     */
    Ready("准备就绪"),

    /**
     * 录制中.
     */
    Recording("录制中"),

    /**
     * 录制完成.
     */
    Recorded("录制完成"),

    /**
     * 上传文件中
     */
    Uploading("文件上传中"),

    /**
     * 上传完成.
     */
    Uploaded("文件上传完成"),

    /**
     * 播放中.
     */
    Playing("播放中"),

    /**
     * 暂停
     */
    Suspend("暂停播放"),
    /**
     * 停止播放.
     */
    Stop("播放结束");

    private String defaultTip;

    MediaState(String defaultTip){
        this.defaultTip = defaultTip;
    }

    /**
     * 获取默认提示.
     * @return
     */
    public String getTip() {
        return defaultTip;
    }
}
