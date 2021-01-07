package kiun.com.bvroutine.views.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import kiun.com.bvroutine.interfaces.PagerHandlerGeter;
import kiun.com.bvroutine.interfaces.PagerHandlerSeter;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class PagerFragmentAdapter extends FragmentPagerAdapter {

    public final static String INDEX = "PAGER_FRAGMENT_INDEX";
    public final static String EXTRA = "PAGER_FRAGMENT_EXTRA";

    private Class<? extends Fragment>[] fragmentClz;
    private String[] arguments = null;
    private PagerHandlerGeter geter;
    private Map<Class<? extends Fragment>,SetCaller<? extends Fragment>> listeners = new HashMap<>();
    private Fragment[] allFragments;
    private int limitPager = 1;

    public PagerFragmentAdapter(FragmentManager fm, int limitPager, Class<? extends Fragment>... fragments) {
        super(fm);
        this.limitPager = limitPager;
        fm.registerFragmentLifecycleCallbacks(new FragmentLifecycle(), false);
        fragmentClz = fragments;
        allFragments = new Fragment[fragments.length];
    }

    public PagerFragmentAdapter(FragmentManager fm, Class<? extends Fragment>... fragments) {
        this(fm,1, fragments);
    }

    public int getLimitPager() {
        return limitPager;
    }

    public void setLimitPager(int limitPager) {
        this.limitPager = limitPager;
    }

    private class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {

            if (listeners.size() > 0){
                SetCaller<Fragment> listener = (SetCaller<Fragment>) listeners.get(f.getClass());
                if (listener != null){
                    listener.call(f);
                }
            }

            if (f instanceof PagerHandlerSeter){
                if (geter != null){
                    ((PagerHandlerSeter) f).setPagerHandler(geter.getPagerHandler());
                }
            }
        }
    }

    public void initArguments(String ...args){
        arguments = args;
    }

    public<F extends Fragment> void addCreateListener(Class<F> clz, SetCaller<F> listener){
        if(listeners.containsKey(clz)){
            return;
        }
        listeners.put(clz, listener);
    }

    public<T extends Fragment> T getFragmentItem(int position){
        return (T) allFragments[position];
    }

    public void setHandlerGeter(PagerHandlerGeter geter){
        this.geter = geter;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < fragmentClz.length){
            try {
                Fragment fragment = fragmentClz[position].newInstance();
                Bundle bundle = new Bundle();
                bundle.putInt(INDEX, position);
                if (arguments != null && position < arguments.length){
                    bundle.putString(EXTRA, arguments[position]);
                }
                fragment.setArguments(bundle);
                allFragments[position] = fragment;
                return fragment;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragmentClz.length;
    }
}
