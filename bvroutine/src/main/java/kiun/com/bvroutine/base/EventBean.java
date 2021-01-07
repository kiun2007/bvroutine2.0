package kiun.com.bvroutine.base;

import android.util.Log;

import androidx.databinding.ViewDataBinding;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.data.verify.Problem;
import kiun.com.bvroutine.data.verify.ProblemExport;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.utils.VerifyUtil;

public abstract class EventBean {

    @JSONField(serialize = false)
    private ViewDataBinding binding;

    @JSONField(serialize = false)
    private int dataBR = -1;

    @JSONField(serialize = false)
    private SetCaller listener;

    @JSONField(serialize = false)
    private SetCaller<String> errorListener;

    @JSONField(serialize = false)
    private Map<String, ProblemExport> problemExports = new HashMap<>();

    public<T extends EventBean> T bind(int br, ViewDataBinding viewDataBinding){
        this.binding = viewDataBinding;
        this.dataBR = br;
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

    protected void onError(String error){
        if (errorListener != null){
            errorListener.call(error);
        }
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
     * 值变化后通知界面.
     * @param onlyBr 是否只通知界面变化, 不通知监听.
     * @param fieldName 变化的字段名.
     */
    public void onChanged(boolean onlyBr, String fieldName){

        if (!onlyBr && listener != null){
            listener.call(this);
        }

        if (binding != null && dataBR != -1){
            binding.invalidateAll();
        }

        if (problemExports.isEmpty()) return;

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
    }

    public List<Problem> verify(){
        return VerifyUtil.verify(this);
    }
}