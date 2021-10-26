package kiun.com.bvroutine.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

/**
 * 文 件 名: NavigationRow
 * 作 者: 刘春杰
 * 创建日期: 2020/5/20 22:26
 * 说明: 导航条（行）控件
 */
public class NavigationRow extends LinearLayout implements TypedBindView {

    @AttrBind
    Drawable icon;

    @AttrBind
    String title;

    @ViewBind
    TextView titleTextView;

    @ViewBind
    ImageView iconImageView;

    public NavigationRow(Context context) {
        super(context);
    }

    public NavigationRow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public NavigationRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.NavigationRow;
    }

    @Override
    public void initView() {

        titleTextView.setText(title);
        iconImageView.setImageDrawable(icon);
    }

    public void setTitle(String title){
        titleTextView.setText(title);
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public int layoutId() {
        return R.layout.layout_navigation_row;
    }
}