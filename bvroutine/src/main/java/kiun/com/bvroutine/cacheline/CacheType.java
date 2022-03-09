package kiun.com.bvroutine.cacheline;

/**
 * 缓存类型.
 */
public enum CacheType{
    /**
     * 没有缓存.
     */
    CacheNone,

    /**
     * 下行缓存.
     */
    CacheDownLoad,

    /**
     * 上行缓存.
     */
    CacheUpLoad
}