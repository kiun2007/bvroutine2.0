package com.szxgm.gusustreet.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.mcxtzhang.indexlib.suspension.ISuspensionInterface;
import java.util.List;

import kiun.com.bvroutine.utils.ViewUtil;

public class SuspensionDecoration extends RecyclerView.ItemDecoration {

    private List<? extends ISuspensionInterface> mDatas;
    private Paint mPaint;
    private Rect mBounds;
    private LayoutInflater mInflater;
    private Context context;
    private int mTitleHeight;
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF999999");
    private static int mTitleFontSize;
    private int mHeaderViewCount = 0;

    public SuspensionDecoration(Context context, List<? extends ISuspensionInterface> datas) {

        this.mDatas = datas;
        this.mPaint = new Paint();
        this.mBounds = new Rect();
        this.mTitleHeight = (int) TypedValue.applyDimension(1, 30.0F, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(2, 16.0F, context.getResources().getDisplayMetrics());
        this.mPaint.setTextSize((float)mTitleFontSize);
        this.mPaint.setAntiAlias(true);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public SuspensionDecoration setTitleHeight(int mTitleHeight) {
        this.mTitleHeight = mTitleHeight;
        return this;
    }

    public SuspensionDecoration setColorTitleBg(int colorTitleBg) {
        COLOR_TITLE_BG = colorTitleBg;
        return this;
    }

    public SuspensionDecoration setColorTitleFont(int colorTitleFont) {
        COLOR_TITLE_FONT = colorTitleFont;
        return this;
    }

    public SuspensionDecoration setTitleFontSize(int mTitleFontSize) {
        this.mPaint.setTextSize((float)mTitleFontSize);
        return this;
    }

    public SuspensionDecoration setDatas(List<? extends ISuspensionInterface> mDatas) {
        this.mDatas = mDatas;
        return this;
    }

    public int getHeaderViewCount() {
        return this.mHeaderViewCount;
    }

    public SuspensionDecoration setHeaderViewCount(int headerViewCount) {
        this.mHeaderViewCount = headerViewCount;
        return this;
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();

        for(int i = 0; i < childCount; ++i) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            position -= this.getHeaderViewCount();
            if (this.mDatas != null && !this.mDatas.isEmpty() && position <= this.mDatas.size() - 1 && position >= 0 && ((ISuspensionInterface)this.mDatas.get(position)).isShowSuspension() && position > -1) {
                if (position == 0) {
                    this.drawTitleArea(c, left, right, child, params, position);
                } else if (null != ((ISuspensionInterface)this.mDatas.get(position)).getSuspensionTag() && !((ISuspensionInterface)this.mDatas.get(position)).getSuspensionTag().equals(((ISuspensionInterface)this.mDatas.get(position - 1)).getSuspensionTag())) {
                    this.drawTitleArea(c, left, right, child, params, position);
                }
            }
        }

    }

    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        this.mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect((float)left, (float)(child.getTop() - params.topMargin - this.mTitleHeight), (float)right, (float)(child.getTop() - params.topMargin), this.mPaint);
        this.mPaint.setColor(COLOR_TITLE_FONT);
        this.mPaint.getTextBounds((this.mDatas.get(position)).getSuspensionTag(), 0, (this.mDatas.get(position)).getSuspensionTag().length(), this.mBounds);
        c.drawText((this.mDatas.get(position)).getSuspensionTag(), (float)child.getPaddingLeft()+ ViewUtil.dp2px(context,15), (float)(child.getTop() - params.topMargin - (this.mTitleHeight / 2 - this.mBounds.height() / 2)), this.mPaint);
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

        if (layoutManager == null){
            return;
        }

        int pos = layoutManager.findFirstVisibleItemPosition();
        pos -= this.getHeaderViewCount();
        if (this.mDatas != null && !this.mDatas.isEmpty() && pos <= this.mDatas.size() - 1 && pos >= 0 && ((ISuspensionInterface)this.mDatas.get(pos)).isShowSuspension()) {
            String tag = ((ISuspensionInterface)this.mDatas.get(pos)).getSuspensionTag();
            View child = parent.findViewHolderForLayoutPosition(pos + this.getHeaderViewCount()).itemView;
            boolean flag = false;
            if (pos + 1 < this.mDatas.size() && null != tag && !tag.equals(((ISuspensionInterface)this.mDatas.get(pos + 1)).getSuspensionTag())) {
                Log.d("zxt", "onDrawOver() called with: media_menu = [" + child.getTop());
                if (child.getHeight() + child.getTop() < this.mTitleHeight) {
                    c.save();
                    flag = true;
                    c.translate(0.0F, (float)(child.getHeight() + child.getTop() - this.mTitleHeight));
                }
            }

            this.mPaint.setColor(COLOR_TITLE_BG);
            c.drawRect((float)parent.getPaddingLeft(), (float)parent.getPaddingTop(), (float)(parent.getRight() - parent.getPaddingRight()), (float)(parent.getPaddingTop() + this.mTitleHeight), this.mPaint);
            this.mPaint.setColor(COLOR_TITLE_FONT);
            this.mPaint.getTextBounds(tag, 0, tag.length(), this.mBounds);
            c.drawText(tag, (float)child.getPaddingLeft() + ViewUtil.dp2px(context,15), (float)(parent.getPaddingTop() + this.mTitleHeight - (this.mTitleHeight / 2 - this.mBounds.height() / 2)), this.mPaint);
            if (flag) {
                c.restore();
            }
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        position -= this.getHeaderViewCount();
        if (this.mDatas != null && !this.mDatas.isEmpty() && position <= this.mDatas.size() - 1) {
            if (position > -1) {
                ISuspensionInterface titleCategoryInterface = (ISuspensionInterface)this.mDatas.get(position);
                if (titleCategoryInterface.isShowSuspension()) {
                    if (position == 0) {
                        outRect.set(0, this.mTitleHeight, 0, 0);
                    } else if (null != titleCategoryInterface.getSuspensionTag() && !titleCategoryInterface.getSuspensionTag().equals(((ISuspensionInterface)this.mDatas.get(position - 1)).getSuspensionTag())) {
                        outRect.set(0, this.mTitleHeight, 0, 0);
                    }
                }
            }

        }
    }
}
