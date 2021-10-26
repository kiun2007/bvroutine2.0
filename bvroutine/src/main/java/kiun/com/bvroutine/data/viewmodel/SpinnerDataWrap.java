package kiun.com.bvroutine.data.viewmodel;


import java.util.List;

import kiun.com.bvroutine.base.EventBean;

public class SpinnerDataWrap<T extends SpinnerItem> extends EventBean implements SpinnerDropDownData {

    protected List<T> allList;
    int selectedIndex = 0;
    private int itemLayoutId;
    private int dropDownViewId;

    public SpinnerDataWrap(List<T> allList, int itemLayoutId, int dropDownViewId) {
        this.allList = allList;
        this.itemLayoutId = itemLayoutId;
        this.dropDownViewId = dropDownViewId;
    }

    public SpinnerDataWrap(List<T> list){
        allList = list;
    }

    @Override
    public void setSelected(int index) {
        selectedIndex = index;
        onChanged(false);
    }

    @Override
    public int getSelected() {
        return selectedIndex;
    }

    @Override
    public int itemLayoutId() {
        return itemLayoutId;
    }

    @Override
    public List<T> allItems() {
        return allList;
    }

    public T getSelectedItem(){
        if (allList != null && allList.size() > selectedIndex){
            return allList.get(selectedIndex);
        }
        return null;
    }

    @Override
    public int dropDownViewId() {
        return dropDownViewId;
    }
}
