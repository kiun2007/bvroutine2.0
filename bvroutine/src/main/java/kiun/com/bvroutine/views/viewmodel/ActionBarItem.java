package kiun.com.bvroutine.views.viewmodel;

import android.graphics.drawable.Drawable;

import androidx.databinding.Bindable;

import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.handlers.ActionBarHandler;

public class ActionBarItem extends EventBean {

    private String title;
    private boolean barNoBack;
    private String backTitle;
    private String rightTitle;
    private boolean withStatusBar = true;
    private Drawable rightImage = null;

    private ActionBarHandler handler = null;

    public ActionBarHandler getHandler() {
        if(handler == null){
            handler = new ActionBarHandler();
        }
        return handler;
    }

    public void setHandler(ActionBarHandler handler) {
        this.handler = handler;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        onChanged();
    }

    public boolean isBarNoBack() {
        return barNoBack;
    }

    public void setBarNoBack(boolean barNoBack) {
        this.barNoBack = barNoBack;
        onChanged();
    }

    public String getBackTitle() {
        return backTitle;
    }

    public void setBackTitle(String backTitle) {
        this.backTitle = backTitle;
        onChanged();
    }

    public boolean isWithStatusBar() {
        return withStatusBar;
    }

    public void setWithStatusBar(boolean withStatusBar) {
        this.withStatusBar = withStatusBar;
        onChanged();
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
        onChanged();
    }

    @Bindable
    public Drawable getRightImage() {
        return rightImage;
    }

    public void setRightImage(Drawable rightImage) {
        this.rightImage = rightImage;
    }
}