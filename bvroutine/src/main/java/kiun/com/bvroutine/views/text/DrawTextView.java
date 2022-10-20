package kiun.com.bvroutine.views.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public class DrawTextView extends AppCompatTextView implements TypedView {

    @AttrBind
    private String[] labels;

    /**
     * 颜料盒.
     */
    @AttrBind
    private int[] colors;

    /**
     * 固定颜色.
     */
    @AttrBind
    private int labelColor;

    @AttrBind
    private int labelSize;

    @AttrBind
    private Drawable labelDrawable;

    private int endPadding = 10;

    /**
     * 颜色集合作用于背景或者文字
     */
    @AttrBind(boolDef = true)
    private boolean inBackground;

    private Paint paint = new Paint();
    private int index = 0;

    public DrawTextView(Context context) {
        super(context);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (labels == null || index >= labels.length || index < 0){
            return;
        }

        int stateColor = colors != null ? colors[index] : 0;
        String drawText = labels[index];
        boolean isWithDrawable = labelDrawable != null;

        Layout layout = getLayout();
        int last = layout.getLineCount() - 1;

        float drawWidth = isWithDrawable ? labelDrawable.getIntrinsicWidth() : paint.measureText(drawText);
        float drawHeight = isWithDrawable ? labelDrawable.getIntrinsicHeight() : paint.getTextSize();

        float right = layout.getLineRight(last) + endPadding;
        float baseLine = (layout.getLineBottom(last) + layout.getLineTop(last)) / 2;
        float top = baseLine - drawHeight / 2;
        float bottom = layout.getLineBottom(last);

        if (right + drawWidth > getWidth()){
            right = layout.getLineLeft(last);
            top = bottom + endPadding;

            if (top >= getHeight()){
                setHeight((int) (top + drawHeight));
            }
        }

        float offset = getPaddingLeft();

        if (getCompoundDrawables()[0] != null){
            offset += getCompoundDrawables()[0].getIntrinsicWidth() + getCompoundDrawablePadding();
        }

        right += offset;

        Rect rect = new Rect((int) right, (int)top, (int)(right + drawWidth), (int)(top + drawHeight));

        if (isWithDrawable){

            if (inBackground){
                labelDrawable.setTint(stateColor);
            }
            labelDrawable.setBounds(rect);
            labelDrawable.draw(canvas);
        }

        if (!inBackground){
            paint.setColor(stateColor);
        }

        float textWidth = paint.measureText(drawText);
        canvas.save();
        canvas.translate(rect.centerX() - textWidth / 2, rect.top);
        canvas.drawText(drawText, 0,paint.getTextSize() + (rect.height() - paint.getTextSize())/2 - 2, paint);
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public void setIndex(int index) {
        this.index = index;
        invalidate();
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.DrawTextView;
    }

    @Override
    public void initView() {
        paint.setTextSize(labelSize);

        if (inBackground){
            paint.setColor(labelColor);
        }else{
            if (labelDrawable != null){
                labelDrawable.setTint(labelColor);
            }
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
