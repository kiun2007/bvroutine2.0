package kiun.com.bvroutine.data.viewmodel;

import java.util.List;

public interface SpinnerData {

    void setSelected(int index);

    int getSelected();

    int itemLayoutId();

    List<? extends SpinnerItem> allItems();
}
