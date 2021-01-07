package kiun.com.bvroutine.utils.view;

import android.view.View;
import android.view.ViewGroup;

import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class ViewGroupUtil {

    public static void mapViewGroup(ViewGroup group, SetCaller<View> caller) {
        caller.call(group);

        for(int var2 = 0; var2 < group.getChildCount(); ++var2) {
            View var3 = group.getChildAt(var2);
            caller.call(var3);
            if (var3 instanceof ViewGroup) {
                mapViewGroup((ViewGroup)var3, caller);
            }
        }
    }

    public static void setEnabled(ViewGroup group, boolean value) {
        mapViewGroup(group, v -> v.setEnabled(value));
    }
}
