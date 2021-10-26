package kiun.com.bvroutine.interfaces.handler;

import android.os.Parcelable;

import kiun.com.bvroutine.base.BVBaseFragment;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.NavigationBaseFragment;
import kiun.com.bvroutine.interfaces.presenter.NavigationPresenter;

public class FragmentNavigationHandler extends BaseHandler {

    private NavigationPresenter naviPresenter;
    NavigationBaseFragment current = null;

    public FragmentNavigationHandler(NavigationPresenter presenter){
        naviPresenter = presenter;
    }

    public void setCurrent(NavigationBaseFragment current) {
        this.current = current;
    }

    public void navigationTo(int toIndex){
        navigationTo(toIndex,null);
    }

    public void navigationTo(int toIndex, Parcelable value){

        Class<? extends NavigationBaseFragment>[] fragmentClzs;
        if (current != null && (fragmentClzs = current.getNextFragments()) != null){
            if (toIndex < 0 || toIndex >= fragmentClzs.length){
                return;
            }
            naviPresenter.gotoNavi(fragmentClzs[toIndex], value);
        }
    }
}
