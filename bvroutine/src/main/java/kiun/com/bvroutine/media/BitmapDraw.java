package kiun.com.bvroutine.media;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public final class BitmapDraw {

    Bitmap bitmap;
    Canvas canvas;
    int textSize;
    Rect lastRect;

    public BitmapDraw(int width, int height, int color){

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(color);
    }

    public BitmapDraw setTextSize(int textSize){
        this.textSize = textSize;
        return this;
    }

    public void drawText(String text, int color){

        Paint p = new Paint();
        p.setColor(color);
        p.setTextSize(textSize);
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        float width = p.measureText(text);
        float x = (bitmap.getWidth() - width) / 2;

        canvas.drawText(text, x, lastRect.bottom + p.getTextSize(), p);
    }

    public void drawIcon(Drawable drawable){

        int left = (bitmap.getWidth() - drawable.getIntrinsicWidth()) / 2;
        int top = (bitmap.getHeight() - (drawable.getIntrinsicHeight() + textSize)) / 2;

        lastRect = new Rect(left, top, left + drawable.getIntrinsicWidth(), top + drawable.getIntrinsicHeight());
        drawable.setBounds(lastRect);
        drawable.draw(canvas);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
