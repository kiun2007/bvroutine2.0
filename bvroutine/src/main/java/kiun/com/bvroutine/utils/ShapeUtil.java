package kiun.com.bvroutine.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;

public class ShapeUtil {

    /**
     * 获得一个指定填充色，边框宽度、颜色的圆角矩形drawable。
     * Android 中 在xml中写的"shape"标签映射对象就是GradientDrawable。
     * 通过设置solidColors 和strokeColors 可实现选择器的效果
     *
     * @param solidColors  填充色
     * @param strokeColors 描边色
     * @param strokeWidth  描边线宽度
     * @param dashWidth    虚线（破折线）的长度（以像素为单位）
     * @param dashGap      虚线（破折线）间距，当dashGap=0dp时，为实线
     * @param radius       圆角角度
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(ColorStateList solidColors, ColorStateList strokeColors,
                                            int strokeWidth, float dashWidth, float dashGap, float radius) {

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gradientDrawable.setColor(solidColors);
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
            gradientDrawable.setStroke(strokeWidth, strokeColors, dashWidth, dashGap);
        } else {
            gradientDrawable.setColor(solidColors.getDefaultColor());
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
            gradientDrawable.setStroke(strokeWidth, strokeColors.getDefaultColor(), dashWidth, dashGap);
        }
        return gradientDrawable;
    }
    /**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @param dashWidth   虚线（破折线）宽度
     * @param dashGap     虚线（破折线）间距，当dashGap=0dp时，为实线
     * @param radius      圆角角度
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor, @ColorInt int strokeColor, int strokeWidth,
                                            float dashWidth, float dashGap, float radius) {
        return getShapeDrawable(ColorStateList.valueOf(solidColor), ColorStateList.valueOf(strokeColor), strokeWidth, dashWidth, dashGap, radius);
    }

    /**
     * 获得一个选择器Drawable.
     * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
     *
     * @param defaultDrawable 默认时显示的Drawable
     * @param pressedDrawable 按下时显示的Drawable
     * @return 选择器Drawable
     */
    public static StateListDrawable getSelectorDrawable(Drawable defaultDrawable, Drawable pressedDrawable) {
        if (defaultDrawable == null) return null;
        if (pressedDrawable == null) pressedDrawable = defaultDrawable;
        int[][] state = {{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}};
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(state[0], defaultDrawable);
        stateListDrawable.addState(state[1], pressedDrawable);
        return stateListDrawable;
    }

}
