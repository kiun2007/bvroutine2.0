package kiun.com.bvroutine.base.binding;

import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.binding.value.AppCompatSpinnerBindConvert;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.utils.SystemUtil;

public class AppCompatSpinnerBinding {

    @BindingAdapter(value = {"android:values", "android:titles", "android:noSelect"}, requireAll = false)
    public static void setSpinnerValues(AppCompatSpinner view, Collection values, Collection titles, String noSelect){

        if(noSelect != null && values != null){
            LinkedList list = new LinkedList();
            list.add(null);
            list.addAll(values);
            values = list;
        }
        view.setTag(R.id.tagSpinnerValues, values);

        BindConvert bindConvert = (BindConvert) view.getTag(R.id.tagBinConvert);
        BindConvert newConvert = new AppCompatSpinnerBindConvert(view);

        if (bindConvert != null){
            bindConvert.replace(newConvert);
        }
        view.setTag(R.id.tagBinConvert, newConvert);

        if (bindConvert instanceof ValueSetter){
            ((ValueSetter) bindConvert).setValues(values);
        }

        if (titles != null){
            if (titles != null && view instanceof AppCompatSpinner) {
                List<String> titleList = new LinkedList<>();
                if (noSelect != null){
                    titleList.add(noSelect);
                }
                for (Object item : titles) {
                    titleList.add(item == null ? null : item.toString());
                }

                int spinnerItemLayout = SystemUtil.getAttr(view.getContext(), R.attr.textViewResourceId).resourceId;
                int dropDownResourceId = SystemUtil.getAttr(view.getContext(), R.attr.dropDownResourceId).resourceId;
                final ArrayAdapter<String> adapter = new ArrayAdapter(
                        view.getContext(), spinnerItemLayout, titleList.toArray());
                adapter.setDropDownViewResource(dropDownResourceId);

                view.setAdapter(adapter);
            }
        }
    }
}
