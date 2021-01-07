package kiun.com.bvroutine.views;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ScaleLayout extends LinearLayout {

    float ratio = 1;

    public ScaleLayout(Context context) {
        super(context);
    }

    public ScaleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (ratio != 0) {
            float height = width * ratio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @BindingAdapter("ratio")
    public static void setRatio(ScaleLayout scaleLayout, float ratio){
        scaleLayout.ratio = ratio;
    }
}
