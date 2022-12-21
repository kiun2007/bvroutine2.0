package kiun.com.bvroutine.views.events;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import kiun.com.bvroutine.base.binding.ChangedListener;
import kiun.com.bvroutine.base.binding.ValChangedListener;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.interfaces.callers.ObjectSetCaller;
import kiun.com.bvroutine.presenters.listener.ListenerController;

/**
 * 事件适配器
 */
public class EventAdapter implements View.OnClickListener, AdapterView.OnItemSelectedListener, ObjectSetCaller {

    private View view;
    private View.OnClickListener listener;

    public EventAdapter(View view) {
        this.view = view;
        if (view instanceof ChangedListener){
            ((ChangedListener) view).setListener(this);
            return;
        }

        ListenerController.regListener(view, this);
        if (view instanceof Spinner){
            ((Spinner) view).setOnItemSelectedListener(this);
        } else {
            view.setOnClickListener(this);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        ListenerController.execClick(this, v);
        if (listener != null){
            listener.onClick(v);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ListenerController.execItemClick(this, parent, view, position, id);
        if (listener != null){
            listener.onClick(parent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        int a = 0;
    }

    @Override
    public void call(Object v) {
        if (listener != null){
            listener.onClick(view);
        }
    }
}
