package kiun.com.bvroutine.views.events;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine.base.RequestBVFragment;
import kiun.com.bvroutine.base.TransmitAgentType;
import kiun.com.bvroutine.base.TransmitView;
import kiun.com.bvroutine.base.binding.ActionAnim;
import kiun.com.bvroutine.base.binding.VerifyBinder;
import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.interfaces.callers.FormViewCaller;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.callers.StringArrayCaller;
import kiun.com.bvroutine.interfaces.presenter.RequestBindingPresenter;
import kiun.com.bvroutine.interfaces.view.FormView;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;
import kiun.com.bvroutine.presenters.ImageViewLoadingPresenter;
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

    /**
     * 初始化动作监视器
     * @param call
     * @param eventBean
     * @param beforehand
     * @param arrayCaller
     * @param onClickListener
     */
    public ActionListener(FormViewCaller call, EventBean eventBean, String[] beforehand, StringArrayCaller arrayCaller, View.OnClickListener onClickListener) {
        super(beforehand, arrayCaller);
        this.call = call;
        this.eventBean = eventBean;
        this.onClickListener = onClickListener;
    }

    private void callNoNull(ActionAnim presenter, SetCaller<ActionAnim> caller){
        if (presenter != null && caller != null){
            caller.call(presenter);
        }
    }

    private ActionAnim getAnimHandler(View view){

        if (view instanceof TransmitView){
            TransmitAgentType type = ((TransmitView) view).agentType();
            if (type == TransmitAgentType.ALL || type == TransmitAgentType.ANIM){
                View child = ((TransmitView) view).child();
                view = child == null ? view : child;
            }
        }

        if (view instanceof TextView){
            return new TextViewLoadingPresenter((TextView) view);
        }

        if (view instanceof ImageView){
            return new ImageViewLoadingPresenter((ImageView) view);
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void implement(View view) {

        Context context = view.getContext();
        RequestBVActivity<?> activity = (RequestBVActivity<?>) view.getContext();

        ActionAnim actionAnim = getAnimHandler(view);
        callNoNull(actionAnim, ActionAnim::begin);

        if (eventBean != null){

            eventBean.setWithWaring(false);
            List<Problem> problems = eventBean.verify(view);
            if (!problems.isEmpty()){

                //采集验证问题
                VerifyBinder verifyBinder = new VerifyBinder(view.getRootView());
                verifyBinder.sendProblem(problems, eventBean);

                List<Problem> warringProblems = ListUtil.filter(problems, item -> !item.isForce());
                problems = ListUtil.filter(problems, Problem::isForce);

                if (!problems.isEmpty()){
                    if (problems.size() == 1){
                        Toast.makeText(context, problems.get(0).getDesc(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context, "页面有多个验证错误,请按照提示填写!", Toast.LENGTH_LONG).show();
                    }
                    callNoNull(actionAnim, ActionAnim::failed);
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

                        new AlertDialog.Builder(context).setIcon(R.drawable.ic_baseline_warning_24).setTitle("警告")
                                .setMessage(stringBuilder.toString())
                                .setPositiveButton("继续提交", (dialog, which) -> {
                                    commitStart(view, activity, actionAnim,true);
                                }).setNegativeButton("取消", (dialog, which) -> {
                            Toast.makeText(context, "提交已取消", Toast.LENGTH_LONG).show();
                            callNoNull(actionAnim, ActionAnim::failed);
                        }).show();
                        return;
                    }
                }
            }
        }
        commitStart(view, activity, actionAnim,false);
    }

    /**
     * 执行异步任务
     * @param activity
     * @param formView
     * @return
     * @throws Exception
     */
    private Object callOfExecute(RequestBVActivity<?> activity, FormView formView) throws Exception {
        RequestBindingPresenter p = activity.getRequestPresenter();
        Object result = call.call(formView);
        if (result instanceof Call){
            return p.execute((Call<?>) result);
        }
        return result;
    }

    private FormView getFormView(View view){

        if (view instanceof FormView){
            return (FormView) view;
        }else if (view.getTag(R.id.tagBinConvert) instanceof FormView){
            return (FormView) view.getTag(R.id.tagBinConvert);
        }
        return null;
    }

    /**
     * 开始提交
     * @param view
     * @param activity
     * @param isWaring
     */
    private void commitStart(View view,
                             RequestBVActivity<?> activity,
                             ActionAnim actionAnim,
                             boolean isWaring){

        RequestBVFragment<?> fragment = ViewUtil.findRootViewTag(view, RequestBVFragment.class);
        String tag = view.getTag() instanceof String ? (String) view.getTag() : null;

        FormView formView = getFormView(view);
        if (formView == null) formView =  activity;
        TransmitView transmitView = view instanceof TransmitView ? (TransmitView) view : null;

        view.setEnabled(false);
        RequestBindingPresenter p = activity.getRequestPresenter();
        FormView finalFormView = formView;
        p.addRequest(()-> this.callOfExecute(activity, finalFormView), (v)->{

            /**
             * 先使用 属性actionHandler {@link kiun.com.bvroutine.base.binding.ActionBinding#setActionHandler}
             */
            Object handler = view.getTag(R.id.tagActionHandler);
            Object[] argument = (Object[]) view.getTag(R.id.tagActionArgument);

            //在查找是否属于 fragment
            if (handler == null){
                handler = fragment;
            }

            //最后查找到 所属Activity
            if (handler == null){
                handler = activity;
            }

            boolean isSuccess = false;
            String message = null;

            if (v instanceof DataWrap){
                isSuccess = DataUtil.dataComplete(tag, (DataWrap) v, handler, isWaring, view, argument);
                message = ((DataWrap<?>) v).getMsg();
            }else if (v != null){
                isSuccess = DataUtil.dataComplete(tag, v, handler, isWaring, activity);
            }

            if (isSuccess || v == null){
                callNoNull(actionAnim, ActionAnim::complete);
                view.setEnabled(true);
                if (onClickListener != null){
                    onClickListener.onClick(view);
                }
            }else{
                callNoNull(actionAnim, ActionAnim::error);
                view.setEnabled(true);
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                if (transmitView != null){
                    transmitView.catchError(message);
                }
            }
        }, ex->{
            if (transmitView != null){
                transmitView.catchError(ex.getMessage());
            }
            callNoNull(actionAnim, ActionAnim::error);
            view.setEnabled(true);
            Toast.makeText(activity, ex.getMessage() == null ? "未知错误" : ex.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}