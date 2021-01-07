package kiun.com.bvroutine.presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import kiun.com.bvroutine.interfaces.presenter.DialogPresenter;

public class ProgressDialogPresenter implements DialogPresenter {

    ProgressDialog progressDialog = null;
    public ProgressDialogPresenter(Context context){
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public void configDialog(String title, Object... args) {
        progressDialog.setTitle(title);

        if (args.length > 0 && args[0] instanceof Integer){
            progressDialog.setIcon((Integer) args[0]);
        }

        if (args.length > 1 && args[1] instanceof Integer){
            progressDialog.setMax((Integer) args[1]);
        }

        if (args.length > 2 && args[2] instanceof Integer){
            progressDialog.setProgressStyle((Integer) args[2]);
        }

        if (args.length > 3 && args[3] instanceof Boolean){
            progressDialog.setCancelable((Boolean) args[3]);
        }
    }

    @Override
    public void showMessage(String msg, Object... values) {

        Message message = new Message();
        message.obj = msg;

        if (values.length > 0 && values[0] instanceof Integer){
            message.arg1 = (Integer) values[0];
        }

        if (values.length > 1 && values[1] instanceof Integer){
            message.arg2 = (Integer) values[1];
        }
        mainLoopHandler.sendMessage(message);
    }

    @Override
    public void show() {

    }

    Handler mainLoopHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.obj instanceof String){
                progressDialog.setMessage((CharSequence) msg.obj);
                progressDialog.setMax(msg.arg1);
                progressDialog.setProgress(msg.arg2);
                progressDialog.show();
            }
        }
    };

    @Override
    public void hide() {
        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
