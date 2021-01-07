package kiun.com.bvroutine.views.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class CenterImageSpan extends ImageSpan {

    public CenterImageSpan(Context context, @DrawableRes int resourceId) {
        super(context, resourceId);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {

        Drawable b = getDrawable();
        canvas.save();

        int transY = (bottom - top - b.getBounds().height()) / 2;
        canvas.translate(x, top + transY);
        b.draw(canvas);
        canvas.restore();
    }
}
