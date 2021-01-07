package kiun.com.bvroutine.base.binding.value;

import androidx.viewpager.widget.ViewPager;

import kiun.com.bvroutine.views.layout.StaticPager;

public class StaticPagerBindConvert extends BindConvert<StaticPager, Integer, Integer> implements ViewPager.OnPageChangeListener {

    public StaticPagerBindConvert(StaticPager view) {
        super(view);
        view.addOnPageChangeListener(this);
    }

    @Override
    public Integer getValue() {
        return nowValue;
    }

    @Override
    public void setValue(Integer value) {
        view.setCurrentItem(value);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        nowValue = position;
        onChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
