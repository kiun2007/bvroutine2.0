package com.szxgm.gusustreet.views.bind;

import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Arrays;

import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.base.binding.value.BindConvertBuilder;
import kiun.com.bvroutine.utils.MCString;

public class AppCompatSpinnerBindConvert extends BindConvert<AppCompatSpinner, String, String> implements AdapterView.OnItemSelectedListener {

    private String[] values = null;

    private AppCompatSpinnerBindConvert(AppCompatSpinner view, String[] values) {
        this(view);
        this.values = values;
    }

    public AppCompatSpinnerBindConvert(AppCompatSpinner view) {
        super(view);
        view.setOnItemSelectedListener(this);
    }

    @Override
    public String getValue() {
        return nowValue;
    }

    @Override
    public void setValue(String value) {
        if (values != null){
            int index = value == null ? 0 : Arrays.asList(values).indexOf(value);
            view.setSelection(index);
            return;
        }
        view.setSelection(MCString.toNumber(value).intValue() - 1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (values != null){
            nowValue = MCString.item(position, 0, values);
        }else{
            nowValue = String.valueOf(position + 1);
        }
        onChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public static class Builder extends BindConvertBuilder<AppCompatSpinner>{

        private String[] values = null;

        public Builder(String[] values) {
            this.values = values;
        }

        @Override
        public BindConvert<AppCompatSpinner, ?, ?> build(AppCompatSpinner view) {
            return new AppCompatSpinnerBindConvert(view, values);
        }
    }

    public static Builder create(String[] valueArray){
        return new Builder(valueArray);
    }
}
