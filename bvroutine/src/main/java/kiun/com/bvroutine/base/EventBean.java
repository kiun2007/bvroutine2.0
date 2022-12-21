package kiun.com.bvroutine.base;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ViewDataBinding;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.data.verify.ProblemExport;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.VerifyUtil;

public abstract class EventBean extends BaseObservable implements Serializable {

    @JSONField(serialize = false)
    private transient ViewDataBinding binding;

    @JSONField(serialize = false)
    private transient int dataBR = -1;

    @JSONField(serialize = false)
    private transient SetCaller listener;

    @JSONField(serialize = false)
    private transient SetCaller<String> errorListener;

    @JSONField(serialize = false)
    protected transient boolean isWithWaring = false;

    @JSONField(serialize = false)
    @Expose(serialize = false, deserialize = false)
    protected transient Map<String, ProblemExport> problemExports = new HashMap<>();

    /**
     * 数据传递给下个页面 的名称
     */
    @JSONField(serialize = false)
    protected transient String transfer;

    public<T extends EventBean> T bind(int br, ViewDataBinding viewDataBinding){
        binding = viewDataBinding;
        dataBR = br;
        return (T) this;
    }

    public<T extends EventBean> T listener(SetCaller<T> listener){
        this.listener = listener;
        return (T) this;
    }

    public<T extends EventBean> T error(SetCaller<String> msgCaller){
        errorListener = msgCaller;
        return (T) this;
    }

    @JSONField(serialize = false)
    public SetCaller getListener() {
        return listener;
    }

    public void addExport(String field, ProblemExport export){
        problemExports.put(field, export);
    }

    public void onChanged(){
        onChanged(true);
    }

    public<T extends EventBean> T transfer(String transfer) {
        this.transfer = transfer;
        return (T) this;
    }

    protected void onError(String error){
        if (errorListener != null){
            errorListener.call(error);
        }
    }

    public void setWithWaring(boolean withWaring) {
        isWithWaring = withWaring;
    }

    /**
     * 通过堆栈信息自动获取字段名称.
     * @return
     */
    private String findFieldName(){
        long start = System.currentTimeMillis();
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            try {
                if (stack[i].getMethodName().startsWith("set")){

                    Class clz = Class.forName(stack[i].getClassName());
                    if (EventBean.class.isAssignableFrom(clz) && !clz.equals(EventBean.class)){

                        String fieldName = stack[i].getMethodName().replace("set", "");
                        String first = fieldName.substring(0, 1).toLowerCase();
                        String last = fieldName.substring(1, fieldName.length());

                        fieldName = first + last;
                        return fieldName;
                    }
                }
            } catch (ClassNotFoundException e) {
            }
        }

        long time = System.currentTimeMillis() - start;
        Log.v("time", String.valueOf(time));
        return null;
    }

    public void onChanged(boolean onlyBr){
        onChanged(onlyBr, null);
    }

    /**
     * 单独通知给监听者
     */
    public void notifyListener(){
        if (listener != null){
            listener.call(this);
        }
    }

    /**
     * 值变化后通知界面.
     * @param onlyBr 是否只通知界面变化, 不通知监听.
     * @param fieldName 变化的字段名.
     */
    public void onChanged(boolean onlyBr, String fieldName){

        if (!onlyBr && listener != null){
            listener.call(this);
        }

        if (binding != null){
            if (dataBR == -1){
                binding.invalidateAll();
            }else{
                binding.setVariable(dataBR, this);
            }
        }

        if (problemExports == null || problemExports.isEmpty()) return;

        if (fieldName == null){
            fieldName = findFieldName();
        }

        if (fieldName == null){
            return;
        }

        ProblemExport export = problemExports.get(fieldName);
        if (export != null){
            export.clear();
        }

        transferChanged();
    }

    protected void beforeVerify(View view){
    }

    public List<Problem> verify(View view){
        beforeVerify(view);
        return VerifyUtil.verify(this);
    }

    @Override
    public void notifyPropertyChanged(int fieldId) {
        super.notifyPropertyChanged(fieldId);
        transferChanged();
    }

    private void transferChanged(){
        if (transfer != null && binding != null && binding.getRoot().getContext() instanceof Activity){
            Activity activity = (Activity) binding.getRoot().getContext();
            activity.getIntent().putExtra(transfer, this);
        }
    }
}