package kiun.com.bvroutine.views.events;

import android.app.AlertDialog;
import android.view.View;

import kiun.com.bvroutine.interfaces.callers.StringArrayCaller;

public abstract class BeforehandClickListener implements View.OnClickListener{

    private String[] beforehand;
    private StringArrayCaller arrayCaller;

    public BeforehandClickListener(String[] beforehand, StringArrayCaller arrayCaller) {
        this.beforehand = beforehand;
        this.arrayCaller = arrayCaller;
    }

    public BeforehandClickListener setEventAdapter(EventAdapter eventAdapter) {
        eventAdapter.setListener(this);
        return this;
    }

    protected abstract void implement(View view);

    @Override
    public void onClick(View v) {

        if (arrayCaller != null){
            beforehand = arrayCaller.call();
        }

        if (beforehand == null || beforehand.length == 0){
            implement(v);
        }else {
            String title = "提示";
            String message = beforehand[0];

            String positive = "确定";
            String negative = "取消";

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext()).setTitle(title).setMessage(message);
            if (beforehand.length > 1){
                positive = beforehand[1];
            }
            if (beforehand.length > 2){
                negative = beforehand[2];
            }

            builder.setPositiveButton(positive, (dialog, which) -> {
                implement(v);
            });
            builder.setNegativeButton(negative, null);
            builder.show();
        }
    }
}
