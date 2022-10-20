package kiun.com.bvroutine.views.events;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import kiun.com.bvroutine.presenters.listener.ListenerController;

public class EventAdapter implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View view;

    private View.OnClickListener listener;

    public EventAdapter(View view) {
        this.view = view;
        ListenerController.regListener(view, this);
        if (view instanceof Spinner){
            ((Spinner) view).setOnItemSelectedListener(this);
        }else {
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

    }
}
