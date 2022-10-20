package kiun.com.bvroutine.presenters;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.binding.value.UnionSpinnerConvert;
import kiun.com.bvroutine.base.binding.variable.AutoImport;
import kiun.com.bvroutine.base.binding.variable.ViewLoadVariableSet;
import kiun.com.bvroutine.interfaces.callers.PagerCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.handler.ViewLoadHandler;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.utils.SystemUtil;

/**
 * 下拉框联盟
 */
@AutoImport(ViewLoadVariableSet.class)
public class SpinnerUnion implements ViewLoadHandler {

    private RequestBVActivity activity;

    private int spinnerItemTextLayoutId;

    private int dropdownItemLayoutId;

    private boolean isInitStart = true;

    public static class Empty {

        private String title;

        public Empty(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private static class UnionSrc implements SetCaller {

        private Spinner spinner;

        private Integer sort;

        private PagerCaller caller;

        private SpinnerUnion union;

        private UnionSrc next;

        private String empty = null;

        private int spinnerItemTextLayoutId = R.layout.spinner_normal_item_text;

        private int dropdownItemLayoutId = R.layout.spinner_normal_dropdown_item;

        public UnionSrc(SpinnerUnion union) {
            this.union = union;
        }

        public void setEmpty(String empty) {
            this.empty = empty;
        }

        public void setSpinner(Spinner spinner) {
            this.spinner = spinner;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public void setCaller(PagerCaller caller) {
            this.caller = caller;
        }

        private void binding(){
            UnionSpinnerConvert convert = (UnionSpinnerConvert) spinner.getTag(R.id.tagBinConvert);
            if (convert != null){
                convert.setOnValueChanged(this);
            }
        }

        public void requestData(){

            if(union.activity != null && caller != null){

                union.activity.addRequest(()-> RetrofitUtil.unpackWrap(null, caller.get(null).execute()), v->{

                    List<?> data = (List<?>) v;
                    if(data != null){

                        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(union.activity, spinnerItemTextLayoutId);
                        adapter.setDropDownViewResource(dropdownItemLayoutId);

                        List<?> list = data;
                        if (empty != null && list.size() > 1){
                            adapter.add(new Empty(empty));
                        }

                        for (Object item : list) {
                            adapter.add(item);
                        }
                        spinner.setAdapter(adapter);
                    }
                });
            }
        }

        @Override
        public void call(Object o) {
            if(next != null){
                next.requestData();
            }
        }
    }

    private Map<Integer, UnionSrc> spinnerViewMap = new LinkedHashMap<>();

    private Integer[] sortItems;

    @BindingAdapter({"android:union", "android:unionSort", "android:unionSource", "android:unionEmpty"})
    public static void setUnion(Spinner spinner, SpinnerUnion union, Integer sort, PagerCaller caller, String empty){

        if (union != null){
            union.spinnerViewMap.put(sort, new UnionSrc(union){{
                setSpinner(spinner);
                setCaller(caller);
                setSort(sort);
                setEmpty(empty);
            }});
        }
    }

    @BindingAdapter({"android:union", "android:unionSort", "android:unionSource"})
    public static void setUnion(Spinner spinner, SpinnerUnion union, Integer sort, PagerCaller caller){
        setUnion(spinner, union, sort, caller, null);
    }

    @BindingAdapter("android:unionSource")
    public static void setUnionSource(Spinner spinner, PagerCaller caller){
    }

    @Override
    public void setContext(Context context) {
        if(context instanceof RequestBVActivity){
            this.activity = (RequestBVActivity) context;
            spinnerItemTextLayoutId = SystemUtil.getAttr(context, R.attr.textViewResourceId).resourceId;
            dropdownItemLayoutId = SystemUtil.getAttr(context, R.attr.dropDownResourceId).resourceId;
        }
    }

    @Override
    public boolean isInitStart() {
        return isInitStart;
    }

    public SpinnerUnion initStart(boolean isInitStart){
        this.isInitStart = isInitStart;
        return this;
    }

    public void start(){

        if(spinnerViewMap.isEmpty()){
            return;
        }

        for (UnionSrc item : spinnerViewMap.values()){
            if (spinnerItemTextLayoutId != 0){
                item.spinnerItemTextLayoutId = spinnerItemTextLayoutId;
            }

            if(dropdownItemLayoutId != 0){
                item.dropdownItemLayoutId = dropdownItemLayoutId;
            }
            item.binding();
        }

        sortItems = new Integer[spinnerViewMap.size()];
        spinnerViewMap.keySet().toArray(sortItems);
        Arrays.sort(sortItems);

        for (int i = 0; i < sortItems.length; i++) {
            spinnerViewMap.get(sortItems[i]).next = i == sortItems.length - 1 ? null : spinnerViewMap.get(sortItems[i+1]);
        }
        spinnerViewMap.get(sortItems[0]).requestData();
    }
}
