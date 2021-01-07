package kiun.com.bvroutine.views.delegate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public abstract class ViewDelegate<V extends View> implements TypedView {

    protected V view;

    public ViewDelegate(@NonNull V view, AttributeSet attributeSet) {
        this.view = view;
        if (attributeSet != null){
            ViewUtil.initTyped(this, attributeSet);
        }
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return view.findViewById(id);
    }
}
