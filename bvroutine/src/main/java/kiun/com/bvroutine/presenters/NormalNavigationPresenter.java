package kiun.com.bvroutine.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import kiun.com.bvroutine.base.NavigationBaseFragment;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.handlers.ActionBarHandler;
import kiun.com.bvroutine.interfaces.handler.FragmentNavigationHandler;
import kiun.com.bvroutine.interfaces.presenter.NavigationPresenter;
import kiun.com.bvroutine.interfaces.view.NavigationView;

public class NormalNavigationPresenter implements NavigationPresenter, FragmentManager.OnBackStackChangedListener {

    public static final String ARGS_VALUE = "VALUE";

    int enterAnimation = -1, exitAnimation = -1, popEnterAnimation = -1, popExitAnimation = -1;
    int contentViewId;
    FragmentManager mFragmentManager;
    NavigationView view;
    boolean replace = false;
    Class<? extends NavigationBaseFragment> rootFragment = null;
    FragmentNavigationHandler handler = new FragmentNavigationHandler(this);
    NavigationActivityHandler barHandler = new NavigationActivityHandler();

    public NormalNavigationPresenter(){}
    public NormalNavigationPresenter(boolean replace){
        this.replace = replace;
    }

    FragmentManager.FragmentLifecycleCallbacks lifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            if (f instanceof NavigationBaseFragment){
                NavigationBaseFragment fragment = (NavigationBaseFragment) f;
                fragment.setNavigationHandler(handler);
                fragment.setBarHandler(barHandler);
                handler.setCurrent(fragment);
            }
        }
    };

    @Override
    public void onBackStackChanged() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        Fragment fragment = fragments.get(fragments.size()-1);
        if (fragment instanceof NavigationBaseFragment){
            handler.setCurrent((NavigationBaseFragment) fragment);
        }
    }

    public class NavigationActivityHandler extends ActionBarHandler {

        @Override
        public void onClick(Context context, int tag, View view) {
            if (tag == ActionBarHandler.TAG_BACK_BUTTON_ITEM){
                backNavi();
            }
        }
    }

    @Override
    public void initNavigation(@NonNull NavigationView view, FragmentManager fragmentManager) {
        this.view = view;
        contentViewId = view.getContentViewId();
        mFragmentManager = fragmentManager;
        mFragmentManager.registerFragmentLifecycleCallbacks(lifecycleCallbacks, false);
        mFragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    public void setRoot(Class<? extends NavigationBaseFragment> clz, Parcelable value) {
        commitFragment(clz, value, true);
    }

    @Override
    public void backNavi() {
        if (!backNaviImmediate()){
            view.backIsRoot();
        }
    }

    @Override
    public boolean backNaviImmediate() {
        return mFragmentManager.popBackStackImmediate();
    }

    @Override
    public void backNavi(Class<? extends NavigationBaseFragment> clz) {
        mFragmentManager.popBackStack(clz.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void gotoRoot() {
        if (rootFragment != null){
            mFragmentManager.popBackStack(rootFragment.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void commitFragment(Class<? extends NavigationBaseFragment> clz, Parcelable value, boolean isRoot){

        if (isRoot && mFragmentManager.getFragments().size() > 0) return;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        try {
            NavigationBaseFragment fragment = clz.newInstance();

            Bundle bundle = new Bundle();
            if(value != null){
                bundle.putParcelable(ARGS_VALUE, value);
            }

            if (isRoot && rootFragment == null){
                rootFragment = clz;
                bundle.putBoolean(NavigationBaseFragment.ROOT_TAG, true);
                transaction.add(contentViewId, fragment);
            }else{
                if (exitAnimation > 0 && enterAnimation > 0){
                    transaction.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation); //,
                }else{
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                }

                if (replace){
                    transaction.replace(contentViewId, fragment);
                }else{
                    Fragment lastFragment = mFragmentManager.getFragments().get(mFragmentManager.getFragments().size() - 1);
                    transaction.add(contentViewId,fragment);
                    transaction.hide(lastFragment);
                }
                transaction.addToBackStack(clz.getName());
            }
            fragment.setArguments(bundle);
            transaction.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void gotoNavi(Class<? extends NavigationBaseFragment> clz, Parcelable value) {
        commitFragment(clz, value, false);
    }

    @Override
    public void next(BaseBean value) {

    }

    @Override
    public void configAnimation(int enter, int exit, int popEnter, int popExit) {
        enterAnimation = enter; exitAnimation = exit; popEnterAnimation = popEnter; popExitAnimation = popExit;
    }
}
