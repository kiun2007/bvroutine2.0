package kiun.com.bvroutine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public class MapContainer extends RelativeLayout implements TypedView {

    @AttrBind(resource = true, def = -1)
    private int scrollView;

    public MapContainer(Context context) {
        super(context);
    }

    public MapContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (scrollView > 0){
            ScrollView scroll = findParentById(scrollView);
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                scroll.requestDisallowInterceptTouchEvent(false);
            } else {
                scroll.requestDisallowInterceptTouchEvent(true);
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.MapContainer;
    }

    @Override
    public void initView() {
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }
}