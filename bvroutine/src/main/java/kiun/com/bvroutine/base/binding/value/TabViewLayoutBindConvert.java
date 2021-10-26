package kiun.com.bvroutine.base.binding.value;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import kiun.com.bvroutine.views.TabViewLayout;

public class TabViewLayoutBindConvert extends BindConvert<TabViewLayout, Object, Object> implements TabLayout.BaseOnTabSelectedListener {

    public TabViewLayoutBindConvert(TabViewLayout view) {
        super(view);
        view.addOnTabSelectedListener(this);
    }

    @Override
    public Object getValue() {
        return convert(nowValue);
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Integer){
            Integer index = (Integer) value;
            Objects.requireNonNull(view.getTabAt(index)).select();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        onChanged(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
