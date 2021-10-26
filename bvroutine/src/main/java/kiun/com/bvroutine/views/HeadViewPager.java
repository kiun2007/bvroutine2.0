package kiun.com.bvroutine.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.viewpager.widget.ViewPager;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.PagerItemChanger;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.databinding.ViewHeadpagerBinding;
import kiun.com.bvroutine.interfaces.PagerHandlerGeter;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.views.adapter.PagerFragmentAdapter;
import kiun.com.bvroutine.views.listeners.PagerHandler;

@InverseBindingMethods({
        @InverseBindingMethod(type = HeadViewPager.class, attribute = "adapter"),
        @InverseBindingMethod(type = HeadViewPager.class, attribute = "handler"),
})
public class HeadViewPager extends LinearLayout implements View.OnClickListener, PagerHandlerGeter, PagerItemChanger, ViewPager.OnPageChangeListener {

    ViewHeadpagerBinding dataBinding;
    PagerHandler pagerHandler;
    String[] allTitle;
    TextView currentView;
    List<TextView> allTextView = new LinkedList<>();
    TypedArray array;
    int paintColor = 0;
    int unselectColor = 0;
    int selectedColor = 0;
    boolean isTextView = false;
    View firstChild = null;
    SetCaller<Integer> pagerCaller;

    public HeadViewPager(Context context) {
        this(context, null);
    }

    public HeadViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        array = getContext().obtainStyledAttributes(attrs, R.styleable.HeadViewPager);
        String titles = array.getString(R.styleable.HeadViewPager_titles);
        if (titles != null){
            allTitle = titles.split(",");
        }

        unselectColor = array.getColor(R.styleable.HeadViewPager_unselectColor, 0xFF3699ff);
        selectedColor = array.getColor(R.styleable.HeadViewPager_textSeletedColor, 0xFFFFFFFF);
        paintColor = array.getColor(R.styleable.HeadViewPager_paintColor, 0xFF3699ff);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int initCount = getChildCount();

        if (initCount >= 1){
            firstChild = getChildAt(0);
            isTextView = (firstChild instanceof TextView);
        }
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_headpager, this, true);
        dataBinding.pagerContent.addOnPageChangeListener(this);

        if (firstChild == null || isTextView){
            dataBinding.normalHeader.setPaintColor(paintColor);
            if (allTitle != null){
                pagerHandler = new PagerHandler(this::onSelectChanged, allTitle);
                initHandler(pagerHandler);

                int headPadding = array.getDimensionPixelOffset(R.styleable.HeadViewPager_headPadding, -1);
                if (headPadding > -1){
                    int bottomPadding = array.getDimensionPixelOffset(R.styleable.HeadViewPager_bottomPadding, -1);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                    dataBinding.normalHeader.setLayoutParams(layoutParams);
                    dataBinding.contentPanel.setPadding(headPadding,0,headPadding,bottomPadding);
                }

                TextView tmpTextView = null;
                if (isTextView){
                    tmpTextView = (TextView) firstChild;
                }

                for (int i = 0; i < allTitle.length; i++) {
                    TextView itemText = new TextView(getContext());

                    if (tmpTextView != null){
                        itemText.setPadding(tmpTextView.getPaddingLeft(), tmpTextView.getPaddingTop(), tmpTextView.getPaddingRight(), tmpTextView.getPaddingBottom());
                        itemText.setLayoutParams(tmpTextView.getLayoutParams());
                        itemText.setGravity(tmpTextView.getGravity());
                    }else{
                        int itemWidth = array.getDimensionPixelOffset(R.styleable.HeadViewPager_itemWidth, LayoutParams.MATCH_PARENT);
                        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(itemWidth, LayoutParams.MATCH_PARENT);
                        linearLayoutParams.weight = 1;

                        int itemPadding = array.getDimensionPixelOffset(R.styleable.HeadViewPager_itemPadding, 0);
                        itemText.setPadding(itemPadding,itemPadding,itemPadding,itemPadding);
                        itemText.setGravity(Gravity.CENTER);
                        itemText.setLayoutParams(linearLayoutParams);
                    }

                    pagerHandler.getTitleObser(i).addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable sender, int propertyId) {
                            if (sender instanceof ObservableField){
                                itemText.setText(((ObservableField<String>) sender).get());
                            }
                        }
                    });
                    itemText.setText(pagerHandler.getTitleObser(i).get());
                    itemText.setOnClickListener(this);
                    itemText.setTag(i);
                    if (currentView == null) {
                        currentView = itemText;
                        currentView.setBackgroundColor(paintColor);
                        currentView.setTextColor(selectedColor);
                    }else{
                        itemText.setTextColor(unselectColor);
                    }
                    allTextView.add(itemText);
                    dataBinding.normalHeader.addView(itemText);
                }
            }
        }

        array.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (firstChild != null){
            removeView(firstChild);
            dataBinding.contentPanel.removeAllViews();
            dataBinding.contentPanel.addView(firstChild);
        }
    }

    public void initAdapter(PagerFragmentAdapter adapter){
        if (dataBinding != null && adapter != null){
            dataBinding.pagerContent.setAdapter(adapter);
            dataBinding.pagerContent.setOffscreenPageLimit(adapter.getLimitPager());
            adapter.setHandlerGeter(this);
            int current = dataBinding.pagerContent.getCurrentItem();
            if (allTextView.size() > 0){
                setSelected(allTextView.get(current), false);
            }else if (pagerHandler != null){
                pagerHandler.setCurrentIndex(current);
            }
        }
    }

    public void initHandler(PagerHandler handler){
        if (dataBinding != null){
            pagerHandler = handler;
            dataBinding.pagerContent.addOnPageChangeListener(handler);
            handler.setPagerItemChanger(this);
            if (allTitle == null){
                pagerHandler.setCurrentIndex(dataBinding.pagerContent.getCurrentItem());
            }
        }
    }

    public void setCurrentItem(int currentItem){
        if (dataBinding != null){
            dataBinding.pagerContent.setCurrentItem(currentItem);
        }
    }

    private void onSelectChanged(int position){
        setSelected(allTextView.get(position), false);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(HeadViewPager viewPager, PagerFragmentAdapter adapter){
        viewPager.initAdapter(adapter);
    }

    @BindingAdapter("handler")
    public static void setHandler(HeadViewPager viewPager, PagerHandler handler){
        if (handler != null){
            viewPager.initHandler(handler);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView && currentView != null){
            setSelected((TextView) v, true);
        }
    }

    public PagerHandler getPagerHandler() {
        return pagerHandler;
    }

    private void setSelected(TextView target, boolean isEvent){
        currentView.setTextColor(unselectColor);
        currentView.setBackgroundColor(0);
        target.setTextColor(selectedColor);
        target.setBackgroundColor(paintColor);
        currentView = target;

        if ((target.getTag() instanceof Integer) && isEvent){
            setCurrentItem((Integer) target.getTag());
        }
    }

    public void setPagerCaller(SetCaller<Integer> pagerCaller) {
        this.pagerCaller = pagerCaller;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (this.pagerCaller != null){
            pagerCaller.call(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
