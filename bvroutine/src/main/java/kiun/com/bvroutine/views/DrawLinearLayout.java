package kiun.com.bvroutine.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public class DrawLinearLayout extends LinearLayout implements TypedView {

    @AttrBind(dimension = true)
    int radius = 5;

    @AttrBind
    int paintColor = 0xFF3699FF;

    @AttrBind
    int gradientColor = -1;

    @AttrBind
    int drawPadding = -1;

    @AttrBind
    int paintStyle = 1;

    @AttrBind
    int stroke = 1;

    @AttrBind
    boolean childDraw = false;

    @AttrBind
    boolean shadow = false;

    public DrawLinearLayout(Context context) {
        super(context);
    }

    public DrawLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public DrawLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    private Paint getBorder(){
        Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setStyle(Paint.Style.values()[paintStyle]);
        paint.setStrokeWidth(stroke);
        return paint;
    }

    private Paint getShadow(){
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(radius, 0, ViewUtil.dp2px(getContext(), 4), 0x18000000);
        paint.setPathEffect(new PathEffect());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint borderPaint = getBorder();

        float left = drawPadding == -1 ? (borderPaint.getStrokeWidth() / 2) : drawPadding;
        RectF rectF = new RectF(left, left,this.getWidth() - 2 * left, this.getHeight() - 2 * left);

        if (shadow){
            canvas.drawRoundRect(rectF, radius, radius, getShadow());
        }

        if (gradientColor != -1){
            Shader shader = new LinearGradient(rectF.right/2, rectF.bottom, rectF.right/2, rectF.top, new int[]{paintColor, gradientColor}, null, Shader.TileMode.REPEAT);
            borderPaint.setShader(shader);
        }
        canvas.drawRoundRect(rectF, radius, radius, borderPaint);

        if(childDraw){
            for (int i = 1; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (this.getOrientation() == HORIZONTAL){
                    canvas.drawLine(view.getX(), left, view.getX(), this.getHeight() - left, borderPaint);
                }else{
                    canvas.drawLine(left, view.getY(), this.getWidth() - left, view.getY(), borderPaint);
                }
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        setClipToOutline(true);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, getWidth(), getHeight(), radius);
            }
        });
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.DrawLinearLayout;
    }

    @Override
    public void initView() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
