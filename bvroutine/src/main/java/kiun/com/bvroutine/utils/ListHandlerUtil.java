package kiun.com.bvroutine.utils;

import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.presenters.list.NetListProvider;

public class ListHandlerUtil {

    public static void refresh(ListHandler listHandler){
        if (listHandler == null){
            return;
        }
        if (listHandler.getTag() instanceof NetListProvider){
            ((NetListProvider) listHandler.getTag()).refresh();
        }
    }
}
