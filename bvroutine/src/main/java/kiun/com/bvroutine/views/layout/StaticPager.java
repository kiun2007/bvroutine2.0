package kiun.com.bvroutine.views.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ClassUtil;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.adapter.PagerFragmentAdapter;

public class StaticPager extends ViewPager implements TypedView {

    @AttrBind
    private String[] pagesClzName;

    @AttrBind
    private BVBaseActivity activity;

    @AttrBind(boolDef = false)
    private boolean noScroll = false;

    @AttrBind
    private int maxCachePageSize = 3;

    private PagerFragmentAdapter pagerFragmentAdapter;

    private FragmentManager fragmentManager;

    public StaticPager(@NonNull Context context) {
        super(context);
    }

    public StaticPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (noScroll) return false;
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll) return false;
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.StaticPager;
    }

    @Override
    public void initView() {

        if (pagesClzName != null && activity != null){

            Class<? extends Fragment> fragmentClzs[] = new Class[pagesClzName.length];
            for (int i = 0; i < pagesClzName.length; i++) {
                String pageClzName = pagesClzName[i];
                fragmentClzs[i] = ClassUtil.forName(pageClzName, Fragment.class);
            }

            if (getId() == -1){
                setId(View.generateViewId());
            }
            pagerFragmentAdapter = new PagerFragmentAdapter(activity.getSupportFragmentManager(), fragmentClzs);
            setAdapter(pagerFragmentAdapter);
        }

        setOffscreenPageLimit(maxCachePageSize);
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
