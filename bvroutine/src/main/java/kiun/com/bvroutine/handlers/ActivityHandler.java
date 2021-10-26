package kiun.com.bvroutine.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.interfaces.callers.IntentSetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class ActivityHandler<B extends Parcelable> extends ListHandler<B>{

    private Class<? extends Activity> activityClz;
    private List<KeyValue<Class<? extends Activity>, IntentSetCaller>> keyValues = new LinkedList<>();

    public ActivityHandler(int handlerBr, Class<? extends Activity> clz){
        this(handlerBr, 0, clz);
    }

    public ActivityHandler(int handlerBr, int errorLayout, Class<? extends Activity> clz){
        super(handlerBr, errorLayout);
        activityClz = clz;
    }


    public void addResult(Class<? extends Activity> clz, IntentSetCaller caller){
        keyValues.add(new KeyValue<>(clz, caller));
    }

    @Override
    public void onClick(Context context, int tag, B data) {

        Intent intent = new Intent(context, activityClz);
        intent.putExtra("EXTRA", data);
        context.startActivity(intent);
    }

    public void onClick(Context context, int tag, String key, Serializable data) {

        Intent intent = new Intent(context, activityClz);
        intent.putExtra(key, data);
        context.startActivity(intent);
    }

    public void resultClick(Context context, int index, String key, B data){
        if (context instanceof BVBaseActivity && index >= 0 && index < keyValues.size()){
            Intent intent = new Intent(context, keyValues.get(index).key());
            if (data != null){
                intent.putExtra(key, data);
            }
            ((BVBaseActivity) context).startForResult(intent, keyValues.get(index).value());
        }
    }

    public void resultClick(Context context, int index, String key, Serializable data){
        if (context instanceof BVBaseActivity && index >= 0 && index < keyValues.size()){
            Intent intent = new Intent(context, keyValues.get(index).key());
            if (data != null){
                intent.putExtra(key, data);
            }
            ((BVBaseActivity) context).startForResult(intent, keyValues.get(index).value());
        }
    }

    public static ActivityHandler create(Class<? extends Activity> clz, int errorLayoutId){
        
        ActivityHandler activityHandler = new ActivityHandler(BR.handler, errorLayoutId, clz);
        return activityHandler;
    }
}