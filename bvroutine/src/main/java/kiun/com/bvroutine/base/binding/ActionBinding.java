package kiun.com.bvroutine.base.binding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;
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
import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.base.binding.value.BindConvertBuilder;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.interfaces.callers.GetIntentCaller;
import kiun.com.bvroutine.interfaces.callers.IntentResult;
import kiun.com.bvroutine.interfaces.callers.ObjectSetCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.callers.StringArrayCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.presenters.TextViewLoadingPresenter;
import kiun.com.bvroutine.utils.DataUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.events.BeforehandClickListener;
import retrofit2.Call;

@InverseBindingMethods({
        @InverseBindingMethod(type = View.class, attribute = "android:action"),
        @InverseBindingMethod(type = View.class, attribute = "android:startActivity")
})
public class ActionBinding {

    public static final class ActionListener extends BeforehandClickListener {

        private FormViewCaller call;
        private EventBean eventBean;
        //结束后调用 onClick
        private View.OnClickListener onClickListener;

        public ActionListener(FormViewCaller call, EventBean eventBean, String[] beforehand, StringArrayCaller arrayCaller, View.OnClickListener onClickListener) {
            super(beforehand, arrayCaller);
            this.call = call;
            this.eventBean = eventBean;
            this.onClickListener = onClickListener;
        }

        private void callNoNull(TextViewLoadingPresenter presenter, SetCaller<TextViewLoadingPresenter> caller){
            if (presenter != null && caller != null){
                caller.call(presenter);
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void implement(View view) {

            TextView viewButton = view instanceof TextView ? (TextView) view : null;
            RequestBVActivity<?> activity = (RequestBVActivity<?>) view.getContext();
            RequestBVFragment<?> fragment = ViewUtil.findRootViewTag(view, RequestBVFragment.class);

            TextViewLoadingPresenter presenter = viewButton != null ? new TextViewLoadingPresenter(viewButton) : null;
            callNoNull(presenter, TextViewLoadingPresenter::begin);

            Call retrofitCall  = (Call) call.call(activity);

            if (eventBean != null){

                eventBean.setWithWaring(false);

                List<Problem> problems = eventBean.verify();
                if (!problems.isEmpty()){

                    VerifyBinder verifyBinder = new VerifyBinder(view.getRootView());
                    verifyBinder.sendProblem(problems, eventBean);

                    List<Problem> warringProblems = ListUtil.filter(problems, item -> !item.isForce());

                    problems = ListUtil.filter(problems, Problem::isForce);

                    if (!problems.isEmpty()){
                        if (problems.size() == 1){
                            Toast.makeText(activity, problems.get(0).getDesc(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(activity, "页面有多个验证错误,请按照提示填写!", Toast.LENGTH_LONG).show();
                        }
                        callNoNull(presenter, TextViewLoadingPresenter::failed);
                        return;
                    }else{
                        if (!warringProblems.isEmpty()){

                            eventBean.setWithWaring(true);
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("提交前请先确认如下问题:\n");
                            for (int i = 0; i < warringProblems.size(); i++) {
                                Log.w("Verify", warringProblems.get(i).getField() + ":" + warringProblems.get(i).getDesc());
                            }
                            stringBuilder.append(String.format("%d处警告, 取消提交后可在页面查看详细\n", warringProblems.size()));
                            stringBuilder.append("是否继续提交?");

                            new AlertDialog.Builder(activity).setIcon(R.drawable.ic_baseline_warning_24).setTitle("警告")
                                    .setMessage(stringBuilder.toString())
                                    .setPositiveButton("继续提交", (dialog, which) -> {
                                        commitStart(viewButton, activity, presenter, retrofitCall,true);
                                    }).setNegativeButton("取消", (dialog, which) -> {
                                        Toast.makeText(activity, "提交已取消", Toast.LENGTH_LONG).show();
                                        callNoNull(presenter, TextViewLoadingPresenter::failed);
                                    }).show();
                            return;
                        }
                    }
                }
            }
            commitStart(view, activity, presenter, retrofitCall,false);
        }

        private void commitStart(View view, RequestBVActivity<?> activity, TextViewLoadingPresenter presenter, Call retrofitCall, boolean isWaring){

            RequestBVFragment<?> fragment = ViewUtil.findRootViewTag(view, RequestBVFragment.class);
            String tag = view.getTag() instanceof String ? (String) view.getTag() : null;

            RequestBindingPresenter p = activity.getRequestPresenter();
            p.addRequest(()->p.execute(retrofitCall), (v)->{
                if (v != null){
                    Object handler = view.getTag(R.id.tagActionHandler);
                    if (handler == null){
                        handler = fragment;
                    }

                    if (handler == null){
                        handler = activity;
                    }
                    boolean isSuccess = DataUtil.dataComplete(tag, v, handler, isWaring);

                    if (isSuccess){
                        callNoNull(presenter, TextViewLoadingPresenter::complete);
                        if (onClickListener != null){
                            onClickListener.onClick(view);
                        }
                    }else{
                        callNoNull(presenter, TextViewLoadingPresenter::error);
                    }
                }
            }, ex->{
                callNoNull(presenter, TextViewLoadingPresenter::error);
                Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
            });
        }
    }

    @BindingAdapter("android:iconGravity")
    public static void setIconGravity(TextView view, int gravity){
        view.setTag(R.id.tagIconGravity, gravity);
    }

    @BindingAdapter(value = {"android:action", "android:verify", "android:beforehand", "android:onClick", "android:beforeAction"}, requireAll = false)
    public static void setActionAndVerifyAndBeforehand(View button, FormViewCaller call, EventBean eventBean,
                                                       String[] beforehand, View.OnClickListener onClickListener, StringArrayCaller arrayCaller){

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
            button.setOnClickListener(new ActionListener(call, eventBean, beforehand, arrayCaller, onClickListener));
        }
    }

    @BindingAdapter("android:startActivity")
    public static void setStartActivity(View button, Class clz){
        if (button.getContext() instanceof Activity){
            button.setOnClickListener((view)-> {
                button.getContext().startActivity(new Intent(button.getContext(), clz));
            });
        }
    }

    @BindingAdapter("android:actionHandler")
    public static void setActionHandler(View view, Object handler){
        view.setTag(R.id.tagActionHandler, handler);
    }

    @BindingAdapter("android:startIntent")
    public static void startIntent(View button, Intent intent){
        if (button.getContext() instanceof Activity && intent != null){
            button.setOnClickListener((view)-> {
                button.getContext().startActivity(intent);
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
            view.setTag(R.id.tagBinConvert, builder.build(view));
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
