package kiun.com.bvroutine.interfaces.view;

import android.view.View;

@FunctionalInterface
public interface ItemFunction {
    void onItemClick(View listView, int tag);
}