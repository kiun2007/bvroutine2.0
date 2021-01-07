package kiun.com.bvroutine.base.binding;

import androidx.databinding.BindingAdapter;

import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.views.ShortListView;

public class ShortListViewBinding {

    @BindingAdapter({"android:listHandler","android:val"})
    public static void setListHandlerAndVal(ShortListView shortListView, ListHandler listHandler, Object value){
        setListHandler(shortListView, listHandler);
        ActionBinding.setValue(shortListView, value);
    }

    @BindingAdapter("android:listHandler")
    public static void setListHandler(ShortListView shortListView, ListHandler listHandler) {
        shortListView.setListHandler(listHandler);
    }
}
