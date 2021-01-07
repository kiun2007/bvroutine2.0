package kiun.com.bvroutine.presenters;

import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class ViewsPresenter<V extends View> {
    protected List<V> allViews = new LinkedList<>();

    public ViewsPresenter(V ...args){
        for (V item : args){
            allViews.add(item);
        }
    }
}
