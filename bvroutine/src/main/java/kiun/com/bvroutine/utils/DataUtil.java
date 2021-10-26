package kiun.com.bvroutine.utils;

import kiun.com.bvroutine.interfaces.view.MessageView;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class DataUtil {

    public static boolean dataComplete(String tag, DataWrap warp, Object handler, boolean isWithWaring){

        boolean isSuccess = warp.isSuccess();
        if (isSuccess){
            if (tag != null){
                JexlUtil.run(tag, "handler", handler, "data", warp.getData(), "waring", isWithWaring);
            }
        } else {
            if (handler instanceof MessageView){
                ((MessageView) handler).errorMsg(warp.getMsg());
            }
        }
        return isSuccess;
    }
}
