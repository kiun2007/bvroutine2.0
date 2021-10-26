package kiun.com.bvroutine.handlers;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.presenter.GridPresenter;

public abstract class GridHandler<T> extends BaseHandler<T> {

    protected GridPresenter presenter;
    SetCaller<T> caller;
    float scaleHeight = 1.0f;
    private boolean isEditMode;

    public GridHandler(int hanlderBr, SetCaller<T> caller) {
        super(hanlderBr);
        this.caller = caller;
    }

    public void remove(T item){
        if (presenter != null){
            presenter.remove(item);
        }
    }

    public void removeAt(int index){
        if (presenter != null){
            presenter.removeAt(index);
        }
    }

    public void setScaleHeight(float scaleHeight) {
        this.scaleHeight = scaleHeight;
    }

    public float getScaleHeight() {
        return scaleHeight;
    }

    @Override
    public void onClick(Context context, int tag, T data) {
        if (caller != null){
            caller.call(data);
        }
    }

    public abstract void onClick(View v, T item, int index);

    public void setPresenter(GridPresenter presenter) {
        this.presenter = presenter;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }
}
