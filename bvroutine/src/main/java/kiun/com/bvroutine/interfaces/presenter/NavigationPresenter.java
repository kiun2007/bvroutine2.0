package kiun.com.bvroutine.interfaces.presenter;

import android.os.Parcelable;
import androidx.fragment.app.FragmentManager;
import kiun.com.bvroutine.base.NavigationBaseFragment;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.interfaces.view.NavigationView;

/**
 * 导航事务接口.
 */
public interface NavigationPresenter {

    /**
     * 初始化导航事务.
     * @param view
     * @param fragmentManager
     */
    void initNavigation(NavigationView view, FragmentManager fragmentManager);

    /**
     * 设置根Fragment.
     * @param clz
     * @param value
     */
    void setRoot(Class<? extends NavigationBaseFragment> clz, Parcelable value);

    /**
     * 返回上个界面.
     */
    void backNavi();

    /**
     * 立刻返回到上个页面,如果已经是第一个页面返回false.
     * @return
     */
    boolean backNaviImmediate();

    /**
     * 返回上个界面.
     */
    void backNavi(Class<? extends NavigationBaseFragment> clz);

    /**
     * 返回第一个界面.
     */
    void gotoRoot();

    /**
     * 跳到指定的界面.
     * @param clz 页面类.
     */
    void gotoNavi(Class<? extends NavigationBaseFragment> clz, Parcelable value);

    /**
     * 跳转到下一个页面.
     * @param value
     */
    void next(BaseBean value);

    /**
     * 配置动画.
     * @param enter
     * @param exit
     */
    void configAnimation(int enter, int exit, int popEnter, int popExit);
}
