package kiun.com.bvroutine.views.delegate;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.utils.ObjectUtil;

/**
 * 流式布局分组托管业务
 */
public class FlowGroupDelegate extends ViewDelegate<ViewGroup> {

    /**
     * 流式布局水平间距
     */
    @AttrBind(def = 10)
    private int flowHorizontalSpacing;

    /**
     * 流式布局垂直间距
     */
    @AttrBind(def = 10)
    public int flowVerticalSpacing;

    /**
     * 均分若干一行.
     */
    @AttrBind(def = -1)
    public int columnOfAverage;

    @AttrBind(def = ViewGroup.LayoutParams.WRAP_CONTENT)
    public int itemHeight;

    public FlowGroupDelegate(@NonNull ViewGroup view, AttributeSet attributeSet) {
        super(view, attributeSet);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.FlowGroupDelegate;
    }

    @Override
    public void initView() {}

    private List<View> getVisibleViews(){

        List<View> views = new LinkedList<>();
        for (int i = 0; i < view.getChildCount(); i++) {
            if (view.getChildAt(i).getVisibility() == View.VISIBLE){
                views.add(view.getChildAt(i));
            }
        }
        return views;
    }

    public void onViewAdded(View view){

    }

    public void measure(int widthMeasureSpec, int heightMeasureSpec){

        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        //调用ViewGroup的方法，测量子view
        ObjectUtil.findMethod(ViewGroup.class, "measureChildren", int.class, int.class)
                .invoke(view, widthMeasureSpec, heightMeasureSpec);

        //当前这一行的累计行宽
        int lineWidth = 0;
        //当前这行的最大行高
        int totalHeight = 0;

        int lines = 0;
        //计算实际能使用宽度
        int widthUseSize = widthSize - (view.getPaddingLeft() + view.getPaddingRight());

        //二维视图
        List<List<View>> lineViews = new LinkedList<>();
        List<View> currentLine = new LinkedList<>();
        lineViews.add(currentLine);

        List<View> visibleViews = getVisibleViews();
        for (int i = 0; i < visibleViews.size(); i++) {

            View child = visibleViews.get(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();

            if (columnOfAverage > 0){
                params.width = (int) (widthUseSize / columnOfAverage - ((columnOfAverage-1)*flowHorizontalSpacing)/columnOfAverage - 0.5);
                params.height = itemHeight;
                child.setLayoutParams(params);

                //调用ViewGroup的方法，测量子view
                ObjectUtil.findMethod(ViewGroup.class, "measureChildren", int.class, int.class)
                        .invoke(view, widthMeasureSpec, heightMeasureSpec);
            }

            //先加元素所需宽度
            int itemWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;

            //预算是否超出宽度
            if (itemWidth + lineWidth > widthUseSize){
                //可能超出，则换行
                lineWidth = 0;
                lineViews.add(currentLine = new LinkedList<View>());
            }else{
                //未超出宽度，继续加宽度并添加水平间隔
                lineWidth += itemWidth + flowHorizontalSpacing;
            }
            currentLine.add(child);
        }

        for (List<View> line : lineViews) {
            int maxHeight = 0;
            for(View child : line){
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
            }

            totalHeight += maxHeight;
            if (lineViews.indexOf(line) < (lineViews.size() - 1)){
                totalHeight += flowVerticalSpacing;
            }
        }

        //加上当前容器的padding值
        totalHeight += view.getPaddingTop() + view.getPaddingBottom();

        ObjectUtil.findMethod(View.class, "setMeasuredDimension", int.class, int.class).invoke(view,
                widthMode == View.MeasureSpec.EXACTLY ? widthSize : 0,
                heightMode == View.MeasureSpec.EXACTLY ? heightSize : totalHeight);
    }

    public void layout(boolean changed, int l, int t, int r, int b){

        List<View> visibleViews = getVisibleViews();
        //pre为前面所有的child的相加后的位置
        int preLeft = view.getPaddingLeft();
        int preTop = view.getPaddingTop();

        //记录每一行的最高值
        int maxHeight = 0;
        for (int i = 0; i < visibleViews.size(); i++) {
            View child = visibleViews.get(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();

            if (columnOfAverage > 0){
                int widthSize = r - l;
                params.width = (int) (widthSize / columnOfAverage - ((columnOfAverage-1)*flowHorizontalSpacing)/columnOfAverage - 0.5);
                params.height = itemHeight;
                child.setLayoutParams(params);
            }

            //r-l为当前容器的宽度。如果子view的累积宽度大于容器宽度，就换行。
            if (preLeft + params.leftMargin + child.getMeasuredWidth() + params.rightMargin + view.getPaddingRight() > (r - l)) {
                //重置
                preLeft = view.getPaddingLeft();
                //要选择child的height最大的作为设置
                preTop = preTop + maxHeight + flowVerticalSpacing; //flowHorizontalSpacing
                maxHeight = view.getChildAt(i).getMeasuredHeight() + params.topMargin + params.bottomMargin;
            } else { //不换行,计算最大高度
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);
            }
            //left坐标
            int left = preLeft + params.leftMargin;
            //top坐标
            int top = preTop + params.topMargin;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            //为子view布局
            child.layout(left, top, right, bottom);
            //计算布局结束后，preLeft的值
            preLeft += (params.leftMargin + child.getMeasuredWidth() + params.rightMargin + flowHorizontalSpacing);
        }
    }
}
