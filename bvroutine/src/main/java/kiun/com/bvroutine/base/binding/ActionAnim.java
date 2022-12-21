package kiun.com.bvroutine.base.binding;

/**
 * 动作动画
 */
public interface ActionAnim {

    /**
     * 开始
     */
    void begin();

    /**
     * 清除图标.
     */
    void clear();

    /**
     * 完成.
     */
    void complete();

    /**
     * 网络反馈结果错误.
     */
    void error();

    /**
     * 本地验证错误.
     */
    void failed();
}
