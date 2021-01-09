package kiun.com.bvroutine.base.binding.value;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import kiun.com.bvroutine.data.viewmodel.SpinnerData;
import kiun.com.bvroutine.data.viewmodel.SpinnerDropDownData;
import kiun.com.bvroutine.data.viewmodel.SpinnerItem;
import kiun.com.bvroutine.utils.ListUtil;

public class SpinnerBindConvert<T> extends BindConvert<Spinner, SpinnerData, T> implements AdapterView.OnItemSelectedListener {

    public static final Class clz = SpinnerBindConvert.class;

    private ArrayAdapter<String> arrayAdapter;

    protected SpinnerData nowSpinnerData;

    public SpinnerBindConvert(Spinner view) {
        super(view);
        view.setOnItemSelectedListener(this);
    }

    @Override
    public T getValue() {
        return nowValue;
    }

    @Override
    public void setValue(SpinnerData value) {

        if (value == null){
            return;
        }

        boolean isFirst = arrayAdapter == null;
        if (isFirst){
            arrayAdapter = new ArrayAdapter<>(view.getContext(), value.itemLayoutId());
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            if (value instanceof SpinnerDropDownData){
                arrayAdapter.setDropDownViewResource(((SpinnerDropDownData) value).dropDownViewId());
            }
            view.setAdapter(arrayAdapter);
        }

        arrayAdapter.clear();
        arrayAdapter.addAll(ListUtil.toList(value.allItems(), SpinnerItem::label));
        arrayAdapter.notifyDataSetChanged();

        nowSpinnerData = value;

        view.setSelection(value.getSelected());
        if (!isFirst){
            nowSpinnerData.setSelected(value.getSelected());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (nowSpinnerData == null){
            return;
        }

        nowSpinnerData.setSelected(position);
        onChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
