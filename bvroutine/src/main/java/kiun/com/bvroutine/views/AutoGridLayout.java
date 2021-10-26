package kiun.com.bvroutine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public class AutoGridLayout extends GridLayout implements TypedView, View.OnLayoutChangeListener {

    @AttrBind(def = 0, dimension = true)
    int itemMargin;

    public AutoGridLayout(Context context) {
        super(context);
    }

    public AutoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public AutoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    private final LayoutParams getChildParams(View v){
        return (LayoutParams) v.getLayoutParams();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        for (int i = 0, N = getChildCount(); i < N; i++) {
            View c = getChildAt(i);
            c.addOnLayoutChangeListener(this);
            LayoutParams layoutParams = getChildParams(c);
            layoutParams.width = 100;
            c.setLayoutParams(layoutParams);
        }

        super.onLayout(changed, left, top, right, bottom);

//        if (getColumnCount() < 1 || getChildCount() == 0){
//            return;
//        }
//
//        //计算每一列最多允许多宽.
//        int columnMax = (getWidth() - ((getColumnCount() - 1) * itemMargin)) / getColumnCount();
//
//        for (int i = 0, N = getChildCount(); i < N; i++) {
//            View media_menu = getChildAt(i);
//
//            int outWidth = media_menu.getWidth() - columnMax;
//
//            if (outWidth > 0){
//                outWidth /= 2;
//
//                int vLeft = media_menu.getLeft() + outWidth;
//                int vRight = media_menu.getRight() - outWidth;
//                media_menu.layout(vLeft, media_menu.getTop(), vRight, media_menu.getBottom());
//            }
//        }
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.AutoGridLayout;
    }

    @Override
    public void initView() {
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        int a = 0;
    }
}
