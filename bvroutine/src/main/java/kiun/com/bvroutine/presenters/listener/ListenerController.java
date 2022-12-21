package kiun.com.bvroutine.presenters.listener;

import android.view.View;
import android.widget.AdapterView;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.utils.ListUtil;

public class ListenerController {

    public static<LISTENER> void regListener(View view, LISTENER clickListener){

        List<LISTENER> listeners = (List<LISTENER>) view.getTag(R.id.tagListener);
        if (listeners == null){
            view.setTag(R.id.tagListener, listeners = new LinkedList<>());
        }

        for (Object item : listeners){
            if(item.getClass().equals(clickListener.getClass())){
                listeners.remove(item);
                break;
            }
        }
        listeners.add(clickListener);
    }

    public static void execClick(View.OnClickListener current, View view){

        Boolean isRun = (Boolean) view.getTag(R.id.tagEventIsRun);
        if (isRun != null && isRun){
            return;
        }

        view.setTag(R.id.tagEventIsRun, true);
        List<View.OnClickListener> listeners = (List<View.OnClickListener>) view.getTag(R.id.tagListener);
        ListUtil.map(listeners, item-> {
            if (current != item){
                item.onClick(view);
            }
        });
        view.setTag(R.id.tagEventIsRun, false);
    }

    public static void execItemClick(AdapterView.OnItemSelectedListener current, AdapterView<?> parentView, View view, int position, long id){

        Boolean isRun = (Boolean) parentView.getTag(R.id.tagEventIsRun);
        if (isRun != null && isRun){
            return;
        }

        parentView.setTag(R.id.tagEventIsRun, true);
        List<AdapterView.OnItemSelectedListener> listeners = (List<AdapterView.OnItemSelectedListener>) parentView.getTag(R.id.tagListener);
        ListUtil.map(listeners, item-> {
            if (current != item){
                item.onItemSelected(parentView, view, position, id);
            }
        });
        parentView.setTag(R.id.tagEventIsRun, false);
    }
}
