package kiun.com.bvroutine.base;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

public abstract class BaseHandler<T> extends BaseObservable {

    protected int handlerBr = 0;

    public BaseHandler(){
    }

    public BaseHandler(int hanlderBr){
        this.handlerBr = hanlderBr;
    }

    public void onClick(Context context, int tag, T data){
    }

    public void onClick(View view, int tag, T data){
        onClick(view.getContext(), tag, data);
    }

    public int getBR() {
        return handlerBr;
    }
}