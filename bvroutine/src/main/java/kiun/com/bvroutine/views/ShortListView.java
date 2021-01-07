package kiun.com.bvroutine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.BR;
import kiun.com.bvroutine.R;
import kiun.com.bvroutine.base.AttrBind;
import kiun.com.bvroutine.base.EventBean;
import kiun.com.bvroutine.base.binding.ValListener;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.view.TypedView;
import kiun.com.bvroutine.utils.ListUtil;
import kiun.com.bvroutine.utils.ViewBindingUtil;
import kiun.com.bvroutine.utils.ViewUtil;
import kiun.com.bvroutine.views.adapter.Indexer;
import kiun.com.bvroutine.views.delegate.FlowGroupDelegate;

public class ShortListView extends LinearLayout implements TypedView, ValListener<List> {

    @AttrBind
    int itemLayout = -1;

    @AttrBind
    int emptyLayout = -1;

    View emptyView = null;

    List<ViewDataBinding> bindings = new LinkedList<>();

    private ListHandler listHandler;

    FlowGroupDelegate delegate;

    public ShortListView(Context context) {
        super(context);
    }

    public ShortListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        delegate = new FlowGroupDelegate(this, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    public ShortListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new FlowGroupDelegate(this, attrs);
        ViewUtil.initTyped(this, attrs);
    }

    @Override
    public int[] getStyleableId() {
        return R.styleable.ShortListView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        delegate.layout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        delegate.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void initView() {

        if (isInEditMode()) {
            if (itemLayout != -1){
                for (int i = 0; i < 3; i++) {
                    LayoutInflater.from(getContext()).inflate(itemLayout, this, true);
                }
            }
        }

        if (emptyLayout != -1){
            emptyView = LayoutInflater.from(getContext()).inflate(emptyLayout, null, false);
        }
    }

    private void showAll(boolean isShow){
        for (ViewDataBinding binding : bindings){
            binding.getRoot().setVisibility(isShow ? VISIBLE : GONE);
        }
    }

    @Override
    public List getVal() {
        return null;
    }

    public void setVal(List list){

        if (itemLayout == -1){
            return;
        }

        showAll(true);
        for (int i = 0; list != null && i < list.size(); i++) {

            if (i >= bindings.size()){
                ViewDataBinding viewDataBinding = ViewBindingUtil.inflate(LayoutInflater.from(getContext()), itemLayout, this, true);
                bindings.add(viewDataBinding);
            }

            ViewDataBinding binding = bindings.get(i);

            if (listHandler != null){
                binding.setVariable(BR.handler, listHandler);
            }
            Object item = list.get(i);

            if (item instanceof EventBean){
                EventBean eventBean = (EventBean) item;
                eventBean.bind(BR.item, binding);
            }

            binding.setVariable(BR.item, item);
            binding.setVariable(BR.index, new Indexer(i, list.size()));
        }

        for (int i = list == null ? 0 : list.size(); i < bindings.size(); i++){
            bindings.get(i).getRoot().setVisibility(GONE);
        }

        if (emptyView != null){
            if (ListUtil.isEmpty(list)){
                if (emptyView.getParent() != this){
                    addView(emptyView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                }
                emptyView.setVisibility(VISIBLE);
            }else{
                if (emptyView.getParent() == this){
                    removeView(emptyView);
                }else{
                    emptyView.setVisibility(GONE);
                }
            }
        }
        invalidate();
    }

    public void setListHandler(ListHandler listHandler) {
        this.listHandler = listHandler;
    }

    @Override
    public <T extends View> T findParentById(int id) {
        return null;
    }

    @Override
    public void setListener(InverseBindingListener listener) {
    }
}
