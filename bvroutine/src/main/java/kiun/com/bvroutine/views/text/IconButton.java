package kiun.com.bvroutine.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.ViewBind;
import kiun.com.bvroutine.interfaces.view.TypedBindView;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.delegate.TextViewDelegate;

public class IconButton extends LinearLayout implements TypedBindView {

    @AttrBind
    private Drawable icon;

    @AttrBind
    private String title;

    @ViewBind
    TextView centerTextView;

    TextViewDelegate textViewDelegate;

    public IconButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
        textViewDelegate = new TextViewDelegate(centerTextView, attrs);
    }

    public IconButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
        textViewDelegate = new TextViewDelegate(centerTextView, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.IconButton;
    }

    @Override
    public void initView() {
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public int layoutId() {
        return R.layout.layout_icon_button;
    }
}
