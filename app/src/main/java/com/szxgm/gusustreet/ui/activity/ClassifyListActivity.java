package com.szxgm.gusustreet.ui.activity;

import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public abstract class ClassifyListActivity<T> extends GeneralListActivity implements TabLayout.BaseOnTabSelectedListener {

    protected abstract List<T> classifyList() throws Exception;

    protected abstract String title(T item);

    protected abstract void onSelected(T item);

    private boolean isFirst = false;

    private int selectIndex = -1;

    @Override
    public void initView() {
        binding.classifyTabLayout.setVisibility(View.VISIBLE);
        reloadTab();
        binding.classifyTabLayout.addOnTabSelectedListener(this);
        super.initView();
    }

    protected void reloadTab(){
        rbp.addRequest(this::classifyList, this::classifyComplete);
    }

    protected void setTabMode(int mode){
        binding.classifyTabLayout.setTabMode(mode);
    }

    @Override
    protected void startList() {}

    protected boolean isSelect(int index, T item){
        return index == 0;
    }

    protected void classifyComplete(List<T> list){

        binding.classifyTabLayout.removeAllTabs();
        int i = 0;
        for (T item : list){
            TabLayout.Tab tab = binding.classifyTabLayout.newTab();
            tab.setTag(item);
            tab.setText(title(item));

            binding.classifyTabLayout.addTab(tab, isSelect(i, item));
            i++;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        if (selectIndex == binding.classifyTabLayout.getSelectedTabPosition()){
            return;
        }
        selectIndex = binding.classifyTabLayout.getSelectedTabPosition();
        onSelected((T) tab.getTag());

        if (isFirst){
            general.refresh();
        }else{
            general.start();
        }
        isFirst = true;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
