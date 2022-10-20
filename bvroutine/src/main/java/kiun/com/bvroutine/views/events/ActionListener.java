package kiun.com.bvroutine.views.events;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.base.binding.VerifyBinder;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.callers.StringArrayCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.presenters.TextViewLoadingPresenter;
import kiun.com.bvroutine.utils.DataUtil;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ViewUtil;
import retrofit2.Call;

public final class ActionListener extends BeforehandClickListener {

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

        if (eventBean != null){

            eventBean.setWithWaring(false);

            List<Problem> problems = eventBean.verify(view);
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
                                    commitStart(viewButton, activity, presenter,true);
                                }).setNegativeButton("取消", (dialog, which) -> {
                            Toast.makeText(activity, "提交已取消", Toast.LENGTH_LONG).show();
                            callNoNull(presenter, TextViewLoadingPresenter::failed);
                        }).show();
                        return;
                    }
                }
            }
        }
        commitStart(view, activity, presenter,false);
    }

    private Object callOfExecute(RequestBVActivity<?> activity) throws Exception {
        RequestBindingPresenter p = activity.getRequestPresenter();
        Object result = call.call(activity);
        if (result instanceof Call){
            return p.execute((Call<?>) result);
        }
        return result;
    }

    private void commitStart(View view,
                             RequestBVActivity<?> activity,
                             TextViewLoadingPresenter presenter,
                             boolean isWaring){

        RequestBVFragment<?> fragment = ViewUtil.findRootViewTag(view, RequestBVFragment.class);
        String tag = view.getTag() instanceof String ? (String) view.getTag() : null;

        RequestBindingPresenter p = activity.getRequestPresenter();
        p.addRequest(()-> this.callOfExecute(activity), (v)->{

            /**
             * 先使用 属性actionHandler {@link kiun.com.bvroutine.base.binding.ActionBinding#setActionHandler}
             */
            Object handler = view.getTag(R.id.tagActionHandler);

            //在查找是否属于 fragment
            if (handler == null){
                handler = fragment;
            }

            //最后查找到 所属Activity
            if (handler == null){
                handler = activity;
            }

            boolean isSuccess = false;

            if (v instanceof DataWrap){
                isSuccess = DataUtil.dataComplete(tag, (DataWrap) v, handler, isWaring);
            }else if (v != null){
                isSuccess = DataUtil.dataComplete(tag, v, handler, isWaring);
            }

            if (isSuccess){
                callNoNull(presenter, TextViewLoadingPresenter::complete);
                if (onClickListener != null){
                    onClickListener.onClick(view);
                }
            }else{
                callNoNull(presenter, TextViewLoadingPresenter::error);
            }
        }, ex->{
            callNoNull(presenter, TextViewLoadingPresenter::error);
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}