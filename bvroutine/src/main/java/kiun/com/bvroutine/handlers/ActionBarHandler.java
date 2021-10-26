package kiun.com.bvroutine.handlers;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class ActionBarHandler extends BaseHandler<View> {

    private CallBack rightCallBack;

    private SetCaller<View> viewOnclickCall;

    /**
     * 返回导航按钮.
     */
    public static final int TAG_BACK_BUTTON_ITEM = 100;

    /**
     * 标题.
     */
    public static final int TAG_TITLE_ITEM = 101;

    /**
     * 右边按钮.
     */
    public static final int TAG_RIGHTBUTTON_ITEM = 102;

    /**
     * 右边图片.
     */
    public static final int TAG_RIGHTIMAGE_ITEM = 103;

    public void setRightCallBack(CallBack rightCallBack) {
        this.rightCallBack = rightCallBack;
    }

    public void setRightCallBack(SetCaller<View> rightCallBack){
        this.viewOnclickCall = rightCallBack;
    }

    @Override
    public void onClick(Context context, int tag, View view) {
        switch (tag){
            case TAG_BACK_BUTTON_ITEM:{
                if (context instanceof Activity){
                    ((Activity) context).finish();
                }
                break;
            }
            case TAG_RIGHTBUTTON_ITEM:{

                if (this.rightCallBack != null){
                    this.rightCallBack.call();
                    return;
                }
                if (this.viewOnclickCall != null){
                    viewOnclickCall.call(view);
                }
                break;
            }
            case TAG_TITLE_ITEM:
            case TAG_RIGHTIMAGE_ITEM: {
                break;
            }
        }
    }
}
