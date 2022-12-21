package kiun.com.bvroutine.utils;

import android.content.Context;
import android.view.View;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.interfaces.view.MessageView;
import kiun.com.bvroutine.interfaces.wrap.DataWrap;

public class DataUtil {

    public static boolean dataComplete(String tag, DataWrap warp, Object handler, boolean isWithWaring, View view){
        return dataComplete(tag, warp, handler, isWithWaring, view, null);
    }

    public static boolean dataComplete(String tag, DataWrap warp, Object handler, boolean isWithWaring, View view, Object[] argument){

        boolean isSuccess = warp.isSuccess();
        Context context = view != null ? view.getContext() : null;

        if (isSuccess){
            if (tag != null){

                List<Object> arrays = new LinkedList<>(
                        Arrays.asList(
                                "handler", handler,
                                "data", warp.getData(),
                                "waring", isWithWaring,
                                "context", context,
                                "view", view
                        )
                );

                if (argument != null && argument.length > 0 && argument.length % 2 == 0){

                    for(Object item : argument){
                        arrays.add(item);
                    }
                }
                Object v = JexlUtil.runArray(tag, arrays);
                if (v instanceof Boolean){
                    return (boolean) v;
                }
            }
        } else {
            if (handler instanceof MessageView){
                ((MessageView) handler).errorMsg(warp.getMsg());
            }
        }
        return isSuccess;
    }

    public static boolean dataComplete(String tag, Object data, Object handler, boolean isWithWaring, Context context){

        if (tag != null){
            JexlUtil.run(tag, "handler", handler, "data", data, "waring", isWithWaring, "context", context);
        }
        return true;
    }
}
