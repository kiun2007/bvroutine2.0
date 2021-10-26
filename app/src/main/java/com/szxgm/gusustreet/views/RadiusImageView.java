package com.szxgm.gusustreet.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.databinding.InverseBindingListener;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.szxgm.gusustreet.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.GlideUtil;
import kiun.com.bvroutine.utils.ViewUtil;

@SuppressLint("AppCompatCustomView")
public class RadiusImageView extends ImageView implements TypedView, ValListener<String> {

    private Paint mPaint;

    @AttrBind(dimension = true)
    private int radiusValue = 0;

    @AttrBind(resource = true, def = -1)
    private int defaultImage;

    @AttrBind(resource = true, def = R.drawable.svg_photo_normal)
    private int photoNormal;

    private float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    public RadiusImageView(Context context) {
        this(context, null);
    }

    public RadiusImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadiusImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        ViewUtil.initTyped(this, attrs);
    }

    public void setSrc(String src){

        if (src != null){
            GlideUtil.into(this, src, false);
        }else{
            if (defaultImage > 0){
                this.setImageResource(defaultImage);
            }
        }
    }

    public void setVal(String val){
        int a = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        Matrix mDrawMatrix = getImageMatrix();
        if (mDrawable == null) {
            return; // couldn't resolve the URI
        }

        if (mDrawable.getIntrinsicWidth() == 0 || mDrawable.getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

        if (mDrawMatrix == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
            mDrawable.draw(canvas);
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (getCropToPadding()) {
                    final int scrollX = getScrollX();
                    final int scrollY = getScrollY();
                    canvas.clipRect(scrollX + getPaddingLeft(), scrollY + getPaddingTop(),
                            scrollX + getRight() - getLeft() - getPaddingRight(),
                            scrollY + getBottom() - getTop() - getPaddingBottom());
                }
            }
            canvas.translate(getPaddingLeft(), getPaddingTop());
            if (radiusValue > 0) {//当为圆形模式的时候
                Bitmap bitmap = drawable2Bitmap(mDrawable);
                mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom()),
                        radiusValue, radiusValue, mPaint);
            } else {
                if (mDrawMatrix != null) {
                    canvas.concat(mDrawMatrix);
                }
                mDrawable.draw(canvas);
            }
            canvas.restoreToCount(saveCount);
        }
    }

    /**
     * drawable转换成bitmap
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //根据传递的scaletype获取matrix对象，设置给bitmap
        Matrix matrix = getImageMatrix();
        if (matrix != null) {
            canvas.concat(matrix);
        }
        drawable.draw(canvas);
        return bitmap;
    }

    public void setRadius(int radius){
        this.radiusValue = radius;
        setRadius(radius,radius,radius,radius);
    }

    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        radiusArray[0] = radiusArray[1] = leftTop;
        radiusArray[2] = radiusArray[3] = rightTop;
        radiusArray[4] = radiusArray[5] = rightBottom;
        radiusArray[6] = radiusArray[7] = leftBottom;
    }


    @Override
    public int[] getStyleableId() {
        return R.styleable.RadiusImageView;
    }

    @Override
    public void initView() {
        if (defaultImage > 0){
            this.setImageResource(defaultImage);
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public String getVal() {
        return null;
    }

    @Override
    public void setListener(InverseBindingListener listener) {
    }
}