package kiun.com.bvroutine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ViewUtil;

public class TabViewLayout extends TabLayout implements TypedView {

    @AttrBind
    private String[] tabArray;

    public TabViewLayout(Context context) {
        super(context);
    }

    public TabViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public TabViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.TabViewLayout;
    }

    @Override
    public void initView() {

        if (tabArray != null){
            for (int i = 0; i < tabArray.length; i++) {
                TabLayout.Tab tab = newTab();
                tab.setTag(i);
                tab.setText(tabArray[i]);
                addTab(tab);
            }
        }
    }

    public void setTabArray(List<?> array){
        removeAllTabs();
        for (Object item : array){
            TabLayout.Tab tab = newTab();
            tab.setTag(item);
            tab.setText(item.toString());
            addTab(tab);
        }
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }
}
