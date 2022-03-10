package kiun.com.bvroutine.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.interfaces.view.MessageView;
import kiun.com.bvroutine.utils.ViewBindingUtil;

/**
 * Created by kiun_2007 on 2018/8/12.
 */
public class MCDialogManager<T,VB extends ViewDataBinding> extends ListHandler<T> implements DialogInterface, MessageView {

    private Context context;
    private Dialog alertDialog;
    private int resId;
    private Object data;
    private int gravity = Gravity.BOTTOM;
    private int dataBr, dialogBr;
    private boolean flag;
    private boolean clickIsDismiss = true;
    private T selectItem = null;
    private SetCaller<T> caller;
    private int colorResId = android.R.color.white;
    private VB binding;
    private int width = 0, height = 0;
    private OnKeyListener onKeyListener;

    private MCDialogManager(Context context, int resId, Object data, int dataBr, int dialogBr) {
        super(BR.handler, 0);
        this.context = context;
        this.resId = resId;
        this.data = data;
        this.dataBr = dataBr;
        this.dialogBr = dialogBr;
    }

    public MCDialogManager<T,VB> setCancelable(boolean flag){
        this.flag = flag;
        return this;
    }

    public MCDialogManager<T,VB> setLayout(int width, int height){
        this.width = width;
        this.height = height;
        return this;
    }

    public MCDialogManager<T,VB> setClickIsDismiss(boolean clickIsDismiss) {
        this.clickIsDismiss = clickIsDismiss;
        return this;
    }

    public MCDialogManager<T,VB> transparent(){
        this.colorResId = android.R.color.transparent;
        return this;
    }

    public MCDialogManager<T,VB> show(){

        if (alertDialog == null){
            alertDialog = new Dialog(context);
            if (onKeyListener != null){
                alertDialog.setOnKeyListener(onKeyListener);
            }

            binding = ViewBindingUtil.inflate(LayoutInflater.from(context), resId, null, false);
            alertDialog.setContentView(binding.getRoot());
            alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            if (data instanceof EventBean){
                binding.setVariable(dataBr, ((EventBean) data).bind(dataBr, binding));
            }else{
                if (data != null){
                    binding.setVariable(dataBr, data);
                }
            }
            binding.setVariable(dialogBr, this);

            Window window = alertDialog.getWindow();
            if (gravity != Gravity.FILL){
                window.setBackgroundDrawableResource(this.colorResId);

                if (width != 0 && height != 0){
                    alertDialog.getWindow().setLayout(width, height);
                }else{
                    if ((gravity == Gravity.RIGHT) || (gravity == Gravity.LEFT)){
                        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
                    }else if (gravity == Gravity.CENTER){
                        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    }else if (gravity == Gravity.BOTTOM){
                        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    }
                }
            }

            window.setGravity(gravity);
            window.setWindowAnimations(R.style.AlertDialog_AppCompat);
            alertDialog.setCancelable(flag);
        }

        alertDialog.show();
        return this;
    }

    public MCDialogManager<T,VB> setOnCancelListener(OnCancelListener onCancelListener){
        alertDialog.setOnCancelListener(onCancelListener);
        return this;
    }

    public VB getBinding() {
        return binding;
    }

    @Override
    public void cancel() {
        alertDialog.cancel();
    }

    public void dismiss() {
        if (alertDialog != null){
            alertDialog.dismiss();
            alertDialog = null;
            if (caller != null) {
                caller.call(selectItem);
            }
        }
    }

    @Override
    public void onClick(Context context, int tag, T data) {

        if (clickIsDismiss){
            alertDialog.dismiss();
            if (caller != null){
                caller.call(data);
            }
        }else{
            selectItem = data;
        }
    }

    public MCDialogManager<T,VB> setOnKeyListener(OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
        return this;
    }

    public MCDialogManager<T,VB> setGravity(int gravity){
        this.gravity = gravity;
        return this;
    }

    public MCDialogManager<T,VB> bindValue(int br, Object data){

        assert binding != null;

        binding.setVariable(br, data);
        return this;
    }

    public MCDialogManager<T,VB> setCaller(SetCaller<T> caller) {
        this.caller = caller;
        return this;
    }

    public static<MC,MVB extends ViewDataBinding> MCDialogManager<MC,?> create(Context context, int resId, Object data, int dataBr, int dialogBr) {
        return new MCDialogManager(context, resId, data, dataBr, dialogBr);
    }

    public static<MC,MVB extends ViewDataBinding> MCDialogManager<MC,MVB> create(Context context, int resId, Object data) {
        return (MCDialogManager<MC, MVB>) create(context, resId, data, BR.data, BR.dialog);
    }

    @Override
    public void errorMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
