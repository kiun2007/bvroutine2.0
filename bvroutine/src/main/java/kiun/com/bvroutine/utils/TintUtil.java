package kiun.com.bvroutine.utils;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TintUtil {

    public static Drawable getTintListDrawable(@NonNull Drawable drawable, @Nullable ColorStateList tint, @Nullable PorterDuff.Mode tintMode) {

        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            Drawable d = null;
            try {
                Class<?> dclClazz = Class.forName("android.support.v4.graphics.drawable.DrawableCompatLollipop");
                Method method = dclClazz.getMethod("wrapForTinting", Drawable.class);
                d = (Drawable) method.invoke(null, drawable);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (d != null) {
                drawable = d;
            }
        } else {
            drawable = DrawableCompat.wrap(drawable);
        }
        if (tintMode != null) {
            DrawableCompat.setTintMode(drawable, tintMode);
        }
        DrawableCompat.setTintList(drawable, tint);
        return drawable;
    }
}
