package kiun.com.bvroutine.interfaces.view;

import android.content.Context;
import android.view.View;

public interface TypedView {

    int[] getStyleableId();

    Context getContext();

    void initView();

    <T extends View> T findParentById(int id);
}
