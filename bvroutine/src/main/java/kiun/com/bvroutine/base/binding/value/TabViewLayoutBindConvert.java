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

        for (int i = 0; i < view.getTabCount(); i++) {
            Object item = view.getTabAt(i).getTag();
            if (Objects.hash(item) == Objects.hash(value)){
                Objects.requireNonNull(view.getTabAt(i)).select();
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        onChanged(tab.getTag());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
