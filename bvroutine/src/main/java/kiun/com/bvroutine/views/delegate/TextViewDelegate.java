package kiun.com.bvroutine.views.delegate;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.utils.image.DrawableUtil;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class TextViewDelegate extends ViewDelegate<TextView> {

    @AttrBind
    private Drawable drawableTop;

    @AttrBind
    private Drawable drawableBottom;

    @AttrBind
    private Drawable drawableLeft;

    @AttrBind
    private Drawable drawableRight;

    @AttrBind
    private int drawablePadding;

    @AttrBind
    private Integer textSize;

    @AttrBind
    private Integer textColor;

    @AttrBind
    private String text;

    public TextViewDelegate(@NonNull TextView view, AttributeSet attributeSet) {
        super(view, attributeSet);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.TextViewDelegate;
    }

    @Override
    public void initView() {
        DrawableUtil.setTextDrawable(view, DrawableUtil.LEFT, drawableLeft);
        DrawableUtil.setTextDrawable(view, DrawableUtil.BOTTOM, drawableBottom);
        DrawableUtil.setTextDrawable(view, DrawableUtil.TOP, drawableTop);
        DrawableUtil.setTextDrawable(view, DrawableUtil.RIGHT, drawableRight);
        view.setText(text);

        if (textSize != null){
            view.setTextSize(COMPLEX_UNIT_PX, textSize);
        }

        if (textColor != null){
            view.setTextColor(textColor);
        }
    }
}
