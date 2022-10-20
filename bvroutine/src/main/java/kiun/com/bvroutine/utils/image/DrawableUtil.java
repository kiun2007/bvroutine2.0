package kiun.com.bvroutine.utils.image;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import kiun.com.bvroutine.R;

public class DrawableUtil {

    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId){

        Drawable drawable = VectorDrawableCompat.create(context.getResources(), drawableId, context.getTheme());
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    private static void setClosely(Drawable[] drawables, TextView textView){

//        float textSize = textView.getPaint().measureText((String) textView.getText());
//        int centerX = (int) (textSize - textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight()) / 2;
//        int centerY = (int) (textView.getPaint().getTextSize() - textView.getHeight() - textView.getPaddingBottom() - textView.getPaddingTop()) / 2;
//
//        if ((textView.getGravity() & CENTER_VERTICAL) == CENTER_VERTICAL){
//
//            if (drawables[1] != null){
//                drawables[1].setBounds(0, centerY - drawables[1].getIntrinsicHeight(),
//                                        drawables[1].getIntrinsicWidth(), centerY);
//            }
//
//            if (drawables[3] != null){
//                drawables[3].setBounds(0, centerY - drawables[1].getIntrinsicHeight(),
//                        drawables[1].getIntrinsicWidth(), centerY);
//            }
//        }
//
//        if ((textView.getGravity() & CENTER_HORIZONTAL) == CENTER_HORIZONTAL){
//
//        }
//        drawable.setBounds(offset, 0, size + offset, size);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId, Rect rect){
        Drawable drawable = VectorDrawableCompat.create(context.getResources(), drawableId, context.getTheme());
        if (drawable != null){
            drawable.setBounds(rect);
        }
        return drawable;
    }

    public static void setTintColor(TextView view, int color){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setCompoundDrawableTintList(ColorStateList.valueOf(color));
        }else{
            for (int i = 0; i < view.getCompoundDrawables().length; i++) {
                Drawable drawable = view.getCompoundDrawables()[0];
                if (drawable != null){
                    drawable.setTint(color);
                }
            }
        }
    }
    public static void setTextDrawable(TextView view, int index, Drawable drawable){
        setTextDrawable(view, index, drawable, false);
    }

    public static void setTextDrawable(TextView view, int index, Drawable drawable, boolean isClosely){
        Drawable[] drawables = view.getCompoundDrawables();
        if (drawable != null){
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        drawables[index] = drawable;

        if (isClosely){
            setClosely(drawables, view);
        }
        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public static void setLeftDrawable(TextView view, @DrawableRes int drawableId){
        Drawable drawable = getDrawable(view.getContext(), drawableId);
        setTextDrawable(view, LEFT, drawable, false);
    }

    public static void setRightDrawable(TextView view, @DrawableRes int drawableId){
        Drawable drawable = getDrawable(view.getContext(), drawableId);
        setTextDrawable(view, RIGHT, drawable, false);
    }

    public static void setTopDrawable(TextView view, @DrawableRes int drawableId){
        Drawable drawable = getDrawable(view.getContext(), drawableId);
        setTextDrawable(view, TOP, drawable, false);
    }

    public static void setBottomDrawable(TextView view, @DrawableRes int drawableId){
        Drawable drawable = getDrawable(view.getContext(), drawableId);
        setTextDrawable(view, BOTTOM, drawable, false);
    }

    public static Drawable anim(Drawable drawable, boolean isRun){
        if (drawable instanceof AnimationDrawable){
            AnimationDrawable animation = (AnimationDrawable) drawable;
            if (isRun){
                animation.run();
                animation.start();
            }else{
                animation.stop();
            }
        }
        return drawable;
    }
}
