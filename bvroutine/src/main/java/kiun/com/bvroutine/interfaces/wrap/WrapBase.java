package kiun.com.bvroutine.interfaces.wrap;

public interface WrapBase {

    /**
     * 状态消息.
     * @return
     */
    String getMsg();

    /**
     * 是否成功.
     * @return
     */
    boolean isSuccess();

    /**
     * 是否登陆超时。
     * @return
     */
    boolean isLogout();
}
