package kiun.com.bvroutine.interfaces;

import kiun.com.bvroutine.views.text.GeneralItemText;

public interface GeneralItemTextListener {

    /**
     * view, id, title, extra
     * @param view
     * @param id
     * @param title
     * @param extra
     */
    void onChanged(GeneralItemText view, String id, String title, Object extra);
}
