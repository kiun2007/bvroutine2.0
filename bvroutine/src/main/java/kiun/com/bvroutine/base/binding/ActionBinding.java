package kiun.com.bvroutine.base.binding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.base.TransmitAgentType;
import kiun.com.bvroutine.base.TransmitView;
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.base.binding.value.BindConvertBuilder;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.interfaces.callers.GetIntentCaller;
import kiun.com.bvroutine.interfaces.callers.IntentResult;
import kiun.com.bvroutine.interfaces.callers.ObjectSetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.callers.StringArrayCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.presenters.TextViewLoadingPresenter;
import kiun.com.bvroutine.presenters.listener.ListenerController;
import kiun.com.bvroutine.utils.DataUtil;
import kiun.com.bvroutine.utils.ListHandlerUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.events.ActionListener;
import kiun.com.bvroutine.views.events.BeforehandClickListener;
import kiun.com.bvroutine.views.events.EventAdapter;
import retrofit2.Call;

@InverseBindingMethods({
        @InverseBindingMethod(type = View.class, attribute = "android:action"),
        @InverseBindingMethod(type = View.class, attribute = "android:startActivity")
})
public class ActionBinding {

    @BindingAdapter("android:iconGravity")
    public static void setIconGravity(TextView view, int gravity){
        view.setTag(R.id.tagIconGravity, gravity);
    }

    /**
     *
     * @param button
     * @param call
     * @param eventBean
     * @param beforehand
     * @param onClickListener
     * @param arrayCaller
     */
    @BindingAdapter(value = {"android:action", "android:verify", "android:beforehand", "android:onClick", "android:beforeAction"}, requireAll = false)
    public static void setActionAndVerifyAndBeforehand(View button,
                                                       FormViewCaller call,
                                                       EventBean eventBean,
                                                       String[] beforehand,
                                                       View.OnClickListener onClickListener,
                                                       StringArrayCaller arrayCaller){
        //全部为空不做任何处理
        if (call == null && onClickListener == null) return;

        if (button instanceof TransmitView){
            View source = button;
            //代理全部业务, 直接替换对象
            if (((TransmitView) button).agentType() == TransmitAgentType.ALL){
                button = ((TransmitView) button).child();
                if (button == null) return;

                if (source.getTag() != null){
                    button.setTag(source.getTag());
                }
            }
        }

        if (onClickListener != null && call == null){
            if (beforehand == null){
                button.setOnClickListener(onClickListener);
            }else{
                button.setOnClickListener(new BeforehandClickListener(beforehand, arrayCaller) {
                    @Override
                    protected void implement(View view) {
                        onClickListener.onClick(view);
                    }
                });
            }
            return;
        }

        if (button.getContext() instanceof RequestBVActivity){
            new ActionListener(call, eventBean, beforehand, arrayCaller, onClickListener)
                    .setEventAdapter(new EventAdapter(button));
        }
    }

    @BindingAdapter("android:startActivity")
    public static void setStartActivity(View button, Class clz){

        if (button.getContext() instanceof Activity){
            if (button instanceof TransmitView && ((TransmitView) button).child() != null){
                button = ((TransmitView) button).child();
            }

            button.setOnClickListener((view)-> {
                view.getContext().startActivity(new Intent(view.getContext(), clz));
            });
        }
    }

    @BindingAdapter("android:actionHandler")
    public static void setActionHandler(View view, Object handler){
        view.setTag(R.id.tagActionHandler, handler);
    }

    @BindingAdapter("android:actionArgument")
    public static void setActionArgument(View view, Object[] argument){
        view.setTag(R.id.tagActionArgument, argument);
    }

    @BindingAdapter(value = {"android:startIntent", "android:listHandler"}, requireAll = false)
    public static void startIntent(View button, Intent intent, ListHandler listHandler){

        if (button.getContext() instanceof BVBaseActivity && intent != null){
            if (button instanceof TransmitView && ((TransmitView) button).child() != null){
                button = ((TransmitView) button).child();
            }

            BVBaseActivity bvBaseActivity = (BVBaseActivity) button.getContext();

            button.setOnClickListener((view)-> {
                bvBaseActivity.startForResult(intent, (intent1)->{
                    ListHandlerUtil.refresh(listHandler);
                });
            });
        }
    }

    @BindingAdapter("android:startIntentAction")
    public static void startIntentAction(View button, GetIntentCaller caller){
        startIntentAll(button, caller, null, null);
    }

    @BindingAdapter("android:startIntentResult")
    public static void startIntentActionAndResult(View button, IntentResult setCaller){
    }

    @BindingAdapter({"android:startIntentAction","android:startIntentResult"})
    public static void startIntentActionAndResult(View button, GetIntentCaller caller, IntentResult setCaller){
        startIntentAll(button, caller, setCaller, null);
    }

    @BindingAdapter({"android:startIntentAction","android:beforehand"})
    public static void startIntentActionAndBeforehand(View button, GetIntentCaller caller, String[] beforehand){
        startIntentAll(button, caller, null, beforehand);
    }

    @BindingAdapter({"android:startIntentAction","android:startIntentResult", "android:beforehand"})
    public static void startIntentAll(View button, GetIntentCaller caller, IntentResult setCaller, String[] beforehand){

        button.setOnClickListener(new BeforehandClickListener(beforehand, null) {
            @Override
            protected void implement(View view) {
                if (caller != null && view.getContext() instanceof BVBaseActivity){
                    Intent intent = caller.get(view);
                    
                    if (setCaller != null){
                        ((BVBaseActivity) view.getContext()).startForResult(intent, result->{
                            setCaller.callBack(result);
                        });
                    }else {
                        view.getContext().startActivity(intent);
                    }
                }
            }
        });
    }

    @BindingAdapter("android:startName")
    public static void startName(View button, ComponentName name){
        if (button.getContext() instanceof Activity){
            button.setOnClickListener((view)-> {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(name);
                button.getContext().startActivity(intent);
            });
        }
    }

    //"android:bindConvertBuilder"
    @BindingAdapter(value = {"android:val", "android:bindConvert"}, requireAll = false)
    public static<T> void setValue(View view, T value, Class<? extends BindConvert> convert){

        if (view instanceof ValListener){
            ((ValListener) view).setVal(value);
            return;
        }

        BindConvert bindConvert = BindConvertBridge.getViewBindConvert(view, convert);
        if (bindConvert != null){
            if (!bindConvert.isOnlySet()){
                bindConvert.setValue(value);
            }
        }else{
            view.setTag(R.id.tagViewValue, value);
        }
    }

    @BindingAdapter("android:broadcast")
    public static void setBroadcast(View view, String broadcast){

        view.getContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ListenerController.execClick(null, view);
            }
        }, new IntentFilter(broadcast));
    }

    @InverseBindingAdapter(attribute = "android:val", event = "android:valAttrChanged")
    public static<T> T getValue(View view) {

        if (view instanceof ValListener){
            return (T) ((ValListener) view).getVal();
        }

        BindConvert<?,T,T> bindConvert = BindConvertBridge.getViewBindConvert(view, null);
        if (bindConvert != null){
            return bindConvert.getValue();
        }
        return null;
    }

    @BindingAdapter(value = {"android:bindConvert", "android:bindConvertBuilder", "android:valAttrChanged", "android:valChanged"}, requireAll = false)
    public static void setValueChanged(View view, Class<? extends BindConvert> convert, BindConvertBuilder builder, InverseBindingListener listener, ObjectSetCaller caller) {

        if (view instanceof ValListener){
            ((ValListener) view).setListener(listener);
            if (view instanceof ValChangedListener){
                ((ValChangedListener) view).setListener(caller);
            }
            return;
        }

        if (convert != null){
            BindConvertBridge.getViewBindConvert(view, convert);
        }

        if (builder != null){
            if(view.getTag(R.id.tagBinConvert) == null){
                view.setTag(R.id.tagBinConvert, builder.build(view));
            }
        }

        BindConvert bindConvert = BindConvertBridge.getViewBindConvert(view, null);
        if (bindConvert != null){
            bindConvert.setListener(listener);
            bindConvert.setChangedCaller(caller);
        }else{
//            view.setTag(R.id.tagViewListener, listener);
        }
    }
}
