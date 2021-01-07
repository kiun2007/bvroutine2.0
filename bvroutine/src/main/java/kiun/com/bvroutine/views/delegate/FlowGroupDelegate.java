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

public class FlowGroupDelegate extends ViewDelegate<ViewGroup> {

    @AttrBind(def = 10)
    private int flowHorizontalSpacing;

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

        //最大的宽
        int maxWidth = 0;
        //累计的高
        int totalHeight = 0;

        //当前这一行的累计行宽
        int lineWidth = 0;
        //当前这行的最大行高
        int maxLineHeight = 0;
        //用于记录换行前的行宽和行高
        int oldHeight;
        int oldWidth;

        int lines = 0;

        List<View> visibleViews = getVisibleViews();
        //假设 widthMode和heightMode都是AT_MOST
        for (int i = 0; i < visibleViews.size(); i++) {
            View child = visibleViews.get(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();

            if (columnOfAverage > 0){
                params.width = (int) (widthSize / columnOfAverage - ((columnOfAverage-1)*flowHorizontalSpacing)/columnOfAverage - 0.5);
                params.height = itemHeight;
                child.setLayoutParams(params);

                //调用ViewGroup的方法，测量子view
                ObjectUtil.findMethod(ViewGroup.class, "measureChildren", int.class, int.class)
                        .invoke(view, widthMeasureSpec, heightMeasureSpec);
            }

            oldHeight = maxLineHeight;
            //当前最大宽度
            oldWidth = maxWidth;

            int deltaX = child.getMeasuredWidth() + params.leftMargin + params.rightMargin + flowHorizontalSpacing;

            if (lineWidth + deltaX + view.getPaddingLeft() + view.getPaddingRight() > widthSize) {
                //如果折行,height增加
                //和目前最大的宽度比较,得到最宽。不能加上当前的child的宽,所以用的是oldWidth
                maxWidth = Math.max(lineWidth, oldWidth);
                //重置宽度
                lineWidth = deltaX;
                //累加高度
                totalHeight += oldHeight;
                //重置行高,当前这个View，属于下一行，因此当前最大行高为这个child的高度加上margin
                maxLineHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin + flowVerticalSpacing;
                //flowHorizontalSpacing
                //记录换多少行
                lines++;
            } else {
                //不换行，累加宽度
                lineWidth += deltaX;
                //不换行，计算行最高
                int deltaY = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxLineHeight = Math.max(maxLineHeight, deltaY);
            }

            if (i == visibleViews.size() - 1) {
                //前面没有加上下一行的搞，如果是最后一行，还要再叠加上最后一行的最高的值
                if (columnOfAverage != -1){
                    if (visibleViews.size() > columnOfAverage * lines){
                        totalHeight += maxLineHeight;
                    }
                }else{
                    totalHeight += maxLineHeight;
                }

                //计算最后一行和前面的最宽的一行比较
                maxWidth = Math.max(lineWidth, oldWidth);
            }
        }

        //加上当前容器的padding值
        maxWidth += view.getPaddingLeft() + view.getPaddingRight();
        totalHeight += view.getPaddingTop() + view.getPaddingBottom();

        ObjectUtil.findMethod(View.class, "setMeasuredDimension", int.class, int.class).invoke(view,
                widthMode == View.MeasureSpec.EXACTLY ? widthSize : maxWidth,
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
