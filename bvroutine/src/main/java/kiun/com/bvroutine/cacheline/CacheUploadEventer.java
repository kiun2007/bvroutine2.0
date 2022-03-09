package kiun.com.bvroutine.cacheline;


import kiun.com.bvroutine.cacheline.data.JSONExtractor;
import kiun.com.bvroutine.cacheline.data.UploadManager;
import kiun.com.bvroutine.cacheline.data.beans.UploadObject;

public interface CacheUploadEventer {

    /**
     * 上传每一条数据成功或失败后触发方法, 该操作是线程不安全的.
     * @param uploadObject 被执行上传操作的对象.
     * @param progress 当前进度.
     * @param count 全部操作数量.
     * @param extractor 服务器返回的结果提取器.
     * @param manager 上传操作对象.
     * @return 是否继续上传操作.
     */
    boolean onUploadProgress(UploadObject uploadObject, int progress, int count, JSONExtractor extractor, UploadManager manager);

    /**
     * 上传操作被强制终止后的最后一个操作对象.
     * @param uploadObject 被执行上传操作的对象.
     * @param isUploadComplete 上传是否已完成.
     * @param manager 上传操作对象.
     */
    void onUploadStop(UploadObject uploadObject, boolean isUploadComplete, UploadManager manager);

    /**
     * 上传前的准备.
     * @param uploadObject
     * @param manager
     */
    void beforeUpload(UploadObject uploadObject, UploadManager manager);
}
