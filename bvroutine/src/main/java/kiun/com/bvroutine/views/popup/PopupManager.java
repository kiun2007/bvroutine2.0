package kiun.com.bvroutine.views.popup;

import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.interfaces.callers.CallBack;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class PopupManager implements PopupMenu.OnMenuItemClickListener {

    PopupMenu popupMenu;
    Map<Integer, CallBack> callBackMap = new HashMap<>();
    private SetCaller<Integer> onClickCallBack;

    public PopupManager(View view, int menuId){

        popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(menuId, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);

        //reflection
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PopupManager addItem(int id, CallBack callBack){
        MenuItem menuItem = popupMenu.getMenu().findItem(id);

        if (menuItem != null){
            callBackMap.put(id, callBack);
            popupMenu.getMenu().findItem(id).setVisible(true);
        }
        return this;
    }

    public void setOnMenuItemClick(SetCaller<Integer> onClickCallBack){
        this.onClickCallBack = onClickCallBack;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (onClickCallBack != null){
            onClickCallBack.call(item.getItemId());
            return true;
        }

        CallBack callBack = callBackMap.get(item.getItemId());

        if (callBack != null){
            callBack.call();
        }
        return true;
    }

    public void show(){
        popupMenu.show();
    }
}
