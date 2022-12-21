package kiun.com.bvroutine.base.binding;

import android.widget.GridView;

import androidx.databinding.BindingAdapter;

import java.util.List;

import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.interfaces.callers.PagerCaller;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.views.TabViewLayout;

public class TabViewLayoutBinding {

    @BindingAdapter("android:netBlock")
    public static void setNetBlock(TabViewLayout view, PagerCaller caller){

    }

    @BindingAdapter(value = {"android:netBlock","android:firstItem"})
    public static void setNetBlock(TabViewLayout view, PagerCaller caller, String firstItem){
        if (view.getContext() instanceof RequestBVActivity){
            RequestBVActivity activity = (RequestBVActivity) view.getContext();

            activity.addRequest(()-> RetrofitUtil.unpackWrap(null, caller.get(null).execute()),
            v -> {
                if (v instanceof List){
                    ((List) v).add(0, firstItem);
                    view.setTabArray((List<?>) v);
                }
            });
        }
    }

    @BindingAdapter("tabArray")
    public static void setTabArray(TabViewLayout view, List<?> array){
        view.setTabArray(array);
    }
}
