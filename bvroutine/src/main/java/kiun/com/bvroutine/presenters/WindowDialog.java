package kiun.com.bvroutine.presenters;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.IntDef;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.interfaces.presenter.DialogPresenter;

public class WindowDialog implements DialogPresenter {

    @IntDef({Gravity.BOTTOM, Gravity.RIGHT, Gravity.LEFT, Gravity.TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

    Dialog alertDialog;
    Window window;
    ViewDataBinding mDataBinding;
    Context context;
    int layoutId;
    int gravity;

    public WindowDialog(Context context, int layoutId,int dialogHandler, @Type int gravity){
        this.context = context;
        this.layoutId = layoutId;
        this.gravity = gravity;

        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false);
        mDataBinding.setVariable(dialogHandler, this);

        alertDialog = new Dialog(context);
        alertDialog.setContentView(mDataBinding.getRoot());
        window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        window.setBackgroundDrawableResource(android.R.color.white);
        window.setGravity(gravity);
        switch (gravity){
            case Gravity.BOTTOM:
                window.setWindowAnimations(R.style.DialogBottom);
                break;
            case Gravity.RIGHT:
                window.setWindowAnimations(R.style.DialogRight);
                break;
            case Gravity.TOP:
                window.setWindowAnimations(R.style.DialogTop);
                break;
            case Gravity.LEFT:
                window.setWindowAnimations(R.style.DialogLeft);
                break;
        }
    }

    @Override
    public void configDialog(String title, Object... args) {
        if (args.length > 0 && args.length % 2 == 0){
            for (int i = 0; i < args.length; i += 2){
                if (args[i] instanceof Integer){
                    mDataBinding.setVariable((Integer) args[i], args[i+1]);
                }
            }
        }
    }

    @Override
    public void showMessage(String msg, Object... values) {
    }

    @Override
    public void show() {
        alertDialog.show();
        window.setLayout(gravity < 0x10 ? WindowManager.LayoutParams.WRAP_CONTENT : WindowManager.LayoutParams.MATCH_PARENT,
                         gravity < 0x10 ? WindowManager.LayoutParams.MATCH_PARENT : WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void hide() {
        alertDialog.hide();
    }
}
