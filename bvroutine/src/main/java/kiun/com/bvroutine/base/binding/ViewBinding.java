package kiun.com.bvroutine.base.binding;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.GetBooleanCaller;
import kiun.com.bvroutine.utils.ViewUtil;

public class ViewBinding {

    private static class ScaleOnLayoutListener implements View.OnLayoutChangeListener {

        private float scale;

        private ScaleOnLayoutListener(float scale) {
            this.scale = scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            int width = right - left;
            int height = (int) (scale * width);

            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            layoutParams.height = height;
            v.setLayoutParams(layoutParams);
        }
    }

    @BindingAdapter("android:scaleHeight")
    public static void setScaleHeight(View view, float scale){

        ScaleOnLayoutListener onLayoutListener = (ScaleOnLayoutListener) view.getTag(R.id.tagOnLayout);
        if (onLayoutListener == null){
            onLayoutListener = new ScaleOnLayoutListener(scale);
            view.addOnLayoutChangeListener(onLayoutListener);
        }
        onLayoutListener.setScale(scale);
        view.setTag(R.id.tagOnLayout, onLayoutListener);
    }

    @BindingAdapter("android:verifyField")
    public static void setVerifyField(View view, String fieldName){
        view.setTag(R.id.tagVerifyField, fieldName);
    }

    @BindingAdapter("android:show")
    public static void setShow(View view, boolean isShow){
        view.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"android:margin", "android:marginTop", "android:marginLeft", "android:marginBottom", "android:marginRight"}, requireAll = false)
    public static void setMargin(View view, Float margin, Float marginTop, Float marginLeft, Float marginBottom, Float marginRight){

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (marginTop == null){
                marginTop = margin;
            }

            if (marginLeft == null){
                marginLeft = margin;
            }

            if (marginBottom == null){
                marginBottom = margin;
            }

            if (marginRight == null){
                marginRight = margin;
            }

            if (marginTop != null){
                layoutParams.topMargin = marginTop.intValue();
            }

            if (marginLeft != null){
                layoutParams.leftMargin = marginLeft.intValue();
            }

            if (marginBottom != null){
                layoutParams.bottomMargin = marginBottom.intValue();
            }

            if (marginRight != null){
                layoutParams.rightMargin = marginRight.intValue();
            }
        }
    }

    @BindingAdapter("android:changer")
    public static void setViewChanged(View view, GetBooleanCaller booleanCaller){
        view.addOnAttachStateChangeListener(ViewUtil.createNotice(booleanCaller));
    }

    private static Object getTintColor(ColorStateList colorStateList, int index, boolean isTextView){

        if (colorStateList == null){
            return null;
        }
        try {
            Method method = colorStateList.getClass().getMethod("getColors");
            int[] colors = (int[]) method.invoke(colorStateList);

            if (colors != null && index >=0 && index < colors.length){
                if (isTextView){
                    return colors[index];
                }
                return ColorStateList.valueOf(colors[index]);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @BindingAdapter("android:bgTintIndex")
    public static void setBgTintIndex(View view, int index){

        boolean isTextView = view instanceof TextView;

        ColorStateList colorStateList = (ColorStateList) view.getTag(R.id.tagTint);
        if (colorStateList == null){
            colorStateList = isTextView? view.getForegroundTintList() : view.getBackgroundTintList();
            view.setTag(R.id.tagTint, colorStateList);
        }

        Object color = getTintColor(colorStateList, index, isTextView);

        if (color instanceof ColorStateList){
            view.setBackgroundTintList((ColorStateList) color);
        }else if (color instanceof Integer){
            TextView textView = (TextView) view;
            textView.setTextColor((Integer) color);
        }
    }

    @BindingAdapter("android:init")
    public static void setInit(View view, CallBack callBack){
        callBack.call();
    }
}
