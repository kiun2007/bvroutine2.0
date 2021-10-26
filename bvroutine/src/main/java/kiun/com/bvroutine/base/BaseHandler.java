package kiun.com.bvroutine.base;

import android.content.Context;

public abstract class BaseHandler<T>{

    protected int handlerBr = 0;

    public BaseHandler(){
    }

    public BaseHandler(int hanlderBr){
        this.handlerBr = hanlderBr;
    }

    public void onClick(Context context, int tag, T data){
    }

    public int getBR() {
        return handlerBr;
    }
}