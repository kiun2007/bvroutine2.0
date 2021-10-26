package kiun.com.bvroutine.views.listeners;


import androidx.databinding.ObservableField;
import androidx.viewpager.widget.ViewPager;

import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.PagerItemChanger;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class PagerHandler implements ViewPager.OnPageChangeListener {

    private String[] titles;
    SetCaller<Integer> selectedListener;
    PagerItemChanger pagerItemChanger;
    int currentIndex = 0;
    public List<ObservableField<String>> allTitleFields = new LinkedList<>();
    public List<ObservableField<Boolean>> selectedFields = new LinkedList<>();

    public PagerHandler(SetCaller<Integer> sel, String ...titles){
        selectedListener = sel;
        setTitles(titles);
    }

    public void setPagerItemChanger(PagerItemChanger pagerItemChanger) {
        this.pagerItemChanger = pagerItemChanger;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setCurrentIndex(int index){
        if (titles != null && index < titles.length && this.pagerItemChanger != null){
            this.pagerItemChanger.setCurrentItem(index);
        }
        ObservableField<Boolean> observableField = getSelectedObser(index);
        for (ObservableField<Boolean> field : selectedFields){
            field.set(false);
        }
        if (observableField != null){
            observableField.set(true);
        }
    }

    public void setTitles(String[] titles) {
        if (titles == null) return;

        allTitleFields.clear();
        selectedFields.clear();
        int i = 0;
        for (String title : titles){
            allTitleFields.add(new ObservableField<>(title));
            selectedFields.add(new ObservableField<>(i == 0));
            i ++;
        }
        this.titles = titles;
    }

    public void setTitle(String title, int index){
        if (titles != null && index < titles.length){
            titles[index] = title;
            allTitleFields.get(index).set(title);
        }
    }

    public void setTitle(String title){
        setTitle(title, currentIndex);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentIndex(position);
        currentIndex = position;
        if (selectedListener != null){
            selectedListener.call(position);
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public String getTitle(int index){
        if (titles != null && index < titles.length){
            return titles[index];
        }
        return null;
    }

    public ObservableField<String> getTitleObser(int index){
        if (titles != null && index < titles.length){
            return allTitleFields.get(index);
        }
        return null;
    }

    public ObservableField<Boolean> getSelectedObser(int index){
        if (titles != null && index < titles.length){
            return selectedFields.get(index);
        }
        return null;
    }

    public boolean indexSelected(int index){
        return currentIndex == index;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
